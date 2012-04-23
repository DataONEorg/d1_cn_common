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

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.config.ClasspathXmlConfig;
import java.io.FileNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dataone.configuration.Settings;

/**
 * Create a Settings property to point the hazelcast client instance to a
 * configuration file.  If the path begins with "classpath:" then the
 * configuration will be loaded from a jar's classpath (useful for testing)
 * Default location is /etc/dataone/storage/hazelcast.xml and is
 * set in ClientConfiguration.java
 *
 * TODO: Need to determine best manner to support two different hazelcast clusters
 * This was intended only to support connecting to the storage cluster,
 * But since there is storage information in the processing cluster (hzNodes)
 * It might be useful to create two instances
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
