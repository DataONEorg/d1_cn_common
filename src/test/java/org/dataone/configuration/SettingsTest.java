package org.dataone.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.SystemConfiguration;
import org.junit.Test;
import org.junit.Ignore;


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

		String returnedValue = Settings.getConfiguration().getString(propKey);

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

		String returnedAgain = Settings.getConfiguration().getString(propKey);
		System.out.println(propKey + " = " + returnedAgain);
		assertTrue("Can read the new system property",returnedAgain.equals(propValue));
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
	 * Tests that properties put into system override those in Settings.
	 */
	@Test
	public void testOverrideWithSystemProperties() {
		String prop = Settings.getConfiguration().getString("test.systemOverride");
		assertEquals("user", prop);
		System.setProperty("test.systemOverride","system");
		prop = Settings.getConfiguration().getString("test.systemOverride");
		assertEquals("system", prop);

	}
	
	
	/**
	 * Test that resetConfiguration can get a property it couldn't before resetting
	 * 
	 * @throws ConfigurationException
	 * @throws IOException
	 */
	@Test
	public void testResetConfiguration()  {

		Configuration c = Settings.getConfiguration();
		System.setProperty("opt.overriding.properties.filename", "org/dataone/configuration/optional.properties");
		
		String bangValue = Settings.getConfiguration().getString("test.bang");
		assertNull(bangValue);
		
		bangValue = Settings.getResetConfiguration().getString("test.bang");
		assertEquals("optional",bangValue);
	}

	
	/**
	 * Include an optional properties file using System property:
	 * -Dopt.overriding.properties.filename=optional.properties
	 * 
	 */
	@Test
	public void testOptionalFile_overrides() {
		System.setProperty("opt.overriding.properties.filename", "org/dataone/configuration/optional.properties");
		String bar = Settings.getResetConfiguration().getString("test.bar");
		System.out.println("test.bar = " + bar);
		assertEquals(bar,"optional");	
	}
	
	
	/**
	 * because interpolation of the property file only happens when the 
	 * configuration is built (during first getConfiguration() call), setting 
	 * a system property with the optional.properties.file after that first
	 * getConfig() call should not do anything.  Confirming this behavior
	 * in this test. 
	 * 
	 * @throws ConfigurationException
	 * @throws IOException
	 */
	@Test
	public void testOptionalFile_lateLoadingProblem() {

		System.setProperty("opt.overriding.properties.filename", "");
		Configuration c = Settings.getResetConfiguration();
		System.setProperty("opt.overriding.properties.filename", "org/dataone/configuration/optional.properties");
		
		String bangValue = Settings.getConfiguration().getString("test.bang");
		System.out.println("test.bang = " + bangValue);
		assertNull(bangValue);
		
		bangValue = Settings.getResetConfiguration().getString("test.bang");
		System.out.println("test.bang = " + bangValue);
		assertEquals(bangValue,"optional");
	}
	
//	@Ignore("not throwing configuration errors")
//	@Test
//	public void testBadOptionalFile_exceptionHandling() {
//		
//		System.setProperty("optional.properties.filename", "nonexistent.properties");	
//		try {
//			
//			System.out.println("1) " + Settings.getConfiguration().getString("optional.properties.filename"));
//			assertEquals(Settings.getConfiguration().getString("test.foo"),"default");
//			fail("Should throw exception before reaching here");
//		} catch (ConfigurationException e) {
//			System.out.println("threw ConfigException");
//			assertTrue("exception properly caught",true);
//		} catch (IOException e) {
//			System.out.println("threw IOException");
//			assertTrue("exception properly caught",true);
//		}
//	}
//	
}
