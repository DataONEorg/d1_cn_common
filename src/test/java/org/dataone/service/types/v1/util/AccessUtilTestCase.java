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
import org.dataone.service.util.Constants;
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
		s.setValue(Constants.SUBJECT_PUBLIC);
		sl.add(s);
		ar.setSubjectList(sl);
		ar.setPermissionList(Arrays.asList(new Permission[]{Permission.READ,Permission.WRITE}));
		ap.addAllow(ar);
		HashMap<Subject,Set<Permission>> permMap = AccessUtil.getPermissionMap(ap);
		assertTrue("subject finds itself", permMap.containsKey(s));
		
		Subject ss = new Subject();
		ss.setValue(Constants.SUBJECT_PUBLIC);
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
