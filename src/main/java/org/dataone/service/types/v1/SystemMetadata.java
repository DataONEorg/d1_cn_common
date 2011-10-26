
package org.dataone.service.types.v1;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** 
 * 
 A set of low level information about an object in the DataONE system.
 SystemMetadata is described in :mod:`SystemMetadata`.

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="SystemMetadata">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="xs:long" name="serialVersion" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="ns:Identifier" name="identifier"/>
 *     &lt;xs:element type="ns:ObjectFormatIdentifier" name="formatId"/>
 *     &lt;xs:element type="xs:long" name="size"/>
 *     &lt;xs:element type="ns:Checksum" name="checksum"/>
 *     &lt;xs:element type="ns:Subject" name="submitter"/>
 *     &lt;xs:element type="ns:Subject" name="rightsHolder"/>
 *     &lt;xs:element type="ns:AccessPolicy" name="accessPolicy" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="ns:ReplicationPolicy" name="replicationPolicy" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="ns:Identifier" name="obsoletes" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="ns:Identifier" name="obsoletedBy" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:dateTime" name="dateUploaded"/>
 *     &lt;xs:element type="xs:dateTime" name="dateSysMetadataModified"/>
 *     &lt;xs:element type="ns:NodeReference" name="originMemberNode" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="ns:NodeReference" name="authoritativeMemberNode" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="ns:Replica" name="replica" minOccurs="0" maxOccurs="unbounded"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class SystemMetadata implements Serializable
{
    private static final long serialVersionUID = 10000000;
    private BigInteger serialVersion;
    private Identifier identifier;
    private ObjectFormatIdentifier formatId;
    private BigInteger size;
    private Checksum checksum;
    private Subject submitter;
    private Subject rightsHolder;
    private AccessPolicy accessPolicy;
    private ReplicationPolicy replicationPolicy;
    private Identifier obsoletes;
    private Identifier obsoletedBy;
    private Date dateUploaded;
    private Date dateSysMetadataModified;
    private NodeReference originMemberNode;
    private NodeReference authoritativeMemberNode;
    private List<Replica> replicaList = new ArrayList<Replica>();

    /** 
     * Get the 'serialVersion' element value.
     * 
     * @return value
     */
    public BigInteger getSerialVersion() {
        return serialVersion;
    }

    /** 
     * Set the 'serialVersion' element value.
     * 
     * @param serialVersion
     */
    public void setSerialVersion(BigInteger serialVersion) {
        this.serialVersion = serialVersion;
    }

    /** 
     * Get the 'identifier' element value.
     * 
     * @return value
     */
    public Identifier getIdentifier() {
        return identifier;
    }

    /** 
     * Set the 'identifier' element value.
     * 
     * @param identifier
     */
    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    /** 
     * Get the 'formatId' element value.
     * 
     * @return value
     */
    public ObjectFormatIdentifier getFormatId() {
        return formatId;
    }

    /** 
     * Set the 'formatId' element value.
     * 
     * @param formatId
     */
    public void setFormatId(ObjectFormatIdentifier formatId) {
        this.formatId = formatId;
    }

    /** 
     * Get the 'size' element value.
     * 
     * @return value
     */
    public BigInteger getSize() {
        return size;
    }

    /** 
     * Set the 'size' element value.
     * 
     * @param size
     */
    public void setSize(BigInteger size) {
        this.size = size;
    }

    /** 
     * Get the 'checksum' element value.
     * 
     * @return value
     */
    public Checksum getChecksum() {
        return checksum;
    }

    /** 
     * Set the 'checksum' element value.
     * 
     * @param checksum
     */
    public void setChecksum(Checksum checksum) {
        this.checksum = checksum;
    }

    /** 
     * Get the 'submitter' element value.
     * 
     * @return value
     */
    public Subject getSubmitter() {
        return submitter;
    }

    /** 
     * Set the 'submitter' element value.
     * 
     * @param submitter
     */
    public void setSubmitter(Subject submitter) {
        this.submitter = submitter;
    }

    /** 
     * Get the 'rightsHolder' element value.
     * 
     * @return value
     */
    public Subject getRightsHolder() {
        return rightsHolder;
    }

    /** 
     * Set the 'rightsHolder' element value.
     * 
     * @param rightsHolder
     */
    public void setRightsHolder(Subject rightsHolder) {
        this.rightsHolder = rightsHolder;
    }

    /** 
     * Get the 'accessPolicy' element value.
     * 
     * @return value
     */
    public AccessPolicy getAccessPolicy() {
        return accessPolicy;
    }

    /** 
     * Set the 'accessPolicy' element value.
     * 
     * @param accessPolicy
     */
    public void setAccessPolicy(AccessPolicy accessPolicy) {
        this.accessPolicy = accessPolicy;
    }

    /** 
     * Get the 'replicationPolicy' element value.
     * 
     * @return value
     */
    public ReplicationPolicy getReplicationPolicy() {
        return replicationPolicy;
    }

    /** 
     * Set the 'replicationPolicy' element value.
     * 
     * @param replicationPolicy
     */
    public void setReplicationPolicy(ReplicationPolicy replicationPolicy) {
        this.replicationPolicy = replicationPolicy;
    }

    /** 
     * Get the 'obsoletes' element value.
     * 
     * @return value
     */
    public Identifier getObsoletes() {
        return obsoletes;
    }

    /** 
     * Set the 'obsoletes' element value.
     * 
     * @param obsoletes
     */
    public void setObsoletes(Identifier obsoletes) {
        this.obsoletes = obsoletes;
    }

    /** 
     * Get the 'obsoletedBy' element value.
     * 
     * @return value
     */
    public Identifier getObsoletedBy() {
        return obsoletedBy;
    }

    /** 
     * Set the 'obsoletedBy' element value.
     * 
     * @param obsoletedBy
     */
    public void setObsoletedBy(Identifier obsoletedBy) {
        this.obsoletedBy = obsoletedBy;
    }

    /** 
     * Get the 'dateUploaded' element value.
     * 
     * @return value
     */
    public Date getDateUploaded() {
        return dateUploaded;
    }

    /** 
     * Set the 'dateUploaded' element value.
     * 
     * @param dateUploaded
     */
    public void setDateUploaded(Date dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    /** 
     * Get the 'dateSysMetadataModified' element value.
     * 
     * @return value
     */
    public Date getDateSysMetadataModified() {
        return dateSysMetadataModified;
    }

    /** 
     * Set the 'dateSysMetadataModified' element value.
     * 
     * @param dateSysMetadataModified
     */
    public void setDateSysMetadataModified(Date dateSysMetadataModified) {
        this.dateSysMetadataModified = dateSysMetadataModified;
    }

    /** 
     * Get the 'originMemberNode' element value.
     * 
     * @return value
     */
    public NodeReference getOriginMemberNode() {
        return originMemberNode;
    }

    /** 
     * Set the 'originMemberNode' element value.
     * 
     * @param originMemberNode
     */
    public void setOriginMemberNode(NodeReference originMemberNode) {
        this.originMemberNode = originMemberNode;
    }

    /** 
     * Get the 'authoritativeMemberNode' element value.
     * 
     * @return value
     */
    public NodeReference getAuthoritativeMemberNode() {
        return authoritativeMemberNode;
    }

    /** 
     * Set the 'authoritativeMemberNode' element value.
     * 
     * @param authoritativeMemberNode
     */
    public void setAuthoritativeMemberNode(NodeReference authoritativeMemberNode) {
        this.authoritativeMemberNode = authoritativeMemberNode;
    }

    /** 
     * Get the list of 'replica' element items.
     * 
     * @return list
     */
    public List<Replica> getReplicaList() {
        return replicaList;
    }

    /** 
     * Set the list of 'replica' element items.
     * 
     * @param list
     */
    public void setReplicaList(List<Replica> list) {
        replicaList = list;
    }

    /** 
     * Get the number of 'replica' element items.
     * @return count
     */
    public int sizeReplicaList() {
        return replicaList.size();
    }

    /** 
     * Add a 'replica' element item.
     * @param item
     */
    public void addReplica(Replica item) {
        replicaList.add(item);
    }

    /** 
     * Get 'replica' element item by position.
     * @return item
     * @param index
     */
    public Replica getReplica(int index) {
        return replicaList.get(index);
    }

    /** 
     * Remove all 'replica' element items.
     */
    public void clearReplicaList() {
        replicaList.clear();
    }
}
