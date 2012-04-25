package org.dataone.service.types.v1.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.dataone.service.types.v1.Group;
import org.dataone.service.types.v1.Permission;
import org.dataone.service.types.v1.Person;
import org.dataone.service.types.v1.Session;
import org.dataone.service.types.v1.Subject;
import org.dataone.service.types.v1.SubjectInfo;
import org.dataone.service.types.v1.SystemMetadata;
import org.dataone.service.util.Constants;
import org.junit.Before;
import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;

public class AuthUtilsTestCase {

	private Session standardSession = null;
	
	private Subject publick = null; 
	private Subject authenticated = null; 
	private Subject verified = null; 
	

	private Subject buildSubject(String cn) {
		Subject subject = new Subject();
		subject.setValue("CN=" + cn + ",DC=something,DC=org");
		return subject;
	}
	
	private Person buildTestPerson(String subjectString) {
		Person p = new Person();
		p.setSubject(buildSubject(subjectString));
		return p;
	}
	
	private Group buildTestGroup(String subjectString) {
		Group g = new Group();
		g.setSubject(buildSubject(subjectString));
		return g;
	}
	
	
	@Before
	public void setupSymbolicSubjects() {
		publick = new Subject();
		publick.setValue(Constants.SUBJECT_PUBLIC);
		
		authenticated = new Subject();
		authenticated.setValue(Constants.SUBJECT_AUTHENTICATED_USER);
		
		verified = new Subject();
		verified.setValue(Constants.SUBJECT_VERIFIED_USER);
	}
	
	
	@Before
	public void createSession() {
		if (standardSession == null) {
			Session ses = new Session();
			Subject subjectX = buildSubject("x");
			ses.setSubject(subjectX);

			SubjectInfo si = new SubjectInfo();
			Person x = buildTestPerson("x");
			Person y = buildTestPerson("y");
			Person z = buildTestPerson("z"); 
			z.setVerified(true);
			x.addEquivalentIdentity(y.getSubject());
			x.addEquivalentIdentity(z.getSubject());
			si.addPerson(x);		
			si.addPerson(y);		
			si.addPerson(z);		

			ses.setSubjectInfo(si);
			standardSession = ses;
		}
	}
	
	
	@Test
	public void testAuthorizedClientSubjects_Public() {
		
		
		Subject[] subjects = AuthUtils.authorizedClientSubjects(standardSession);
		List<Subject> subjectList = (List<Subject>)Arrays.asList(subjects);
		
		
		assertTrue("public should always appear", subjectList.contains(publick));
	}

	
	@Test
	public void testAuthorizedClientSubjects_Authenticated() {
		Subject[] subjects = AuthUtils.authorizedClientSubjects(standardSession);
		List<Subject> subjectList = (List<Subject>)Arrays.asList(subjects);
		
	
		assertTrue("authenticated should be in the list", subjectList.contains(authenticated));
	}

	
	@Test
	public void testAuthorizedClientSubjects_Verified() {
		
		
		Subject[] subjects = AuthUtils.authorizedClientSubjects(standardSession);
		List<Subject> subjectList = (List<Subject>)Arrays.asList(subjects);
	
		assertTrue("verified appears because an equiv identity is verified", subjectList.contains(verified));
	}

	@Test
	public void testAuthorizedClientSubjects_EquivalentIDs() {
		
		
		Subject[] subjects = AuthUtils.authorizedClientSubjects(standardSession);
		List<Subject> subjectList = (List<Subject>)Arrays.asList(subjects);
	
		assertTrue("subject list should contain x", subjectList.contains(buildSubject("x")));
		assertTrue("subject list should contain y", subjectList.contains(buildSubject("y")));
		assertTrue("subject list should contain z", subjectList.contains(buildSubject("z")));
	
	}
	
	
	@Test
	public void testAuthorizedClientSubjects_DaisyChainEquivalentIDs() {
		
		Session ses = new Session();
		Subject subjectX = buildSubject("x");
		ses.setSubject(subjectX);

		SubjectInfo si = new SubjectInfo();
		Person x = buildTestPerson("x");
		Person y = buildTestPerson("y");
		Person z = buildTestPerson("z"); 
		z.setVerified(true);
		x.addEquivalentIdentity(y.getSubject());
		y.addEquivalentIdentity(z.getSubject());
		si.addPerson(x);		
		si.addPerson(y);		
		si.addPerson(z);		

		ses.setSubjectInfo(si);
		Subject[] subjects = AuthUtils.authorizedClientSubjects(standardSession);
		List<Subject> subjectList = (List<Subject>)Arrays.asList(subjects);
	
		assertTrue("subject list contains x", subjectList.contains(buildSubject("x")));
		assertTrue("subject list contains y", subjectList.contains(buildSubject("y")));
		assertTrue("subject list contains z", subjectList.contains(buildSubject("z")));
	
	}
	
