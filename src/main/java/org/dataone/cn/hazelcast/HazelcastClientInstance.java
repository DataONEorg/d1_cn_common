/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dataone.cn.hazelcast;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.config.ClasspathXmlConfig;
import java.io.FileNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dataone.configuration.Settings;

/**
 *
 * @author waltz
 */
public class HazelcastClientInstance {

    public final static Log logger = LogFactory.getLog(HazelcastClientInstance.class);
    private static HazelcastClient hzclient = null;
    private final static String hzConfigLocation = Settings.getConfiguration().getString("dataone.hazelcast.location.clientconfig");

    static public HazelcastClient getHazelcastClient() {
        if (hzclient == null) {
            ClientConfiguration clientConfiguration = null;
            try {
                if (hzConfigLocation != null) {
                    // for testing purposes
                    if (hzConfigLocation.startsWith("classpath:")) {
                        String hzConfigLocationConfig = hzConfigLocation.replace("classpath:", "");
                        ClasspathXmlConfig config = new ClasspathXmlConfig(hzConfigLocationConfig);
                        clientConfiguration = new ClientConfiguration(config);
                    } else {
                        // to override the default behavior
                        clientConfiguration = new ClientConfiguration(hzConfigLocation);
                    }
                } else {
                    // default behavior is to pull from /etc/dataone/
                    clientConfiguration = new ClientConfiguration();
                }
            } catch (FileNotFoundException ex) {
                throw new NullPointerException("FileNotFound so clientConfiguration is Null: " + ex.getMessage());
            }

            logger.info("group " + clientConfiguration.getGroup() + " pwd " + clientConfiguration.getPassword() + " addresses " + clientConfiguration.getLocalhost());
            hzclient = HazelcastClient.newHazelcastClient(clientConfiguration.getGroup(), clientConfiguration.getPassword(),
                    clientConfiguration.getLocalhost());
        }
        return hzclient;
    }
}
