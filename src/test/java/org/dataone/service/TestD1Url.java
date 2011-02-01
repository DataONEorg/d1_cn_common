package org.dataone.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Test;


public class TestD1Url {
	private static String testBaseUrl = "http://foo.com/";
	private static String testBaseUrlNoEndingSlash = "http://foo.com";
	private static String testResource = "myResource";

	@Test
	public void testConstructorBaseUrlOnly() {
		D1Url url = new D1Url(testBaseUrl);
		assertEquals(url.toString(),testBaseUrl);
	}

	@Test
	public void testConstructorBaseUrlAndResource() {
		D1Url url = new D1Url(testBaseUrl,testResource);
		String expected = testBaseUrl + testResource;
		assertEquals(expected, url.toString());
	}

	@Test
	public void testJoinWith() {
		D1Url url = new D1Url(testBaseUrlNoEndingSlash,testResource);
		String expected = testBaseUrlNoEndingSlash + "/" + testResource;
		assertEquals(expected, url.toString());
	}


	@Test
	public void testPathAdditions() {
		D1Url url = new D1Url(testBaseUrl,testResource);
		url.addNextPathElement("pathSeg1");
		url.addNextPathElement("pathSeg2");
		String expected = testBaseUrl + testResource + "/pathSeg1/pathSeg2";
		assertEquals(expected, url.toString());
	}

	// test URL query parameters methods

	@Test
	public void testUrlQuery_NonPair() {
		D1Url url = new D1Url(testBaseUrl,testResource);
		url.addNonEmptyParam("bareValue1");
		url.addNonEmptyParam("bareValue2");
		String expected = testBaseUrl + testResource + "?bareValue1&bareValue2";

		assertEquals(expected, url.toString());
	}

	@Test
	public void testUrlQuery_NonPair_Null() {
		D1Url url = new D1Url(testBaseUrl,testResource);
		url.addNonEmptyParam((String) null);
		String expected = testBaseUrl + testResource;

		assertEquals(expected, url.toString());
	}




	@Test
	public void testUrlQuery_ParamPair_String() {
		D1Url url = new D1Url(testBaseUrl,testResource);
		url.addNonEmptyParamPair("p1", "v1");
		url.addNonEmptyParamPair("p2", "v2");
		String expected = testBaseUrl + testResource + "?p1=v1&p2=v2";

		assertEquals(expected, url.toString());
	}


	@Test
	public void testUrlQuery_ParamPair_Date() {
		D1Url url = new D1Url(testBaseUrl,testResource);
		Date d = new Date();
		url.addDateParamPair("p1",d);
		String expected = testBaseUrl + testResource + "?p1=";
		System.out.println("Expected begins with: " + expected);
		System.out.println("              actual: " + url.toString());
		assertTrue("dates match", url.toString().startsWith(expected) );
	}


	@Test
	public void testUrlQuery_ParamPair_Integer() {
		D1Url url = new D1Url(testBaseUrl,testResource);
		Integer i = new Integer(37);
		url.addNonEmptyParamPair("p1", i);
		String expected = testBaseUrl + testResource + "?p1=37";

		assertEquals(expected, url.toString());
	}


	@Test
	public void testUrlQuery_ParamPair_String_Null() {
		D1Url url = new D1Url(testBaseUrl,testResource);
		url.addNonEmptyParamPair("p1", (String) null);
		url.addNonEmptyParamPair(null, "v2");
		String expected = testBaseUrl + testResource;

		assertEquals(expected, url.toString());
	}

	@Test
	public void testUrlQuery_ParamPair_Date_Null() {
		D1Url url = new D1Url(testBaseUrl,testResource);
		Date d = null;
		url.addDateParamPair("p1",d);
		String expected = testBaseUrl + testResource;

		assertEquals(expected, url.toString());
	}

	@Test
	public void testUrlQuery_ParamPair_Integer_Null() {
		D1Url url = new D1Url(testBaseUrl,testResource);
		Integer i = null;
		url.addNonEmptyParamPair("p1", i);
		String expected = testBaseUrl + testResource;;

		assertEquals(expected, url.toString());
	}


	
	@Test
	public void testUrlQuery_PairAndNonPair() {
		D1Url url = new D1Url(testBaseUrl,testResource);
		url.addNonEmptyParamPair("p1", "v1");
		url.addNonEmptyParam("bareValue");
		String expected = testBaseUrl + testResource + "?p1=v1&bareValue";

		assertEquals(expected, url.toString());
	}


	@Test
	public void testPathAndQuery() {
		D1Url url = new D1Url(testBaseUrl,testResource);
		url.addNextPathElement("pathSeg1");
		url.addNextPathElement("pathSeg2");
		url.addNonEmptyParamPair("p1", "v1");
		url.addNonEmptyParamPair("p2", "v2");
		String expected = testBaseUrl + testResource + "/pathSeg1/pathSeg2" + "?p1=v1&p2=v2";

		assertEquals(expected, url.toString());
	}


	@Test
	public void testAsciiEncoding() {
		D1Url url = new D1Url(testBaseUrl,testResource);
		url.addNextPathElement("path Seg");
		url.addNonEmptyParamPair("p 1", "v 1");
		url.addNonEmptyParamPair("p 2", "v 2");
		String expected = testBaseUrl + testResource + "/path%20Seg" + "?p%201=v%201&p%202=v%202";

		assertEquals(expected, url.toString());
	}

	// null value checking


	@Test
	public void testEmptyBaseUrl() {
		try {
			D1Url url = new D1Url("");	
			fail("Exception should have been thrown");
		} catch (IllegalArgumentException e) {
			assertTrue("Correct exception thrown", true);
		}
	}

	@Test
	public void testWhitespaceBaseUrl() {
		try {
			D1Url url = new D1Url("      	");	
			fail("Exception should have been thrown");
		} catch (IllegalArgumentException e) {
			assertTrue("Correct exception thrown", true);
		}
	}

	@Test
	public void testNullBaseUrl() {
		try {
			D1Url url = new D1Url(null);	
			fail("Exception should have been thrown");
		} catch (IllegalArgumentException e) {
			assertTrue("Correct exception thrown", true);
		}
	}
	
	@Test
	public void testNullPathElement() {
		try {
			D1Url url = new D1Url(testBaseUrl,testResource);
			url.addNextPathElement(" ");
			fail("Exception should have been thrown");
		} catch (IllegalArgumentException e) {
			assertTrue("Correct exception thrown", true);
		}
	}
}
