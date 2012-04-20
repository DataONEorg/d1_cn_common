/**
 * This work was created by participants in the DataONE project, and is
 * jointly copyrighted by participating institutions in DataONE. For 
 * more information on DataONE, see our web site at http://dataone.org.
 *
 *   Copyright ${year}
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 * 
 * $Id$
 */

package org.dataone.service.types.v1.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import org.dataone.service.types.v1.AccessPolicy;
import org.dataone.service.types.v1.AccessRule;
import org.dataone.service.types.v1.Permission;
import org.dataone.service.types.v1.Subject;

/**
 * A helper class to simplify the use of AccessRules and AccessPolicies
 * consisting of static factory methods for creating these nested objects
 * (accessPolicy has accessRules has Permissions and Subjects, each subject has a value)
 * <p>
 * Developer Notes: Currently does not work with other authentication objects (Session, 
 * Person, Group, SubjectList), but nothing  precludes this.  You'll just want to be
 * careful not to duplicate functionality in d1_libclient_java's 
 * org.dataone.client.auth.CertificateManager.
 * 
 * @author rnahf
 *
 */
public class AccessUtil {

	/**
	 * creates an AccessRule containing the items specified in the parameters.
	 * If you have Lists of these things already, consider creating directly
	 * and using ar.setPermissions(List\<Permission\> permissions), for example.
	 * 
	 * @param subjects - an array of Subject objects
	 * @param permissions - an array of Permission objects (enumeration elements)
	 * @return an AccessRule
	 */
	public static AccessRule createAccessRule(Subject[] subjects, Permission[] permissions)
	{
		AccessRule ar = new AccessRule();
		for (Subject subject: subjects) {
			ar.addSubject(subject);
		}
		
		for (Permission permission : permissions) {
			ar.addPermission(permission);
		}
		return ar;
	}

	/**
	 * creates an AccessRule containing the items specified in the parameters.
	 * If you have Lists of these things already, consider creating directly
	 * and using ar.setPermissions(List\<Permission\> permissions), for example.
	 * 
	 * @param subjects - an array of Strings where each string becomes the value of a new Subject
	 * @param permissions - an array of Permission objects (enumeration elements)
	 * @return an AccessRule instance
	 */
	public static AccessRule createAccessRule(String[] subjectStrings, Permission[] permissions)
	{
		AccessRule ar = new AccessRule();
		for (String subjectStr: subjectStrings) {
			Subject s = new Subject();
			s.setValue(subjectStr);
			ar.addSubject(s);
		}
		for (Permission permission : permissions) {
			ar.addPermission(permission);
		}
		return ar;
	}

	/**
	 * creates an AccessPolicy with a single AccessRule, and the AccessRule composed
	 * of Subjects and Permissions specified in the parameters.
	 * 
	 * @param subjectStrings - an array of Strings where each string becomes the value of a new Subject
	 * @param permissions - an array of Permission objects (enumeration elements)
	 * @return an AccessPolicy instance
	 */
	public static AccessPolicy createSingleRuleAccessPolicy(String[] subjectStrings, Permission[] permissions)
	{
		AccessPolicy ap = new AccessPolicy();
		ap.addAllow(createAccessRule(subjectStrings,permissions));
		return ap;
	}

	
	/**
	 * creates a java-List of Subject objects from the given String array.  The
	 * utility of this method is for manually created AccessRules and other 
	 * dataONE objects that take a List of Subjects.
	 * @param subjectStrings - an array of Strings where each string becomes the value of a new Subject
	 * @return a List\<Subject\> object
	 */
	public static List<Subject> createSubjectList(String[] subjectStrings) 
	{
		Vector<Subject> subjectList = new Vector<Subject>();
		for (int i = 0; i < subjectStrings.length; i++) {
			Subject s = new Subject();
			s.setValue(subjectStrings[i]);
			subjectList.add(s);
		}
		return subjectList;
	}
	
	
	/**
	 * Dead-simple convenience method for creating a standard permission set
	 * of Read and Write.
	 * @return a Permission array
	 */
	public static Permission[] createReadWritePermissions()
	{
		return new Permission[]{Permission.READ, Permission.WRITE};
	}

	
	/**
	 * Dead-simple convenience method for creating a standard permission set
	 * of Read and Write and ChangePermission
	 * @return a Permission array
	 */
	public static Permission[] createReadWriteChangePermissions()
	{
		return new Permission[]{Permission.READ, Permission.WRITE, Permission.CHANGE_PERMISSION};
	}

	
	/**
	 * Dead-simple convenience method for creating a standard permission set
	 * of Read and Write.
	 * @return a List of Permission objects
	 */
	public static List<Permission> createReadWritePermissionList()
	{
		Permission[] p = createReadWritePermissions();
		return Arrays.asList(p);
	}
	
	
	/**
	 * Dead-simple convenience method for creating a standard permission set
	 * of Read and Write and ChangePermission
	 * @return a List of Permission objects
	 */
	public static List<Permission> createReadWriteChangePermissionList()
	{
		Permission[] p = createReadWriteChangePermissions();
		return Arrays.asList(p);
	}
	
	public static HashMap<Subject,Set<Permission>> getPermissionMap(AccessPolicy policy)
	{
		HashMap<Subject,Set<Permission>> permissionMap = new HashMap<Subject,Set<Permission>>();
		if (policy != null) {
			for(int i=0; i<policy.sizeAllowList(); i++) {
				AccessRule ar = policy.getAllow(i);
				for(int j=0; j<ar.sizeSubjectList(); j++) {
					Subject s = ar.getSubject(j);
					TreeSet<Permission> perms = new TreeSet<Permission>();
					for(int k=0; k<ar.sizePermissionList(); k++) {
						perms.add(ar.getPermission(k));
					}
					if (permissionMap.containsKey(s)) {
						permissionMap.get(s).addAll(perms);
					} else {
						permissionMap.put(s, perms);
					}
				}
			}	
		}
		return permissionMap;
	}
	
	/**
	 * creates an AccessPolicy instance where each contained AccessRule consists of
	 * one subject and that subject's permissions. 
	 * @param pmap - a permissionMap as generated by AccessUtil.getPermissionMap(accessPolicy)
	 * @return an AccessPolicy instance.
	 */
	public static AccessPolicy buildAccessPolicy(HashMap<Subject,Set<Permission>> pmap)
	{
		AccessPolicy accessPolicy = new AccessPolicy();
		Iterator<Subject> it = pmap.keySet().iterator();
		while (it.hasNext()) {
			AccessRule rule = new AccessRule();
			Subject subject = it.next();
			rule.addSubject(subject);
			rule.setPermissionList(new ArrayList<Permission>(pmap.get(subject)));
			accessPolicy.addAllow(rule);
		}
		return accessPolicy;
	}
}
