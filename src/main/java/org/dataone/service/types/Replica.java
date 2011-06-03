
package org.dataone.service.types;

import java.util.Date;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/0.6.2" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Replica">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:NodeReference" name="replicaMemberNode"/>
 *     &lt;xs:element type="ns:ReplicationStatus" name="replicationStatus"/>
 *     &lt;xs:element type="xs:dateTime" name="replicaVerified"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class Replica
{
    private NodeReference replicaMemberNode;
    private ReplicationStatus replicationStatus;
    private Date replicaVerified;

    /** 
     * Get the 'replicaMemberNode' element value.
     * 
     * @return value
     */
    public NodeReference getReplicaMemberNode() {
        return replicaMemberNode;
    }

    /** 
     * Set the 'replicaMemberNode' element value.
     * 
     * @param replicaMemberNode
     */
    public void setReplicaMemberNode(NodeReference replicaMemberNode) {
        this.replicaMemberNode = replicaMemberNode;
    }

    /** 
     * Get the 'replicationStatus' element value.
     * 
     * @return value
     */
    public ReplicationStatus getReplicationStatus() {
        return replicationStatus;
    }

    /** 
     * Set the 'replicationStatus' element value.
     * 
     * @param replicationStatus
     */
    public void setReplicationStatus(ReplicationStatus replicationStatus) {
        this.replicationStatus = replicationStatus;
    }

    /** 
     * Get the 'replicaVerified' element value.
     * 
     * @return value
     */
    public Date getReplicaVerified() {
        return replicaVerified;
    }

    /** 
     * Set the 'replicaVerified' element value.
     * 
     * @param replicaVerified
     */
    public void setReplicaVerified(Date replicaVerified) {
        this.replicaVerified = replicaVerified;
    }
}
