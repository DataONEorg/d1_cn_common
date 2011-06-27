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

    /**
     * A private constructor to be sure no instances are created.
     */
    private Settings() {
    	
    }
        
    /**
     * Get a Configuration interface that combines all resources found in:
     * 	org/dataone/configuration/config.xml
     * @return an aggregate Configuration for all properties loaded
     */
    public static Configuration getConfiguration() {
        if (configuration == null) {
    		// allow commas in the values
    		AbstractConfiguration.setDefaultListDelimiter(';');
    		// default to include all the configurations in config.xml, but can be extended
        	configuration = new CompositeConfiguration();
        	configuration.addConfiguration(new SystemConfiguration());
        	
        	String propsFile = configuration.getString("opt.overriding.properties.filename");
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
        	
        	String configResourceName = "org/dataone/configuration/config.xml";
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

