package org.dataone.service.types.v1.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
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

	private static final Logger logger = Logger.getLogger(AuthUtils.class);
	
	
	private static Subject buildSubject(String value) {
		Subject s = new Subject();
		s.setValue(value);
		return s;
	}
	
	
	/**
	 * Derived from Metacat implementation
	 * Creates a set of subjects represented in the session object, parsing 
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
	 * @return - the set of Subject objects.
	 */
	public static Set<Subject> authorizedClientSubjects(Session session)
	{
		
		
		// using an arbitrary initial size of the HashSet, 
		Set<Subject> subjects = new HashSet<Subject>();

		// add public subject for everyone
		subjects.add(buildSubject(Constants.SUBJECT_PUBLIC));
		
		if (session != null) {
			
			// parse the session subject as the primary subject
			Subject primarySubject = session.getSubject();
			if (primarySubject != null) {
				subjects.add(primarySubject);
				
				// depending on the primary subject, can add the authenticated symbolic user
				if (! primarySubject.getValue().equals(Constants.SUBJECT_PUBLIC) ) {
					subjects.add(buildSubject(Constants.SUBJECT_AUTHENTICATED_USER));
					
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
		return subjects;
	}
	

	
	/**
	 * A recursive method to traverse the equivalent-identity relationships
	 * and to handle the transitive nature of group membership and verified status.
	 * 
	 * Note: this is a low-level function that does not provide a complete list
	 * of authorized subjects for a given SubjectInfo, so callers beware.
	 */
	// TODO: future optimization might be to replace SubjectInfo with an indexed
	// map of the Person objects contained by it, but probably not worth the setup
	// cost when # of Persons is small.
	public static void findPersonsSubjects(Set<Subject> foundSubjects, SubjectInfo subjectInfo, Subject targetSubject) {

		// add targetSubject - not all target subjects will have Person objects
		// for example the so-called "legacy" identities
		// so always add it
		foundSubjects.add(targetSubject);
		
		
		// setting this up for subsequent searches in the loop
		List<Group> groupList = null;
		if (subjectInfo != null) {
			groupList = subjectInfo.getGroupList();
		}
		
		if (subjectInfo != null && subjectInfo.getPersonList() != null) {
			for (Person p: subjectInfo.getPersonList()) {
				if (p.getSubject().equals(targetSubject)) {

					logger.debug("traversing person: " + targetSubject.getValue());
					
					// check verification status of this identity
					if (p.getVerified() != null && p.getVerified()) {
						foundSubjects.add(buildSubject(Constants.SUBJECT_VERIFIED_USER));
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
					List<Subject> equivList = p.getEquivalentIdentityList();
					if (equivList != null) {
						for (Subject eqId : equivList) {
							// recursive call for un-examined targetSubjects
							if (! foundSubjects.contains(eqId)) {
								findPersonsSubjects(foundSubjects, subjectInfo, eqId);
							}
						}
					}
					
				}
			}
		}
	}
	
	
	
	/**
	 *
	 * Queries the systemMetadata to see if one of the given subjects 
	 * is allowed the specified permission against the given systemMetadata
	 * 
	 * @param subjectSet - the collection of subjects, assumed to represent the subjects
	 *                   of a session
	 * @param requestedPerm -  the permission that is requested authorization for 
	 * @param systemMetadata - the systemMetadata of the target object to test
	 * @return - true if one of the subjects is authorized for the given permission
	 *           otherwise false
	 */
	public static boolean isAuthorized(Collection<Subject> subjectSet, Permission requestedPerm,
			SystemMetadata systemMetadata) 
	{
	
		// take care of exceptional case first
		if (CollectionUtils.isEmpty(subjectSet))
			return false;


		// if rightsHolder is one of subjects, can return allowed (true)
		// without further checks
		if (subjectSet.contains(systemMetadata.getRightsHolder()))
			return true;
		

		// otherwise check the access rules
		boolean allowed = false;
		try {
			List<AccessRule> allows = systemMetadata.getAccessPolicy().getAllowList();
			if (allows != null) {
				search:
					for (AccessRule accessRule: allows) {
						if (accessRule.sizePermissionList() > 0) {
							for (Subject s: subjectSet) {
								if (accessRule.getSubjectList().contains(s)) {
									allowed = comparePermissions(requestedPerm, accessRule.getPermissionList());
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
	    
	    return allowed;	    
	}
	
	/*
	 * a comparison algorithm for hierarchical permissions (WRITE implies READ, and CHANGE
	 * implies WRITE and READ).
	 */
	public static boolean comparePermissions(Permission requested, Collection<Permission> allowed) 
	{
		if (CollectionUtils.isEmpty(allowed))
			return false;
		
		if (requested.equals(Permission.READ)) 
			// if this far, then request READ is always true
			return true;
		
		if (allowed.contains(Permission.CHANGE_PERMISSION))
			return true;
		
		// remaining cases are request WRITE or CHANGE vs. READ and/or WRITE
		// (only WRITE vs. WRITE gives true)
		if (requested.equals(Permission.WRITE) && allowed.contains(Permission.WRITE))
				return true;
		
		return false;
		
	}
}
