package org.dataone.service.exceptions.util;

import static org.junit.Assert.*;

import org.apache.tools.ant.filters.StringInputStream;
import org.dataone.service.exceptions.BaseException;
import org.dataone.service.exceptions.NotFound;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

public class ExceptionUtilTestCase {

	@Before
	public static void setUpBeforeClass() throws Exception {
		String nonErrorString = "fa la la la la";
		StringInputStream nonErrorStream = new StringInputStream(nonErrorString);
		
		NotFound nfe = new NotFound("12345","some generic description");
		StringInputStream xmlErrorStream = new StringInputStream(nfe.serialize(BaseException.FMT_XML));
		StringInputStream jsonErrorStream = new StringInputStream(nfe.serialize(BaseException.FMT_JSON));
		
		
		
	}

	@Ignore("not yet implemented")
	@Test
	public void testFilterErrorsInputStreamBooleanString() {
		fail("Not yet implemented");
	}

	@Ignore("not yet implemented")
	@Test
	public void testDeserializeAndThrowExceptionInputStreamIntegerStringString() {
		fail("Not yet implemented");
	}

}
