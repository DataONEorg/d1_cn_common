package org.dataone.service.types.v1.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.dataone.service.types.v1.AccessPolicy;
import org.dataone.service.types.v1.AccessRule;
import org.dataone.service.types.v1.Permission;
import org.dataone.service.types.v1.Subject;
import org.dataone.service.types.v1.util.AccessUtil;
import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;

public class AccessUtilTestCase {

//	@Test
	public void testCreateAccessRule_SubjectArrayPermissionArray() {
		AccessRule ar = AccessUtil.createAccessRule(new String[] {"a","b"}, 
				new Permission[] {Permission.READ, Permission.WRITE});

		Subject subjectA = new Subject();
		subjectA.setValue("a");
		
//		assertTrue(ar.getSubjectList().contains(subjectA));
		
		assertTrue(ar.getPermissionList().contains(Permission.READ));
	}

//	@Test
	public void testCreateAccessRule_StringArrayPermissionArray() {
		fail("Not yet implemented");
	}

//	@Test
	public void testCreateSingleRuleAccessPolicy() {
		fail("Not yet implemented");
	}

//	@Test
	public void testCreateSubjectList() {
		fail("Not yet implemented");
	}

//	@Test
	public void testCreateReadWritePermissions() {
		fail("Not yet implemented");
	}

//	@Test
	public void testCreateReadWriteChangePermissions() {
		fail("Not yet implemented");
	}

//	@Test
	public void testCreateReadWritePermissionList() {
		fail("Not yet implemented");
	}

//	@Test
	public void testCreateReadWriteChangePermissionList() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPermissionMap() {
		AccessPolicy ap = new AccessPolicy();
		AccessRule ar = new AccessRule();
		List<Subject> sl = new ArrayList<Subject>();
		Subject s = new Subject();
		s.setValue("s");
		sl.add(s);
		ar.setSubjectList(sl);
		ar.setPermissionList(Arrays.asList(new Permission[]{Permission.READ,Permission.WRITE}));
		ap.addAllow(ar);
		HashMap<Subject,Set<Permission>> permMap = AccessUtil.getPermissionMap(ap);
		assertTrue("subject finds itself", permMap.containsKey(s));
		
		Subject ss = new Subject();
		ss.setValue("s");
		assertTrue("subject finds comparable self", permMap.containsKey(ss));
		
		assertTrue("can find all in permission set", 
				permMap.get(ss).containsAll(Arrays.asList(new Permission[]{Permission.READ})));
		assertTrue("can find all in permission set", 
				permMap.get(ss).containsAll(Arrays.asList(new Permission[]{Permission.WRITE,Permission.READ})));
		
	}

//	@Test
	public void testBuildAccessPolicy() {
		fail("Not yet implemented");
	}

}
