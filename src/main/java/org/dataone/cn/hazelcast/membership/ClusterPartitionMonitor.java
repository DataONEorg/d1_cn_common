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

import org.dataone.cn.hazelcast.HazelcastClientFactory;
import org.dataone.cn.hazelcast.HazelcastConfigLocationFactory;

public class ClusterPartitionMonitor {

    private static ClusterPartitionMembershipListener storageMembershipListener = null;
    private static boolean storagePartition = false;

    private static ClusterPartitionMembershipListener processingMembershipListener = null;
    private static boolean processingPartition = false;

    private static ClusterPartitionMembershipListener sessionMembershipListener = null;
    private static boolean sessionPartition = false;

    private ClusterPartitionMonitor() {
    }

    public static void startStorageMonitor() {
        storageMembershipListener = new ClusterPartitionMembershipListener(
                HazelcastClientFactory.getStorageClient(),
                HazelcastConfigLocationFactory.getStorageConfigLocation(),
                ClusterPartitionMembershipListener.STORAGE);
        storageMembershipListener.startListener();
    }

    public static void startProcessingMonitor() {
        processingMembershipListener = new ClusterPartitionMembershipListener(
                HazelcastClientFactory.getProcessingClient(),
                HazelcastConfigLocationFactory.getProcessingConfigLocation(),
                ClusterPartitionMembershipListener.PROCESSING);
        processingMembershipListener.startListener();
    }

    public static void startSessionMonitor() {
        sessionMembershipListener = new ClusterPartitionMembershipListener(
                HazelcastClientFactory.getSessionClient(),
                HazelcastConfigLocationFactory.getSessionConfigLocation(),
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
