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

import com.hazelcast.config.Config;
import com.hazelcast.config.FileSystemXmlConfig;
import com.hazelcast.config.GroupConfig;
import java.io.File;
import java.io.FileNotFoundException;
/**
 *
 * Read in a hazelcast configuration file and return settings such that
 * a client will only connect to the localhost
 *
 * Default is to read the storage cluster from /etc/dataone/storage/hazelcast.xml
 * But it can be overridden for testing purposes.
 *
 * TODO: may need to be written to support spring configurations as well as the standard
 * configuration so as to allow client configuration of processing and storage clusters
 * 
 * @author waltz
 */
public class ClientConfiguration {

    private static String storageConfigurationLocation = "/etc/dataone/storage/hazelcast.xml";
    
    private Config hazelcastConfig;
    public ClientConfiguration() throws FileNotFoundException {
            File hazelcastConfigfile = new File(storageConfigurationLocation);
            hazelcastConfig = new FileSystemXmlConfig(hazelcastConfigfile);
    }
    public ClientConfiguration(String location) throws FileNotFoundException {
            File hazelcastConfigfile = new File(location);
            hazelcastConfig = new FileSystemXmlConfig(hazelcastConfigfile);
    }
    public ClientConfiguration(Config hazelcastConfig) throws FileNotFoundException {
            this.hazelcastConfig = hazelcastConfig;
    }
    public int getPort() {
        return hazelcastConfig.getPort();
    }

    public String getLocalhost() {
        return "127.0.0.1:" + Integer.toString(this.getPort());
    }

    public String getGroup() {
        GroupConfig groupConfig = hazelcastConfig.getGroupConfig();
        return groupConfig.getName();
    }

    public String getPassword() {
        GroupConfig groupConfig = hazelcastConfig.getGroupConfig();
        return groupConfig.getPassword();
    }

    public void setStorageConfigurationLocation(String location) throws FileNotFoundException {
        this.storageConfigurationLocation = location;
        File hazelcastConfigfile = new File(storageConfigurationLocation);
        hazelcastConfig = new FileSystemXmlConfig(hazelcastConfigfile);
    }
}
