package org.dataone.service.exceptions.util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.tools.ant.filters.StringInputStream;
import org.dataone.service.exceptions.BaseException;
import org.dataone.service.exceptions.NotFound;
import org.dataone.service.exceptions.ServiceFailure;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

public class ExceptionUtilTestCase {

	@Before
	public void setUpBeforeClass() throws Exception {
		NotFound nfe = new NotFound("12345","some generic description");
		StringInputStream jsonErrorStream = new StringInputStream(nfe.serialize(BaseException.FMT_JSON));
	}


	@Test
	public void testFilterErrors_IS_nonErrors() {
		String nonErrorString = "fa la la la la";
		StringInputStream nonErrorStream = new StringInputStream(nonErrorString);
		try {
			InputStream is = ExceptionUtil.filterErrors(nonErrorStream, false, "text");
			assertEquals(nonErrorString,IOUtils.toString(is));
			
		} catch (BaseException be) {
			fail("shouldn't throw exception");
		} catch (IllegalStateException e) {
			fail("shouldn't throw exception");
		} catch (IOException e) {
			fail("shouldn't throw exception");
		}		
	}

	@Test
	public void testFilterErrors_IS_xmlError() {
		String setDetailCode = "12345";
		String setDescription = "some generic description";
		NotFound nfe = new NotFound(setDetailCode,setDescription);
		StringInputStream xmlErrorStream = new StringInputStream(nfe.serialize(BaseException.FMT_XML));
		try {
			InputStream is = ExceptionUtil.filterErrors(xmlErrorStream, true, "xml");
			fail("should throw exception");
		} catch (NotFound e) {
			assertEquals(setDetailCode,e.getDetail_code());
			assertEquals(setDescription,e.getDescription());
		} catch (BaseException e) {	
			fail("shouldn't throw this exception: " + e.getClass().getSimpleName());
		} catch (IllegalStateException e) {
			fail("shouldn't throw this exception: " + e.getClass().getSimpleName());
		} catch (IOException e) {
			fail("shouldn't throw this exception: " + e.getClass().getSimpleName());
		}		
	}
	
//	@Ignore("still needs work")
	@Test
	public void testFilterErrors_IS_jsonError() {
		String setDetailCode = "12345";
		String setDescription = "some generic description";
		NotFound nfe = new NotFound(setDetailCode,setDescription);
		StringInputStream xmlErrorStream = new StringInputStream(nfe.serialize(BaseException.FMT_JSON));
		try {
			InputStream is = ExceptionUtil.filterErrors(xmlErrorStream, true, "json");
			fail("should throw exception");
		} catch (ServiceFailure e) {
			assertTrue("expected message returned", e.getDescription().contains("parser for deserializing JSON not written yet"));
		} catch (BaseException e) {	
			fail("shouldn't throw this exception: " + e.getClass().getSimpleName());
		} catch (IllegalStateException e) {
			fail("shouldn't throw this exception: " + e.getClass().getSimpleName());
		} catch (IOException e) {
			fail("shouldn't throw this exception: " + e.getClass().getSimpleName());
		}		
	}
	
	
	@Test
	public void testDeserializeAndThrowException() {
		int statusCode = 99;
		String reason = "stated reason";
		String contentType = "xml";
		String setDescription = "a description";
		NotFound nfe = new NotFound("123",setDescription);
		StringInputStream xmlErrorStream = new StringInputStream(nfe.serialize(BaseException.FMT_XML));
		try {
			ExceptionUtil.deserializeAndThrowException(xmlErrorStream, statusCode, reason, contentType);
			fail("should throw exception");
		} catch (NotFound e) {
			assertEquals(setDescription,e.getDescription());
		} catch (BaseException e) {	
			fail("shouldn't throw this exception: " + e.getClass().getSimpleName());
		} catch (IllegalStateException e) {
			fail("shouldn't throw this exception: " + e.getClass().getSimpleName());
		} catch (IOException e) {
			fail("shouldn't throw this exception: " + e.getClass().getSimpleName());
		}
	
	
	}

}
