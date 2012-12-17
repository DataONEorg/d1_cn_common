package org.dataone.cn.hazelcast.membership;

import org.dataone.cn.hazelcast.HazelcastClientFactory;

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
                HazelcastClientFactory.getStorageClient(), "/etc/dataone/storage/hazelcast.xml",
                ClusterPartitionMembershipListener.STORAGE);
        storageMembershipListener.startListener();
    }

    public static void startProcessingMonitor() {
        processingMembershipListener = new ClusterPartitionMembershipListener(
                HazelcastClientFactory.getProcessingClient(), "/etc/dataone/process/hazelcast.xml",
                ClusterPartitionMembershipListener.PROCESSING);
        processingMembershipListener.startListener();
    }

    public static void startSessionMonitor() {
        sessionMembershipListener = new ClusterPartitionMembershipListener(
                HazelcastClientFactory.getSessionClient(), "/etc/dataone/portal/hazelcast.xml",
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
