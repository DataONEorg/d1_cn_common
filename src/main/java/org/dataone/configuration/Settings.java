package org.dataone.configuration;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.DefaultConfigurationBuilder;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Settings {
	
	private static Log log = LogFactory.getLog(Settings.class);
	
    private static CompositeConfiguration configuration = null;
    
    public static String OPTIONAL_PROPERTY_PROPERTIES_FILENAME = "opt.overriding.properties.filename";
    public static String OPTIONAL_PROPERTY_CONTEXT_LABEL = "opt.context.label";
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
     */
    public static Configuration getConfiguration() {
        if (configuration == null) {
        	
    		// allow commas in the property values
    		AbstractConfiguration.setDefaultListDelimiter(';');
    		
        	configuration = new CompositeConfiguration();
        	
        	// set up first two configurations to implement passing in
        	// the optional properties file
        	configuration.addConfiguration(new SystemConfiguration());
        	
        	String propsFile = configuration.getString(OPTIONAL_PROPERTY_PROPERTIES_FILENAME);
			if (propsFile != null && propsFile.trim().length() > 0) {
				System.out.println("overriding properties file detected: " + propsFile);
				log.debug("overriding properties file detected: " + propsFile);
        	
				URL url = Settings.class.getClassLoader().getResource(propsFile);
				try {
					configuration.addConfiguration(new PropertiesConfiguration(url));
				} catch (ConfigurationException e) {
					System.out.println("configuration exception on optional configuration: " + url + ": " + e.getMessage());
					log.error("ConfigurationException encountered while loading configuration: " + url, e);
				}
			} 
        	
			// TODO: find a better way to set the default context - we don't want to put config files
			// in d1_common_java (too many rebuilds), but then we shouldn't really be setting the 
			// default context here - it needs to be managed in the same package that holds
			// the context files.  Yes?
			
			String context = configuration.getString(OPTIONAL_PROPERTY_CONTEXT_LABEL);
			if (context == null) {
				context = "LOCAL";
			}
			URL url = Settings.class.getClassLoader().getResource(STD_CONFIG_PATH + "/default." + context + ".test.properties");
			if (url != null ) {
				try {
					configuration.addConfiguration(new PropertiesConfiguration(url));
				} catch (ConfigurationException e) {
					System.out.println("configuration exception on optional context: " + url + ": " + e.getMessage());
					log.error("ConfigurationException encountered while loading configuration: " + url, e);
				}
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
}

