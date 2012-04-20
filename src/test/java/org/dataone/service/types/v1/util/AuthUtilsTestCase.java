package org.dataone.service.types.v1.util;

import static org.junit.Assert.*;

import org.dataone.service.types.v1.Subject;
import org.junit.Test;

public class AuthUtilsTestCase {

	@Test
	public void test() {
//		fail("Not yet implemented");
	}

	private Subject buildSubject(String s) {
		Subject subject = new Subject();
		subject.setValue(s);
		return subject;
	}
	
	public void testAuthorizedClientSubject() {
	 	
	}
}
