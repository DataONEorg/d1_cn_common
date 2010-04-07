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

import java.util.ArrayList;

/** A data structure representing the DataONE Replication Policy block of
 * system metadata.
 * 
 * @author Matthew Jones
 */
public class ReplicationPolicy {
    private boolean replicationAllowed;
    private int numberReplicas;
    private ArrayList<NodeReferenceType> preferredMemberNodes;
    private ArrayList<NodeReferenceType> blockedMemberNodes;
    
    public ReplicationPolicy(boolean replicationAllowed, int numberReplicas) {
        preferredMemberNodes = new ArrayList<NodeReferenceType>();
        blockedMemberNodes = new ArrayList<NodeReferenceType>();
        this.replicationAllowed = replicationAllowed;
        this.numberReplicas = numberReplicas;
    }
    
    /**
     * @return the replicationAllowed
     */
    public boolean isReplicationAllowed() {
        return replicationAllowed;
    }
    /**
     * @param replicationAllowed the replicationAllowed to set
     */
    public void setReplicationAllowed(boolean replicationAllowed) {
        this.replicationAllowed = replicationAllowed;
    }
    /**
     * @return the numberReplicas
     */
    public int getNumberReplicas() {
        return numberReplicas;
    }
    /**
     * @param numberReplicas the numberReplicas to set
     */
    public void setNumberReplicas(int numberReplicas) {
        this.numberReplicas = numberReplicas;
    }
    /**
     * @return the preferredMemberNodes
     */
    public ArrayList<NodeReferenceType> getPreferredMemberNodes() {
        return preferredMemberNodes;
    }
    /**
     * @param preferredMemberNodes the preferredMemberNodes to set
     */
    public void setPreferredMemberNodes(
            ArrayList<NodeReferenceType> preferredMemberNodes) {
        this.preferredMemberNodes = preferredMemberNodes;
    }
    /**
     * @return the blockedMemberNodes
     */
    public ArrayList<NodeReferenceType> getBlockedMemberNodes() {
        return blockedMemberNodes;
    }
    /**
     * @param blockedMemberNodes the blockedMemberNodes to set
     */
    public void setBlockedMemberNodes(
            ArrayList<NodeReferenceType> blockedMemberNodes) {
        this.blockedMemberNodes = blockedMemberNodes;
    }
}
