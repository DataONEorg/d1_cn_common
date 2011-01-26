package org.dataone.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
		assertEquals(url.toString(),expected);
	}

	@Test
	public void testJoinWith() {
		D1Url url = new D1Url(testBaseUrlNoEndingSlash,testResource);
		String expected = testBaseUrlNoEndingSlash + "/" + testResource;
		assertEquals(url.toString(),expected);
	}

	
	
	@Test
	public void testPathAdditions() {
		D1Url url = new D1Url(testBaseUrl,testResource);
		url.addNextPathElement("pathSeg1");
		url.addNextPathElement("pathSeg2");
		String expected = testBaseUrl + testResource + "/pathSeg1/pathSeg2";
		assertEquals(url.toString(),expected);
	}
	
	@Test
	public void testQueryAdditions() {
		D1Url url = new D1Url(testBaseUrl,testResource);
		url.addNonEmptyParamPair("p1", "v1");
		url.addNonEmptyParamPair("p2", "v2");
		String expected = testBaseUrl + testResource + "?p1=v1&p2=v2";
		
		assertEquals(url.toString(),expected);
	}
	
	@Test
	public void testSingleQueryAdditions() {
		D1Url url = new D1Url(testBaseUrl,testResource);
		url.addNonEmptyParamPair("p1", "v1");
		String expected = testBaseUrl + testResource + "?p1=v1";
		
		assertEquals(url.toString(),expected);
	}
	
	@Test
	public void testNonPairQueryAddition() {
		D1Url url = new D1Url(testBaseUrl,testResource);
		url.addNonEmptyParamPair("p1", "v1");
		url.addNonEmptyParam("bareValue");
		String expected = testBaseUrl + testResource + "?p1=v1&bareValue";
		
		assertEquals(url.toString(),expected);
	}
	
	@Test
	public void testPathAndQuery() {
		D1Url url = new D1Url(testBaseUrl,testResource);
		url.addNextPathElement("pathSeg1");
		url.addNextPathElement("pathSeg2");
		url.addNonEmptyParamPair("p1", "v1");
		url.addNonEmptyParamPair("p2", "v2");
		String expected = testBaseUrl + testResource + "/pathSeg1/pathSeg2" + "?p1=v1&p2=v2";
		
		assertEquals(url.toString(),expected);
	}

	@Test
	public void testAsciiEncoding() {
		D1Url url = new D1Url(testBaseUrl,testResource);
		url.addNextPathElement("path Seg");
		url.addNonEmptyParamPair("p 1", "v 1");
		url.addNonEmptyParamPair("p 2", "v 2");
		String expected = testBaseUrl + testResource + "/path%20Seg" + "?p%201=v%201&p%202=v%202";
		
		assertEquals(url.toString(),expected);
	}

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
}