	@Test
	public void testAuthorizedClientSubjects_DaisyChainVerified() {
		
		Session ses = new Session();
		Subject subjectX = buildSubject("x");
		ses.setSubject(subjectX);

		SubjectInfo si = new SubjectInfo();
		Person x = buildTestPerson("x");
		Person y = buildTestPerson("y");
		Person z = buildTestPerson("z"); 
		z.setVerified(true);
		x.addEquivalentIdentity(y.getSubject());
		y.addEquivalentIdentity(z.getSubject());
		si.addPerson(x);		
		si.addPerson(y);		
		si.addPerson(z);		

		ses.setSubjectInfo(si);
		Subject[] subjects = AuthUtils.authorizedClientSubjects(ses);
		List<Subject> subjectList = (List<Subject>)Arrays.asList(subjects);
	
		assertTrue("subject list contains x", subjectList.contains(verified));
	}

	
	@Test
	public void testAuthorizedClientSubjects_EmptySession_isPublic() {
		
		Session ses = new Session();
		
		Subject[] subjects = AuthUtils.authorizedClientSubjects(ses);
		List<Subject> subjectList = (List<Subject>)Arrays.asList(subjects);
	
		assertTrue("empty session should revert to public",
				subjectList.contains(publick));
		assertTrue("empty session should have only 1 subject (public)",
				subjectList.size() == 1);
	}
	
	@Test
	public void testAuthorizedClientSubjects_Null_isPublic() {
		
		
		Subject[] subjects = AuthUtils.authorizedClientSubjects(null);
		List<Subject> subjectList = (List<Subject>)Arrays.asList(subjects);
	
		assertTrue("null session should revert to public",
				subjectList.contains(publick));
		assertTrue("null session should have only 1 subject (public)",
				subjectList.size() == 1);
	}
	
	
	
	@Test
	public void testAuthorizedClientSubjects_PublicNotAuthenticated() {
		
		Session ses = new Session();
		ses.setSubject(publick);

		SubjectInfo si = new SubjectInfo();
		Person x = buildTestPerson("x");
		Person y = buildTestPerson("y");
		Person z = buildTestPerson("z"); 

		ses.setSubjectInfo(si);
		Subject[] subjects = AuthUtils.authorizedClientSubjects(ses);
		List<Subject> subjectList = (List<Subject>)Arrays.asList(subjects);
	
		assertFalse("public subject in session, should not have authenticated",
				subjectList.contains(authenticated));
	}
	
	
	@Test
	public void testAuthorizedClientSubjects_GroupTransitive() {
		
		Session ses = new Session();
		ses.setSubject(buildSubject("x"));

		SubjectInfo si = new SubjectInfo();
		Person x = buildTestPerson("x");
		x.addIsMemberOf(buildSubject("groupA"));
		Person y = buildTestPerson("y");
		x.addEquivalentIdentity(buildSubject("y"));
		y.addEquivalentIdentity(buildSubject("x"));		
		y.addIsMemberOf(buildSubject("groupB"));
		
		Person z = buildTestPerson("z");
		x.addEquivalentIdentity(buildSubject("z"));
		z.addEquivalentIdentity(buildSubject("x"));	
		z.addIsMemberOf(buildSubject("groupC"));

		si.addPerson(x);
		si.addPerson(y); 
		si.addPerson(z);
		
		si.addGroup(buildTestGroup("groupA"));
		si.addGroup(buildTestGroup("groupB"));
		si.addGroup(buildTestGroup("groupC"));
		ses.setSubjectInfo(si);
		
		Subject[] subjects = AuthUtils.authorizedClientSubjects(ses);
		List<Subject> subjectList = (List<Subject>)Arrays.asList(subjects);
	
		assertTrue("subject list should contain groupA", subjectList.contains(buildSubject("groupA")));
		assertTrue("subject list should contain groupB", subjectList.contains(buildSubject("groupB")));
		assertTrue("subject list should contain groupC", subjectList.contains(buildSubject("groupC")));
	}
	
	
	
	
	
