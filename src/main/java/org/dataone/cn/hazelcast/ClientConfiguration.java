/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dataone.cn.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.config.FileSystemXmlConfig;
import com.hazelcast.config.GroupConfig;
import java.io.File;
import java.io.FileNotFoundException;
/**
 *
 * @author waltz
 */
public class ClientConfiguration {

    private static String storageConfigurationLocation = "/etc/dataone/storage/hazelcast.xml";
    
    private Config hazelcastConfig = new Config();
    public ClientConfiguration() throws FileNotFoundException {
            File hazelcastConfigfile = new File(storageConfigurationLocation);
            hazelcastConfig = new FileSystemXmlConfig(hazelcastConfigfile);

    }

    public int getPort() {
        return hazelcastConfig.getPort();
    }

    public String[] getAddresses() {
        // clients will connect directly to the localhost for interactions
        String[] addresses = {"localhost:" + Integer.toString(this.getPort())};
        return addresses;
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
