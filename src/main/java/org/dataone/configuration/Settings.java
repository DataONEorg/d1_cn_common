package org.dataone.configuration;

import java.net.URL;
import java.util.Enumeration;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationFactory;

public class Settings {
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
        	try {
        		// default to include all the configurations in config.xml, but can be extended
            	configuration = new CompositeConfiguration();

            	Enumeration<URL> configURLs = Settings.class.getClassLoader().getResources("org/dataone/configuration/config.xml");
            	while (configURLs.hasMoreElements()) {
            		URL configURL = configURLs.nextElement();
            		ConfigurationFactory factory = new ConfigurationFactory();
            		factory.setConfigurationURL(configURL);
        			Configuration config = factory.getConfiguration();
        			configuration.addConfiguration(config);
            	}
            	
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        }
        return configuration;
    }
    
}

