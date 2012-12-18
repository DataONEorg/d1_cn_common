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
 */
package org.dataone.cn.hazelcast.membership;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.XmlConfigBuilder;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MembershipEvent;
import com.hazelcast.core.MembershipListener;
import com.hazelcast.nio.Address;

public abstract class BaseHazelcastMembershipListener implements MembershipListener {

    private static Logger log = Logger.getLogger(BaseHazelcastMembershipListener.class.getName());

    protected HazelcastInstance hzInstance = null;
    protected List<Address> configAddresses = new ArrayList<Address>();
    protected boolean listening = false;

    /**
     * Used for testing only when a configuration location is not available.
     * 
     * @param instance
     */
    public BaseHazelcastMembershipListener(HazelcastInstance instance) {
        this.hzInstance = instance;
    }

    public BaseHazelcastMembershipListener(HazelcastInstance instance, String configLocation) {
        this.hzInstance = instance;
        if (configLocation != null) {
            try {
                Config hzConfig = null;
                if (configLocation.startsWith("classpath:")) {
                    String hzConfigLocationConfig = configLocation.replace("classpath:", "");
                    hzConfig = new ClasspathXmlConfig(hzConfigLocationConfig);
                } else {
                    XmlConfigBuilder configBuilder;
                    configBuilder = new XmlConfigBuilder(configLocation);
                    hzConfig = configBuilder.build();
                }
                configAddresses = hzConfig.getNetworkConfig().getJoin().getTcpIpConfig()
                        .getAddresses();
            } catch (FileNotFoundException e) {
                log.error("error reading hzConfig for: " + instance.getName() + " at: "
                        + configLocation, e);
            }
        }
    }

    public void startListener() {
        if (!this.listening) {
            log.debug("Starting membership listener on instance: " + this.hzInstance.getName());
            this.hzInstance.getCluster().addMembershipListener(this);
            this.listening = true;
        } else {
            log.debug("Already listening for membership on instance: " + this.hzInstance.getName());
        }
    }

    public void stopListener() {
        if (this.listening) {
            log.debug("Stopping membership listener for cluster: " + this.hzInstance.getName());
            this.hzInstance.getCluster().removeMembershipListener(this);
            this.listening = false;
        }
    }

    @Override
    public void memberAdded(MembershipEvent membershipEvent) {
        logEvent(membershipEvent);
    }

    @Override
    public void memberRemoved(MembershipEvent membershipEvent) {
        logEvent(membershipEvent);
    }

    protected int getMembershipCount() {
        return this.hzInstance.getCluster().getMembers().size();
    }

    protected boolean clusterIsPartitioned() {
        if (configAddresses.size() > getMembershipCount()) {
            return true;
        } else {
            return false;
        }
    }

    public abstract void handleMemberAddedEvent();

    public abstract void handleMemberRemovedEvent();

    protected void logEvent(MembershipEvent membershipEvent) {
        String eventType = "unknown operationed";
        if (MembershipEvent.MEMBER_ADDED == membershipEvent.getEventType()) {
            eventType = "added";
        } else if (MembershipEvent.MEMBER_REMOVED == membershipEvent.getEventType()) {
            eventType = "removed";
        }

        String ip = membershipEvent.getMember().getInetSocketAddress().getAddress()
                .getHostAddress();
        int port = membershipEvent.getMember().getInetSocketAddress().getPort();

        log.debug("Member " + eventType + " to/from cluster instance: " + this.hzInstance.getName());
        log.debug("Member " + eventType + " is: " + ip + port);
        log.debug("Cluster size: " + this.hzInstance.getCluster().getMembers().size());
        log.debug("Cluster members: " + this.hzInstance.getCluster().getMembers());
    }

}