	@Test
	public void testAuthorizedClientSubjects_GroupTransitive_DaisyChainEquivID() {
		
		Session ses = new Session();
		ses.setSubject(buildSubject("x"));

		SubjectInfo si = new SubjectInfo();
		Person x = buildTestPerson("x");
		Group a = buildTestGroup("groupA");
		a.addHasMember(x.getSubject());
		x.addIsMemberOf(a.getSubject());
		
		Person y = buildTestPerson("y");
		Group b = buildTestGroup("groupB");
		b.addHasMember(y.getSubject());
		y.addIsMemberOf(b.getSubject());
		
		
		x.addEquivalentIdentity(buildSubject("y"));
		y.addEquivalentIdentity(buildSubject("x"));		
		
		
		Person z = buildTestPerson("z");
		Group c = buildTestGroup("groupC");
		c.addHasMember(z.getSubject());
		z.addIsMemberOf(c.getSubject());
		
		
		y.addEquivalentIdentity(buildSubject("z"));
		z.addEquivalentIdentity(buildSubject("y"));	
		

		si.addPerson(x);
		si.addPerson(y); 
		si.addPerson(z);	
		
		si.addGroup(a);
		si.addGroup(b);
		si.addGroup(c);
		
		ses.setSubjectInfo(si);
		
		Subject[] subjects = AuthUtils.authorizedClientSubjects(ses);
		List<Subject> subjectList = (List<Subject>)Arrays.asList(subjects);
	
		assertTrue("subject list should contain groupA", subjectList.contains(buildSubject("groupA")));
		assertTrue("subject list should contain groupB", subjectList.contains(buildSubject("groupB")));
		assertTrue("subject list should contain groupC", subjectList.contains(buildSubject("groupC")));
	}
	
	
	/**
	 * setting up a circular equivalent identity graph, to make sure we don't get stuck
	 */
	@Test
	public void testAuthorizedClientSubjects_RecursionInfiniteLoopTest() {
		
		Session ses = new Session();
		ses.setSubject(buildSubject("x"));

		SubjectInfo si = new SubjectInfo();
		Person x = buildTestPerson("x");
		Group a = buildTestGroup("groupA");
		a.addHasMember(x.getSubject());
		x.addIsMemberOf(a.getSubject());
		
		Person y = buildTestPerson("y");
		Group b = buildTestGroup("groupB");
		b.addHasMember(y.getSubject());
		y.addIsMemberOf(b.getSubject());
		
		
		x.addEquivalentIdentity(buildSubject("y"));
		y.addEquivalentIdentity(buildSubject("x"));		
		
		
		Person z = buildTestPerson("z");
		Group c = buildTestGroup("groupC");
		c.addHasMember(z.getSubject());
		z.addIsMemberOf(c.getSubject());
		
		
		y.addEquivalentIdentity(buildSubject("z"));
		z.addEquivalentIdentity(buildSubject("y"));	
		
		
		Person w = buildTestPerson("w");
		Group d = buildTestGroup("groupD");
		d.addHasMember(w.getSubject());
		w.addIsMemberOf(d.getSubject());
		
		
		z.addEquivalentIdentity(buildSubject("w"));
		w.addEquivalentIdentity(buildSubject("z"));	
		
		x.addEquivalentIdentity(buildSubject("w"));
		w.addEquivalentIdentity(buildSubject("x"));	

		si.addPerson(w);
		si.addPerson(x);
		si.addPerson(y); 
		si.addPerson(z);	
		
		si.addGroup(a);
		si.addGroup(b);
		si.addGroup(c);
		si.addGroup(d);
		
		ses.setSubjectInfo(si);
		
		Subject[] subjects = AuthUtils.authorizedClientSubjects(ses);
		List<Subject> subjectList = (List<Subject>)Arrays.asList(subjects);
	
		assertTrue("subject list should contain groupA", subjectList.contains(buildSubject("groupA")));
		assertTrue("subject list should contain groupB", subjectList.contains(buildSubject("groupB")));
		assertTrue("subject list should contain groupC", subjectList.contains(buildSubject("groupC")));
		assertTrue("subject list should contain groupC", subjectList.contains(buildSubject("groupD")));
	}
	
	
	@Test
	public void testIsAuthorized_AccessPolicy() 
	{
		SystemMetadata sysmeta = new SystemMetadata();
		
		sysmeta.setAccessPolicy(
				
				AccessUtil.createSingleRuleAccessPolicy(
				new String[]{buildSubject("x").getValue(), buildSubject("y").getValue()},
				new Permission[]{ Permission.WRITE}));
		
		sysmeta.setRightsHolder(buildSubject("qq"));
		
		Subject[] subjects = new Subject[] { buildSubject("z"), buildSubject("y"), buildSubject("x") };
		assertTrue("x should be able to read the object", AuthUtils.isAuthorized(subjects, Permission.READ, sysmeta));		
		assertTrue("x should be able to write the object", AuthUtils.isAuthorized(subjects, Permission.WRITE, sysmeta));		
		assertFalse("x should NOT be able to change the object", AuthUtils.isAuthorized(subjects, Permission.CHANGE_PERMISSION, sysmeta));	
		
		assertFalse("testRightsHolder should be able to change the object", AuthUtils.isAuthorized(subjects, Permission.CHANGE_PERMISSION, sysmeta));
	}
	
	
	@Test
	public void testIsAuthorized_RightsHolder() 
	{
		SystemMetadata sysmeta = new SystemMetadata();
		
		sysmeta.setAccessPolicy(
				
				AccessUtil.createSingleRuleAccessPolicy(
				new String[]{buildSubject("x").getValue(), buildSubject("y").getValue()},
				new Permission[]{ Permission.WRITE}));
		
		sysmeta.setRightsHolder(buildSubject("testRightsHolder"));
		
		Subject[] subjects = new Subject[] { buildSubject("testRightsHolder") };
	
		assertTrue("testRightsHolder should be able to change the object", AuthUtils.isAuthorized(subjects, Permission.CHANGE_PERMISSION, sysmeta));
	}
}