/**
 * This work was created by participants in the DataONE project, and is
 * jointly copyrighted by participating institutions in DataONE. For 
 * more information on DataONE, see our web site at http://dataone.org.
 *
 *   Copyright ${year}
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 * 
 * $Id$
 */

package org.dataone.cn.hazelcast;

import java.io.FileNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dataone.configuration.Settings;

import com.hazelcast.client.ClientConfig;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.config.ClasspathXmlConfig;

/**
 * 
 * Encapsulation layer for the various hazelcast cluster configurations and
 * associated client instances.
 * 
 * One source for HazelcastClient instances.
 * 
 * Based on HazelcastClientInstance class.
 * 
 * @author sroseboo
 * 
 */
public class HazelcastClientFactory {

    public static final Log logger = LogFactory.getLog(HazelcastClientFactory.class);

    private static HazelcastClient hzStorageClient = null;
    private static HazelcastClient hzProcessingClient = null;
    private static HazelcastClient hzSessionClient = null;

    private HazelcastClientFactory() {
    };

    public static HazelcastClient getStorageClient() {
        if (hzStorageClient == null) {
            hzStorageClient = getHazelcastClientUsingConfig(HazelcastConfigLocationFactory
                    .getStorageConfigLocation());
        }
        return hzStorageClient;
    }

    public static HazelcastClient getProcessingClient() {
        if (hzProcessingClient == null) {
            String group = Settings.getConfiguration().getString(
                    "dataone.hazelcast.process.groupName");
            String password = Settings.getConfiguration().getString(
                    "dataone.hazelcast.process.groupPassword");
            String localhost = "127.0.0.1";
            String port = Settings.getConfiguration().getString("dataone.hazelcast.process.port");

            ClientConfig cc = new ClientConfig();
            cc.getGroupConfig().setName(group);
            cc.getGroupConfig().setPassword(password);
            cc.addAddress(localhost + ":" + port);
            HazelcastClient client = HazelcastClient.newHazelcastClient(cc);
            return client;
        }
        return hzProcessingClient;
    }

    public static HazelcastClient getSessionClient() {
        if (hzSessionClient == null) {
            hzSessionClient = getHazelcastClientUsingConfig(HazelcastConfigLocationFactory
                    .getSessionConfigLocation());
        }
        return hzSessionClient;
    }

    private static HazelcastClient getHazelcastClientUsingConfig(String configLocation) {
        ClientConfiguration clientConfiguration = null;
        try {
            if (configLocation != null) {
                // for testing purposes
                if (configLocation.startsWith("classpath:")) {
                    String hzConfigLocationConfig = configLocation.replace("classpath:", "");
                    ClasspathXmlConfig config = new ClasspathXmlConfig(hzConfigLocationConfig);
                    clientConfiguration = new ClientConfiguration(config);
                } else {
                    clientConfiguration = new ClientConfiguration(configLocation);
                }
            }
        } catch (FileNotFoundException ex) {
            throw new NullPointerException("FileNotFound so clientConfiguration is Null: "
                    + ex.getMessage());
        }

        logger.info("group " + clientConfiguration.getGroup() + " pwd "
                + clientConfiguration.getPassword() + " addresses "
                + clientConfiguration.getLocalhost());

        ClientConfig cc = new ClientConfig();
        cc.getGroupConfig().setName(clientConfiguration.getGroup());
        cc.getGroupConfig().setPassword(clientConfiguration.getPassword());
        cc.addAddress(clientConfiguration.getLocalhost());
        HazelcastClient client = HazelcastClient.newHazelcastClient(cc);
        return client;
    }
}
