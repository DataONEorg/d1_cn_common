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

import org.apache.commons.lang.StringUtils;
import org.dataone.cn.hazelcast.HazelcastClientFactory;
import org.dataone.configuration.Settings;

public class ClusterPartitionMonitor {

    private static final String DEFAULT_STORAGE_CLUSTER_CONFIG = "/etc/dataone/storage/hazelcast.xml";
    private static final String DEFAULT_PROCESS_CLUSTER_CONFIG = "/etc/dataone/process/hazelcast.xml";
    private static final String DEFAULT_SESSION_CLUSTER_CONFIG = "/etc/dataone/portal/hazelcast.xml";

    private static final String STORAGE_CLUSTER_OVERRIDE_PROPERTY = "dataone.hazelcast.location.clientconfig";
    private static final String PROCESSING_CLUSTER_OVERRIDE_PROPERTY = "dataone.hazelcast.location.processing.clientconfig";

    private static ClusterPartitionMembershipListener storageMembershipListener = null;
    private static boolean storagePartition = false;

    private static ClusterPartitionMembershipListener processingMembershipListener = null;
    private static boolean processingPartition = false;

    private static ClusterPartitionMembershipListener sessionMembershipListener = null;
    private static boolean sessionPartition = false;

    private ClusterPartitionMonitor() {
    }

    public static void startStorageMonitor() {
        String configLocationSetting = Settings.getConfiguration().getString(
                STORAGE_CLUSTER_OVERRIDE_PROPERTY);
        if (StringUtils.isNotBlank(configLocationSetting)) {
            configLocationSetting = DEFAULT_STORAGE_CLUSTER_CONFIG;
        }

        storageMembershipListener = new ClusterPartitionMembershipListener(
                HazelcastClientFactory.getStorageClient(), configLocationSetting,
                ClusterPartitionMembershipListener.STORAGE);
        storageMembershipListener.startListener();
    }

    public static void startProcessingMonitor() {
        String configLocationSetting = Settings.getConfiguration().getString(
                PROCESSING_CLUSTER_OVERRIDE_PROPERTY);
        if (StringUtils.isNotBlank(configLocationSetting)) {
            configLocationSetting = DEFAULT_PROCESS_CLUSTER_CONFIG;
        }
        processingMembershipListener = new ClusterPartitionMembershipListener(
                HazelcastClientFactory.getProcessingClient(), configLocationSetting,
                ClusterPartitionMembershipListener.PROCESSING);
        processingMembershipListener.startListener();
    }

    public static void startSessionMonitor() {
        sessionMembershipListener = new ClusterPartitionMembershipListener(
                HazelcastClientFactory.getSessionClient(), DEFAULT_SESSION_CLUSTER_CONFIG,
                ClusterPartitionMembershipListener.SESSION);
        sessionMembershipListener.startListener();
    }

    public static void stopMonitors() {
        storageMembershipListener.stopListener();
        processingMembershipListener.stopListener();
        sessionMembershipListener.stopListener();
    }

    public static void setStoragePartition(boolean partition) {
        storagePartition = partition;
    }

    public static void setProcessingPartition(boolean partition) {
        processingPartition = partition;
    }

    public static void setSessionPartition(boolean partition) {
        sessionPartition = partition;
    }

    public static boolean getStoragePartion() {
        return storagePartition;
    }

    public static boolean getProcessingPartition() {
        return processingPartition;
    }

    public static boolean getSessionPartition() {
        return sessionPartition;
    }
}
