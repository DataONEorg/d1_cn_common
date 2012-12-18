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

import org.apache.log4j.Logger;

import com.hazelcast.core.HazelcastInstance;

public class ClusterPartitionMembershipListener extends BaseHazelcastMembershipListener {

    private static Logger log = Logger
            .getLogger(ClusterPartitionMembershipListener.class.getName());

    public static final String STORAGE = "storage";
    public static final String PROCESSING = "processing";
    public static final String SESSION = "session";

    private String cluster = "";

    public ClusterPartitionMembershipListener(HazelcastInstance instance, String configLocation,
            String clusterName) {
        super(instance, configLocation);
        cluster = clusterName;
    }

    @Override
    public void handleMemberAddedEvent() {
        setPartitionStatus();
    }

    @Override
    public void handleMemberRemovedEvent() {
        setPartitionStatus();
    }

    private void setPartitionStatus() {
        if (clusterIsPartitioned()) {
            if (STORAGE.equals(cluster)) {
                ClusterPartitionMonitor.setStoragePartition(true);
            } else if (PROCESSING.equals(cluster)) {
                ClusterPartitionMonitor.setProcessingPartition(true);
            } else if (SESSION.equals(cluster)) {
                ClusterPartitionMonitor.setSessionPartition(true);
            }
        } else {
            if (STORAGE.equals(cluster)) {
                ClusterPartitionMonitor.setStoragePartition(false);
            } else if (PROCESSING.equals(cluster)) {
                ClusterPartitionMonitor.setProcessingPartition(false);
            } else if (SESSION.equals(cluster)) {
                ClusterPartitionMonitor.setSessionPartition(false);
            }
        }
    }
}