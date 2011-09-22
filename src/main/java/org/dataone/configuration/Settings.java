package org.dataone.configuration;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Enumeration;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.DefaultConfigurationBuilder;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Settings {
	
	private static Log log = LogFactory.getLog(Settings.class);
	
    private static CompositeConfiguration configuration = null;
    
    public static String STD_CONFIG_PATH = "org/dataone/configuration";

    /**
     * A private constructor to be sure no instances are created.
     */
    private Settings() {
    	
    }
        
    /**
     * Get a Configuration interface that combines all resources
     * found under all of the org.dataone.configuration/config.xml
     * files from all application packages.  
     * 
     * In the case of property key collision, those from configurations
     * loaded first override those loaded later.  So, recommend creating
     * keys with a package-specific prefix to avoid unnecessary 
     * collisions.
     * 
     * System properties are always loaded first, followed by those
     * properties in an optional file (specified with the system
     * property "opt.overriding.properties.filename"), followed by
     * context-specific default properties files.
     * 
     * 
     * @return an aggregate Configuration for all properties loaded
     * @throws NoSuchMethodException 
     * @throws IllegalAccessException 
     * @throws InvocationTargetException 
     */
    public static Configuration getConfiguration() {
        if (configuration == null) {
        	
    		// allow commas in the property values
    		AbstractConfiguration.setDefaultListDelimiter(';');
    		
        	configuration = new CompositeConfiguration();
        	      	
        	try {
				configuration = loadTestConfigurations(configuration);
			} catch (ConfigurationException e1) {
				// if problems with TestSettings, need to stop loading regular
				// configurations, and cause downstream failures.
				return new CompositeConfiguration();
			}
        	
			// default to include all the configurations at config.xml, but can be extended
        	String configResourceName = STD_CONFIG_PATH + "/config.xml";
        	Enumeration<URL> configURLs = null;
			try {
				configURLs = Settings.class.getClassLoader().getResources(configResourceName);
				while (configURLs.hasMoreElements()) {
	        		URL configURL = configURLs.nextElement();
	        		log.debug("Loading configuration: " + configURL);
	        		DefaultConfigurationBuilder factory = new DefaultConfigurationBuilder();
	        		factory.setURL(configURL);
	    			Configuration config = null;
					try {
						config = factory.getConfiguration();
		    			configuration.addConfiguration(config);
					} catch (ConfigurationException e) {
						log.error("Problem loading configuration: " + configURL, e);
					}
	        	}
			} catch (IOException e) {
				log.error("No configuration resources found for: " + configResourceName, e);
			}	
    		
        }
        return configuration;
    }
    
    
    public static Configuration getResetConfiguration() {
    	configuration = null;
    	return getConfiguration();
    }
    
    
    /**
     * The hook for inserting test configurations into the application.
     * Looks for an org.dataone.configuration.TestSettings class, and loads
     * the properties at the front of the composite configuration, effectively
     * overriding any later values loaded.
     * If TestSettings class not found, assumes not a testing application, so 
     * does not throw an exception
     * 
     * @return Configuration object containing the test configurations.
     * @throws ConfigurationException
     */
    private static CompositeConfiguration loadTestConfigurations(CompositeConfiguration configuration) 
    throws ConfigurationException 
    {
   
		try {
//			ClassLoader myClassLoader = ClassLoader.getSystemClassLoader();
			ClassLoader myClassLoader = Thread.currentThread().getContextClassLoader();
			log.debug("ClassLoader type: " + myClassLoader.getClass().getSimpleName());
			Class<?> testSettings;
			testSettings = myClassLoader.loadClass("org.dataone.configuration.TestSettings");
 	
			try {
				Method getTestConfigurations = testSettings.getMethod("getConfiguration", null);
				configuration = (CompositeConfiguration) getTestConfigurations.invoke(null, null);
				
			// problems loading configurations when in test situation are not
			// recoverable, because we do not want to revert to non-test (production)
			// context if we can't load test configurations.
			} catch (Exception e) {

				String message = "General Problem loading TestSettings. " + 
					e.getClass().getSimpleName() + ": " + e.getMessage();
				log.error(message,e);
				throw new ConfigurationException(message);
			}

		} catch (ClassNotFoundException e) {
			log.debug("TestSettings not found: assume production context");
			// do nothing, because will only find if d1_integration in classpath 
		}		
				
		return configuration;
    }
    
    /**
     * Include additional properties in the global configuration.
     * Properties included in the given file will override existing properties in the global configuration
     * if they are present.
     * @param fileName The properties file (path) to include
     * @throws ConfigurationException
     */
    public static void augmentConfiguration(String fileName) throws ConfigurationException {
    	Configuration config = new PropertiesConfiguration(fileName);
    	// create new composite to hold them all
    	CompositeConfiguration compositeConfiguration = new CompositeConfiguration();
    	compositeConfiguration.addConfiguration(config);
    	// add the originals back
    	int size = ((CompositeConfiguration) getConfiguration()).getNumberOfConfigurations();
    	for (int i = 0; i < size; i++) {
    		Configuration c = ((CompositeConfiguration) getConfiguration()).getConfiguration(i);
    		compositeConfiguration.addConfiguration(c);
    	}
    	configuration = compositeConfiguration;
    }
}

