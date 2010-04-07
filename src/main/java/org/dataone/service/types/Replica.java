/**
 * Copyright 2010 Regents of the University of California and the
 *                National Center for Ecological Analysis and Synthesis
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

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
