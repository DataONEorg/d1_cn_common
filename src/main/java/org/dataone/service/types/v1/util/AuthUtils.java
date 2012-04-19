package org.dataone.service.types.v1.util;

import java.util.ArrayList;
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
	
	public static Subject[] authorizedClientSubjects(Session session)
	{
		// for the "Verified" symbolic user
		Subject verifiedSubject = new Subject();
		verifiedSubject.setValue(Constants.SUBJECT_VERIFIED_USER);

		// get the subject[s] from the session
		Set<Subject> subjects = new TreeSet<Subject>();
		if (session != null) {
			// primary subject
			Subject subject = session.getSubject();
			if (subject != null) {
				subjects.add(subject);
			}
			// details about the subject
			SubjectInfo subjectInfo = session.getSubjectInfo();
			if (subjectInfo != null) {
				// find subjectInfo for the primary subject
				List<Person> personList = subjectInfo.getPersonList();
				List<Group> groupList = subjectInfo.getGroupList();
				if (personList != null) {
					for (Person p : personList) {
						// for every person listed (isVerified is transitive)
						logger.debug("checking person");
						logger.debug("p.getVerified(): " + p.getVerified());
						if (p.getVerified() != null && p.getVerified()) {
							// add the verified symbolic user
							subjects.add(verifiedSubject);
						}
						// add the equivalent identities
						List<Subject> equivList = p.getEquivalentIdentityList();
						if (equivList != null) {
							for (Subject equivSubject : equivList) {
								subjects.add(equivSubject);
								// find that entry
								for (Person equivPerson: personList) {
									if (equivSubject.equals(equivPerson.getSubject())) {
										// transitive group membership
										if (equivPerson.getIsMemberOfList() != null) {
											for (Subject equivGroup: equivPerson.getIsMemberOfList()) {
												subjects.add(equivGroup);
											}
										}
										// TODO: is verified transitive?
										if (equivPerson.getVerified() != null && equivPerson.getVerified()) {
											// add the verified symbolic user
											subjects.add(verifiedSubject);	
										}
									}
								}
							}
						}
						// add the groups they are a member of
						List<Subject> memberOfList = p.getIsMemberOfList();
						if (memberOfList != null) {
							for (Subject g : memberOfList) {
								subjects.add(g);
							}
						}
						// look at all the Groups to see if this person has membership defined there
						if (groupList != null) {
							for (Group group: groupList) {
								if (group.getHasMemberList() != null) {
									for (Subject member: group.getHasMemberList()) {
										// is the person a member?
										if (member.equals(p.getSubject())) {
											// add this group as a subject to check if it is not already there
											subjects.add(group.getSubject());
										}
									}
								}
							}
						}
						break;
					}
				}
			}

			// add the authenticated symbolic since we have a session
			Subject authenticatedSubject = new Subject();
			authenticatedSubject.setValue(Constants.SUBJECT_AUTHENTICATED_USER);
			subjects.add(authenticatedSubject);
		}

		// add public subject for everyone
		Subject publicSubject = new Subject();
		publicSubject.setValue(Constants.SUBJECT_PUBLIC);
		subjects.add(publicSubject);

		return (Subject[]) subjects.toArray();
	}
	
	
	
	public static boolean isAuthorized(Subject[] subjects, Permission permission, 
	     SystemMetadata systemMetadata) 
	throws NotAuthorized
	{
		boolean allowed = false;
		// do we own it?
	    for (Subject s: subjects) {
	      logger.debug("Comparing \t" + 
	                       systemMetadata.getRightsHolder().getValue() +
	                       " \tagainst \t" + s.getValue());
	    	allowed = systemMetadata.getRightsHolder().equals(s);
	    	if (allowed) {
	    		return allowed;
	    	}
	    }    
	    
	    // otherwise check the access rules
	    try {
		    List<AccessRule> allows = systemMetadata.getAccessPolicy().getAllowList();
		    search: // label break
		    for (AccessRule accessRule: allows) {
		      for (Subject s: subjects) {
		        logger.debug("Checking allow access rule for subject: " + s.getValue());
		        if (accessRule.getSubjectList().contains(s)) {
		        	logger.debug("Access rule contains subject: " + s.getValue());
		        	for (Permission p: accessRule.getPermissionList()) {
			        	logger.debug("Checking permission: " + p.xmlValue());
		        		List<Permission> expandedPermissions = expandPermissions(p);
		        		allowed = expandedPermissions.contains(permission);
		        		if (allowed) {
				        	logger.info("Permission granted: " + p.xmlValue() + " to " + s.getValue());
		        			break search; //label break
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
	      throw new NotAuthorized("1820", permission + " not allowed on " + 
	    		  systemMetadata.getIdentifier().getValue());
	    }
	    
	    return allowed;
	    
	}
	
	 /**
	   * Given a Permission, returns a list of all permissions that it encompasses
	   * Permissions are hierarchical so that WRITE also allows READ.
	   * @param permission
	   * @return list of included Permissions for the given permission
	   */
	  protected static List<Permission> expandPermissions(Permission permission) {
		  	List<Permission> expandedPermissions = new ArrayList<Permission>();
		    if (permission.equals(Permission.READ)) {
		    	expandedPermissions.add(Permission.READ);
		    }
		    if (permission.equals(Permission.WRITE)) {
		    	expandedPermissions.add(Permission.READ);
		    	expandedPermissions.add(Permission.WRITE);
		    }
		    if (permission.equals(Permission.CHANGE_PERMISSION)) {
		    	expandedPermissions.add(Permission.READ);
		    	expandedPermissions.add(Permission.WRITE);
		    	expandedPermissions.add(Permission.CHANGE_PERMISSION);
		    }
		    return expandedPermissions;
	  }
}
