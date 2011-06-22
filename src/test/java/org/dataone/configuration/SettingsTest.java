package org.dataone.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.SystemConfiguration;
import org.junit.Test;


public class SettingsTest {
	
	/**
	 * test that system properties set from within the execution thread are
	 * picked up by SystemConfiguration instantiation.
	 * (any that are set prior to instantiation of the SystemConfiguration)
	 * @throws Exception
	 */
	@Test
	public void testReadingSystemProperties() throws Exception {
		String propValue = "dataoneValue";
		String propKey = "dataoneTest";
		System.setProperty(propKey,propValue);

		//SystemConfiguration config = new SystemConfiguration();
		String returnedValue = Settings.getConfiguration().getString(propKey);

//		 = config.getString(propKey);
		System.out.println(propKey + " = " + returnedValue);
		assertTrue("Can read the new system property",returnedValue.equals(propValue));
	}

	/**
	 * test that system properties set from within the execution thread are
	 * picked up by the SystemConfiguration object.
	 * (Specifically, that any that are set after SystemConfiguration are available)
	 * @throws Exception
	 */
	@Test
	public void testReadingSystemProperties_posthoc() throws Exception {
		String propValue = "dataoneValue2";
		String propKey = "dataoneTest2";
		
		// ensure the config is instantiated 
		Configuration config = Settings.getConfiguration();
		
		System.setProperty(propKey,propValue);
		
		String returnedValue = config.getString(propKey);
		System.out.println(propKey + " = " + returnedValue);
		assertTrue("Can read the new system property",returnedValue.equals(propValue));
	}
	
	/**
	 * Test lookup from default properties file
	 */
	@Test
	public void testDefault() {
		String foo = Settings.getConfiguration().getString("test.foo");
		assertEquals("default", foo);
	
	}
	
	/**
	 * Test looking up an overridden value
	 */
	@Test
	public void testUser() {
		String bar = Settings.getConfiguration().getString("test.bar");
		assertEquals("user", bar);
	
	}
	
	/**
	 * Include an optional properties file using System property:
	 * -Doptional.properties.filename=optional.properties
	 * @throws Exception
	 */
	@Test
	public void testOptional() {
		String bang = Settings.getConfiguration().getString("test.bang");
		if (System.getProperty("optional.properties.filename") == null) {
			assertNull(bang);
		} else {
			assertEquals("optional", bang);
		}
	
	}
}
