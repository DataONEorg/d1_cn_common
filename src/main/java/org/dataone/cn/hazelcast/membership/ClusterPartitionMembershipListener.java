package org.dataone.cn.hazelcast.membership;


import com.hazelcast.core.HazelcastInstance;

public class ClusterPartitionMembershipListener extends BaseHazelcastMembershipListener {

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