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

package org.dataone.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;


public class D1Url1TestCase {
	private static Logger logger = Logger.getLogger(D1Url1TestCase.class);
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
		logger.info("Expected begins with: " + expected);
		logger.info("              actual: " + url.toString());
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
	
	@Test 
	public void testGetAssembledQueryString() {
		D1Url url = new D1Url(testBaseUrl,testResource);
		url.addNonEmptyParamPair("k1", "v1&");
		url.addNonEmptyParamPair("k2", "v2");
		String expected = "k1=v1%26&k2=v2";

		assertEquals(expected, url.getAssembledQueryString());
	}
}
