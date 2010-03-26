package org.dataone.service.types;

import java.util.Date;

/**
 * The DataONE Type to represent an object replica.
 *
 * @author Matthew Jones
 */
public class Replica {

        private NodeReferenceType replicaMemberNode;
        private ReplicationStatus replicationStatus;
        private Date replicaVerified;

        /** 
         * Get the 'ReplicaMemberNode' element value.
         * 
         * @return value
         */
        public NodeReferenceType getReplicaMemberNode() {
            return replicaMemberNode;
        }

        /** 
         * Set the 'ReplicaMemberNode' element value.
         * 
         * @param replicaMemberNode
         */
        public void setReplicaMemberNode(NodeReferenceType replicaMemberNode) {
            this.replicaMemberNode = replicaMemberNode;
        }

        /** 
         * Get the 'ReplicationStatus' element value.
         * 
         * @return value
         */
        public ReplicationStatus getReplicationStatus() {
            return replicationStatus;
        }

        /** 
         * Set the 'ReplicationStatus' element value.
         * 
         * @param replicationStatus
         */
        public void setReplicationStatus(ReplicationStatus replicationStatus) {
            this.replicationStatus = replicationStatus;
        }

        /** 
         * Get the 'ReplicaVerified' element value.
         * 
         * @return value
         */
        public Date getReplicaVerified() {
            return replicaVerified;
        }

        /** 
         * Set the 'ReplicaVerified' element value.
         * 
         * @param replicaVerified
         */
        public void setReplicaVerified(Date replicaVerified) {
            this.replicaVerified = replicaVerified;
        }

        public static enum ReplicationStatus {
            QUEUED("queued"), REQUESTED("requested"), COMPLETED("completed"), INVALIDATED(
                    "invalidated");
            private final String value;

            private ReplicationStatus(String value) {
                this.value = value;
            }

            public String toString() {
                return value;
            }

            public static ReplicationStatus convert(String value) {
                for (ReplicationStatus inst : values()) {
                    if (inst.toString().equals(value)) {
                        return inst;
                    }
                }
                return null;
            }
        }
}
