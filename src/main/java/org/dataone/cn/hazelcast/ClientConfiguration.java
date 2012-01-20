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
