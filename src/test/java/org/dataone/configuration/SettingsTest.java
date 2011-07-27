package org.dataone.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.Test;


public class SettingsTest {
	
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
	 * Test that resetConfiguration can get a property it couldn't before resetting
	 * 
	 * @throws ConfigurationException
	 * @throws IOException
	 */
//	@Test
	public void testResetConfiguration()  {

		Configuration c = Settings.getConfiguration();
		System.setProperty("opt.overriding.properties.filename", "org/dataone/configuration/optional.properties");
		
		String bangValue = Settings.getConfiguration().getString("test.bang");
		assertNull(bangValue);
		
		bangValue = Settings.getResetConfiguration().getString("test.bang");
		assertEquals("optional",bangValue);
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
