package org.dataone.cn.hazelcast;

import java.io.FileNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dataone.configuration.Settings;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.config.ClasspathXmlConfig;

/**
 * 
 * Encapsulation layer for the various hazelcast cluster configurations and
 * associated client instances.
 * 
 * One source for HazelcastClient instances.
 * 
 * @author sroseboo
 * 
 */
public class HazelcastClientFactory {

    public static final Log logger = LogFactory.getLog(HazelcastClientFactory.class);

    // storage cluster config
    private static HazelcastClient hzStorageClient = null;
    private static final String hzStorageConfigSettingsOverrideLocation = Settings
            .getConfiguration().getString("dataone.hazelcast.location.clientconfig");
    private static final String defaultStorageConfigLocation = "/etc/dataone/storage/hazelcast.xml";

    // processing cluster config
    private static HazelcastClient hzProcessingClient = null;
    private static final String hzProcessingConfigSettingsOverrideLocation = Settings
            .getConfiguration().getString("datone.hazelcast.location.processing.clientconfig");
    private static final String defaultProcessingConfigLocation = "/etc/dataone/process/hazelcast.xml";

    private HazelcastClientFactory() {
    };

    public static HazelcastClient getStorageClient() {
        return getHazelcastClient(hzStorageConfigSettingsOverrideLocation,
                defaultStorageConfigLocation, hzStorageClient);
    }

    public static HazelcastClient getProcessingClient() {
        return getHazelcastClient(hzProcessingConfigSettingsOverrideLocation,
                defaultProcessingConfigLocation, hzProcessingClient);
    }

    private static HazelcastClient getHazelcastClient(String configLocationSetting,
            String defaultLocation, HazelcastClient client) {
        if (client == null) {
            ClientConfiguration clientConfiguration = null;
            try {
                if (configLocationSetting != null) {
                    // for testing purposes
                    if (configLocationSetting.startsWith("classpath:")) {
                        String hzConfigLocationConfig = configLocationSetting.replace("classpath:",
                                "");
                        ClasspathXmlConfig config = new ClasspathXmlConfig(hzConfigLocationConfig);
                        clientConfiguration = new ClientConfiguration(config);
                    } else {
                        // to override the default behavior
                        clientConfiguration = new ClientConfiguration(configLocationSetting);
                    }
                } else {
                    // default behavior is to pull from /etc/dataone/
                    clientConfiguration = new ClientConfiguration(defaultLocation);
                }
            } catch (FileNotFoundException ex) {
                throw new NullPointerException("FileNotFound so clientConfiguration is Null: "
                        + ex.getMessage());
            }

            logger.info("group " + clientConfiguration.getGroup() + " pwd "
                    + clientConfiguration.getPassword() + " addresses "
                    + clientConfiguration.getLocalhost());
            client = HazelcastClient.newHazelcastClient(clientConfiguration.getGroup(),
                    clientConfiguration.getPassword(), clientConfiguration.getLocalhost());
        }
        return client;
    }
}
