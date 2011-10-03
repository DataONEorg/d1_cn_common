package org.dataone.service.types.v1.util;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import org.dataone.service.types.v1.AccessPolicy;
import org.dataone.service.types.v1.AccessRule;
import org.dataone.service.types.v1.Permission;
import org.dataone.service.types.v1.Subject;

/**
 * A helper class to simplify the use of AccessRules and AccessPolicies
 * consisting of mostly factory methods for creating these nested objects
 * (accessPolicy has accessRules has Permissions and Subjects, each subject has a value)
 * <p>
 * Currently does not work with other authentication objects (Session, Person, 
 * Group, SubjectList), but nothing  precludes this.  Just want to be careful not
 * to duplicate functionality in d1_libclient_java's org.dataone.client.auth.CertificateManager.
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
	 * @param subjects - an array of Strings to be used to build Subjects
	 * @param permissions - an array of Permission objects (enumeration elements)
	 * @return an AccessRule
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

	
	public static AccessPolicy createSingleRuleAccessPolicy(String[] subjectStrings, Permission[] permissions)
	{
		AccessPolicy ap = new AccessPolicy();
		ap.addAllow(createAccessRule(subjectStrings,permissions));
		return ap;
	}

	
	
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
	
	
	
	public static Permission[] createReadWritePermissions()
	{
		return new Permission[]{Permission.READ, Permission.WRITE};
	}
	
	public static Permission[] createReadWriteChangePermissions()
	{
		return new Permission[]{Permission.READ, Permission.WRITE, Permission.CHANGE_PERMISSION};
	}
	
	public static List<Permission> createReadWritePermissionList()
	{
		Permission[] p = createReadWritePermissions();
		return Arrays.asList(p);
	}
	
	public static List<Permission> createReadWriteChangePermissionList()
	{
		Permission[] p = createReadWriteChangePermissions();
		return Arrays.asList(p);
	}
}
