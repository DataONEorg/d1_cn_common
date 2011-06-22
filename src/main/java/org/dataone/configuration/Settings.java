package org.dataone.configuration;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationFactory;
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
     * Get a Configuration interface  
     * @return the value of that property
     */
    public static Configuration getConfiguration() {
        if (configuration == null) {
    		// allow commas in the values
    		AbstractConfiguration.setDefaultListDelimiter(';');
    		// default to include all the configurations in config.xml, but can be extended
        	configuration = new CompositeConfiguration();
        	String configResourceName = "org/dataone/configuration/config.xml";
        	Enumeration<URL> configURLs = null;
			try {
				configURLs = Settings.class.getClassLoader().getResources(configResourceName);
				while (configURLs.hasMoreElements()) {
	        		URL configURL = configURLs.nextElement();
	        		log.debug("Loading configuration: " + configURL);
	        		ConfigurationFactory factory = new ConfigurationFactory();
	        		factory.setConfigurationURL(configURL);
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
    
}

