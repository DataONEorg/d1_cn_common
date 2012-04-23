package org.dataone.service.types.v1.util;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.dataone.service.exceptions.NotAuthorized;
import org.dataone.service.types.v1.AccessRule;
import org.dataone.service.types.v1.Group;
import org.dataone.service.types.v1.Permission;
import org.dataone.service.types.v1.Person;
import org.dataone.service.types.v1.Session;
import org.dataone.service.types.v1.Subject;
import org.dataone.service.types.v1.SubjectInfo;
import org.dataone.service.types.v1.SystemMetadata;
import org.dataone.service.util.Constants;

public class AuthUtils {

	private static Logger logger = Logger.getLogger(AuthUtils.class);
	
	private static Subject verifiedSubject = null;
	
	
	/**
	 * Derived from Metacat implementation
	 * Creates a list of subjects represented in the session object, parsing 
	 * both the subject of the session and the subjectInfo.
	 * 
	 * - It will always include 'public' subject
	 * 
	 * - Includes 'authenticated' subject when the session subject is not empty or 'public'
	 * - Zeros-out the subjectInfo of the session if the subject of the session is
	 *   null or 'public'
	 * - 'verified' is included if primary subject or equivalent identity in the 
	 *   subectInfo is verified.
	 * - group membership is transitive, too - the group subjects of equivalent 
	 *   identities are returned as well.
	 * - Does not handle administrative access / authorization
	 * 
	 * @param session
	 * @return - a Subject array.
	 */
	public static Subject[] authorizedClientSubjects(Session session)
	{
		// setup a static subject for verified symbolic user
		if (verifiedSubject == null) {
			verifiedSubject = new Subject();
			verifiedSubject.setValue(Constants.SUBJECT_VERIFIED_USER);
		}
		
		// get the subject[s] from the session
		Set<Subject> subjects = new TreeSet<Subject>();

		// add public subject for everyone
		Subject publicSubject = new Subject();
		publicSubject.setValue(Constants.SUBJECT_PUBLIC);
		subjects.add(publicSubject);
		
		if (session != null) {
			
			// parse the session subject as the primary subject
			Subject primarySubject = session.getSubject();
			if (primarySubject != null) {
				subjects.add(primarySubject);
				
				// depending on the primary subject, can add the authenticated symbolic user
				if (! primarySubject.getValue().equals(Constants.SUBJECT_PUBLIC) ) {
					Subject s = new Subject();
					s.setValue(Constants.SUBJECT_AUTHENTICATED_USER);
					subjects.add(s);
					
				} else {
					// zero out the subjectInfo for non-authenticated sessions
					session.setSubjectInfo(null);
				}
			}
			
			// traverse the subjectInfo for more subjects 
			SubjectInfo subjectInfo = session.getSubjectInfo();
			if (subjectInfo != null) {
				// get subjects for the primary subject within the subjectInfo of the session
				findPersonsSubjects(subjects, subjectInfo, primarySubject);
			}
		}
		return subjects.toArray(new Subject[0]);
	}
	

	
	/*
	 * A recursive method to traverse the equivalent-identity relationships
	 * and to handle the transitive nature of group membership and verified status.
	 */
	private static void findPersonsSubjects(Set<Subject> foundSubjects, SubjectInfo subjectInfo, Subject targetSubject) {

		// setting this up for subsequent searches in the loop
		List<Group> groupList = null;
		if (subjectInfo != null) {
			groupList = subjectInfo.getGroupList();
		}
		
		if (subjectInfo != null && subjectInfo.getPersonList() != null) {
			for (Person p: subjectInfo.getPersonList()) {
				if (p.getSubject().equals(targetSubject)) {

					// add targetSubject
					foundSubjects.add(targetSubject);
					logger.debug("traversing person: " + targetSubject.getValue());
					
					
					// check verification status of this identity
					if (p.getVerified() != null && p.getVerified()) {
						foundSubjects.add(verifiedSubject);
					}

					// add the groups of this identity are a member of
					List<Subject> memberOfList = p.getIsMemberOfList();
					if (memberOfList != null) {
						for (Subject g : memberOfList) {
							foundSubjects.add(g);
						}
					}
					
					
					// just in case the groups have the person as a member
					// and the person doesn't list the group in isMemberOf
					if (groupList != null) {
						for (Group group: groupList) {
							if (group.getHasMemberList() != null) {
								for (Subject member: group.getHasMemberList()) {
									// is the person a member?
									if (member.equals(p.getSubject())) {
										// add this group as a subject to check if it is not already there
										foundSubjects.add(group.getSubject());
									}
								}
							}
						}
					}
					
					// finally add equivalent identities
					// (this is where the recursion happens)
					for (Subject eqId : p.getEquivalentIdentityList()) {
						// recursive call for un-examined targetSubjects
						if (! foundSubjects.contains(eqId)) {
							findPersonsSubjects(foundSubjects, subjectInfo, eqId);
						}
					}
					
				}
			}
		}
	}
	
	
	
	/**
	 * Needs testing!!
	 * Queries the systemMetadata to see if one of the given subjects 
	 * is allowed the specified permission against the given systemMetadata
	 * 
	 * @param subjects - the list of subject, assumed to represent the subjects
	 *                   of a session
	 * @param requestedPerm -  the permission that is requested authorization for 
	 * @param systemMetadata - the systemMetadata of the target object to test
	 * @return - true if one of the subjects is authorized for the given permission
	 * @throws NotAuthorized
	 */
	public static boolean isAuthorized(Subject[] subjects, Permission requestedPerm,
			SystemMetadata systemMetadata) 
	throws NotAuthorized
	{
		boolean allowed = false;

		// if rightsHolder is one of subjects, can return allowed (true)
		// without further checks
		for (Subject s: subjects) {
			allowed = systemMetadata.getRightsHolder().equals(s);
			
			logger.debug(String.format(
					"Comparing \t%s \tagainst \t%s", 
					systemMetadata.getRightsHolder().getValue(),
					s.getValue())
					);
			
			if (allowed) {
				return allowed;
			}
		}    

	    // otherwise check the access rules
	    try {
		    List<AccessRule> allows = systemMetadata.getAccessPolicy().getAllowList();
		    if (allows != null) {
		    	search:
		    		for (AccessRule accessRule: allows) {
		    			if (accessRule.sizePermissionList() > 0) {
		    				for (Subject s: subjects) {
		    					if (accessRule.getSubjectList().contains(s)) {
		    						if(requestedPerm.equals(Permission.READ)) {
		    							// any permission will do, so skip the test
		    							allowed = true;
		    							break search;
		    						} else if (requestedPerm.equals(Permission.CHANGE_PERMISSION)) {
		    							// has to be exact match
		    							allowed = accessRule.getPermissionList().contains(Permission.CHANGE_PERMISSION);
		    						} else {
		    							allowed = accessRule.getPermissionList().contains(Permission.CHANGE_PERMISSION)
		    									|| accessRule.getPermissionList().contains(Permission.WRITE);
		    						}
		    						if (allowed) {
		    							break search;
		    						}
		    					}
		    				}
		    			}
		    		}
		    }
	    } catch (Exception e) {
	    	// catch all for errors - safe side should be to deny the access
	    	logger.error("Problem checking authorization - defaulting to deny", e);
			allowed = false;
		  
	    }
	    
	    // throw or return?
	    if (!allowed) {
	      throw new NotAuthorized("1820", requestedPerm + " not allowed on " + 
	    		  systemMetadata.getIdentifier().getValue());
	    }
	    
	    return allowed;	    
	}
}
