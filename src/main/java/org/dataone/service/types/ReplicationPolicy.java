
package org.dataone.service.types;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/0.6.2" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="ReplicationPolicy">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:NodeReference" name="preferredMemberNode" minOccurs="0" maxOccurs="unbounded"/>
 *     &lt;xs:element type="ns:NodeReference" name="blockedMemberNode" minOccurs="0" maxOccurs="unbounded"/>
 *   &lt;/xs:sequence>
 *   &lt;xs:attribute type="xs:boolean" name="replicationAllowed"/>
 *   &lt;xs:attribute type="xs:int" name="numberReplicas"/>
 * &lt;/xs:complexType>
 * </pre>
 */
public class ReplicationPolicy
{
    private List<NodeReference> preferredMemberNodeList = new ArrayList<NodeReference>();
    private List<NodeReference> blockedMemberNodeList = new ArrayList<NodeReference>();
    private Boolean replicationAllowed;
    private Integer numberReplicas;

    /** 
     * Get the list of 'preferredMemberNode' element items.
     * 
     * @return list
     */
    public List<NodeReference> getPreferredMemberNodeList() {
        return preferredMemberNodeList;
    }

    /** 
     * Set the list of 'preferredMemberNode' element items.
     * 
     * @param list
     */
    public void setPreferredMemberNodeList(List<NodeReference> list) {
        preferredMemberNodeList = list;
    }

    /** 
     * Get the number of 'preferredMemberNode' element items.
     * @return count
     */
    public int sizePreferredMemberNodeList() {
        return preferredMemberNodeList.size();
    }

    /** 
     * Add a 'preferredMemberNode' element item.
     * @param item
     */
    public void addPreferredMemberNode(NodeReference item) {
        preferredMemberNodeList.add(item);
    }

    /** 
     * Get 'preferredMemberNode' element item by position.
     * @return item
     * @param index
     */
    public NodeReference getPreferredMemberNode(int index) {
        return preferredMemberNodeList.get(index);
    }

    /** 
     * Remove all 'preferredMemberNode' element items.
     */
    public void clearPreferredMemberNodeList() {
        preferredMemberNodeList.clear();
    }

    /** 
     * Get the list of 'blockedMemberNode' element items.
     * 
     * @return list
     */
    public List<NodeReference> getBlockedMemberNodeList() {
        return blockedMemberNodeList;
    }

    /** 
     * Set the list of 'blockedMemberNode' element items.
     * 
     * @param list
     */
    public void setBlockedMemberNodeList(List<NodeReference> list) {
        blockedMemberNodeList = list;
    }

    /** 
     * Get the number of 'blockedMemberNode' element items.
     * @return count
     */
    public int sizeBlockedMemberNodeList() {
        return blockedMemberNodeList.size();
    }

    /** 
     * Add a 'blockedMemberNode' element item.
     * @param item
     */
    public void addBlockedMemberNode(NodeReference item) {
        blockedMemberNodeList.add(item);
    }

    /** 
     * Get 'blockedMemberNode' element item by position.
     * @return item
     * @param index
     */
    public NodeReference getBlockedMemberNode(int index) {
        return blockedMemberNodeList.get(index);
    }

    /** 
     * Remove all 'blockedMemberNode' element items.
     */
    public void clearBlockedMemberNodeList() {
        blockedMemberNodeList.clear();
    }

    /** 
     * Get the 'replicationAllowed' attribute value.
     * 
     * @return value
     */
    public Boolean getReplicationAllowed() {
        return replicationAllowed;
    }

    /** 
     * Set the 'replicationAllowed' attribute value.
     * 
     * @param replicationAllowed
     */
    public void setReplicationAllowed(Boolean replicationAllowed) {
        this.replicationAllowed = replicationAllowed;
    }

    /** 
     * Get the 'numberReplicas' attribute value.
     * 
     * @return value
     */
    public Integer getNumberReplicas() {
        return numberReplicas;
    }

    /** 
     * Set the 'numberReplicas' attribute value.
     * 
     * @param numberReplicas
     */
    public void setNumberReplicas(Integer numberReplicas) {
        this.numberReplicas = numberReplicas;
    }
}
