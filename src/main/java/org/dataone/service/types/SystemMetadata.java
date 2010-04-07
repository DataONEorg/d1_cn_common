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
import java.util.Date;
import java.util.List;

/**
 * The DataONE Type to represent system metadata as an object.
 *
 * @author Matthew Jones
 */
public class SystemMetadata 
{
    private IdentifierType identifier;
    private ObjectFormat objectFormat;
    private long size;
    private PrincipalType submitter;
    private PrincipalType rightsHolder;
    private List<IdentifierType> obsoleteList = new ArrayList<IdentifierType>();
    private List<IdentifierType> obsoletedByList = new ArrayList<IdentifierType>();
    private List<IdentifierType> derivedFromList = new ArrayList<IdentifierType>();
    private List<IdentifierType> describeList = new ArrayList<IdentifierType>();
    private List<IdentifierType> describedByList = new ArrayList<IdentifierType>();
    private Checksum checksum;
    private Date embargoExpires;
    private List<AccessRule> accessRuleList = new ArrayList<AccessRule>();
    private ReplicationPolicy replicationPolicy;
    private Date dateUploaded;
    private Date dateSysMetadataModified;
    private NodeReferenceType originMemberNode;
    private NodeReferenceType authoritativeMemberNode;
    private List<Replica> replicaList = new ArrayList<Replica>();
        
    // TODO: add serialization and deserialization to XML {and JSON, CSV, ...}
    
    /**
     */
    public SystemMetadata(IdentifierType identifier, ObjectFormat objectFormat, 
            long size, PrincipalType submitter, PrincipalType rightsHolder,
            Checksum checksum) {
        this.setIdentifier(identifier);
        this.setObjectFormat(objectFormat);
        this.setSize(size);
        this.setSubmitter(submitter);
        this.setRightsHolder(rightsHolder);
        this.setChecksum(checksum);
    }

    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(IdentifierType identifier) {
        this.identifier = identifier;
    }

    /**
     * @return the identifier
     */
    public IdentifierType getIdentifier() {
        return identifier;
    }

    /**
     * @param format the format to set
     */
    public void setObjectFormat(ObjectFormat format) {
        this.objectFormat = format;
    }

    /**
     * @return the format
     */
    public ObjectFormat getObjectFormat() {
        return objectFormat;
    }

    /**
     * @param size the size to set
     */
    public void setSize(long size) {
        this.size = size;
    }

    /**
     * @return the size
     */
    public long getSize() {
        return size;
    }

    /**
     * @return the submitter
     */
    public PrincipalType getSubmitter() {
        return submitter;
    }

    /**
     * @param submitter the submitter to set
     */
    public void setSubmitter(PrincipalType submitter) {
        this.submitter = submitter;
    }

    /**
     * @return the rightsHolder
     */
    public PrincipalType getRightsHolder() {
        return rightsHolder;
    }

    /**
     * @param rightsHolder the rightsHolder to set
     */
    public void setRightsHolder(PrincipalType rightsHolder) {
        this.rightsHolder = rightsHolder;
    }

    /**
     * @return the obsoleteList
     */
    public List<IdentifierType> getObsoleteList() {
        return obsoleteList;
    }

    /**
     * @param obsoleteList the obsoleteList to set
     */
    public void setObsoleteList(List<IdentifierType> obsoleteList) {
        this.obsoleteList = obsoleteList;
    }

    /**
     * @return the obsoletedByList
     */
    public List<IdentifierType> getObsoletedByList() {
        return obsoletedByList;
    }

    /**
     * @param obsoletedByList the obsoletedByList to set
     */
    public void setObsoletedByList(List<IdentifierType> obsoletedByList) {
        this.obsoletedByList = obsoletedByList;
    }

    /**
     * @return the derivedFromList
     */
    public List<IdentifierType> getDerivedFromList() {
        return derivedFromList;
    }

    /**
     * @param derivedFromList the derivedFromList to set
     */
    public void setDerivedFromList(List<IdentifierType> derivedFromList) {
        this.derivedFromList = derivedFromList;
    }

    /**
     * @return the describeList
     */
    public List<IdentifierType> getDescribeList() {
        return describeList;
    }

    /**
     * @param describeList the describeList to set
     */
    public void setDescribeList(List<IdentifierType> describeList) {
        this.describeList = describeList;
    }

    /**
     * @return the describedByList
     */
    public List<IdentifierType> getDescribedByList() {
        return describedByList;
    }

    /**
     * @param describedByList the describedByList to set
     */
    public void setDescribedByList(List<IdentifierType> describedByList) {
        this.describedByList = describedByList;
    }

    /**
     * @return the checksum
     */
    public Checksum getChecksum() {
        return checksum;
    }

    /**
     * @param checksum the checksum to set
     */
    public void setChecksum(Checksum checksum) {
        this.checksum = checksum;
    }

    /**
     * @return the embargoExpires
     */
    public Date getEmbargoExpires() {
        return embargoExpires;
    }

    /**
     * @param embargoExpires the embargoExpires to set
     */
    public void setEmbargoExpires(Date embargoExpires) {
        this.embargoExpires = embargoExpires;
    }

    /**
     * @param accessRuleList the accessRuleList to set
     */
    public void setAccessRuleList(List<AccessRule> accessRuleList) {
        this.accessRuleList = accessRuleList;
    }

    /**
     * @return the accessRuleList
     */
    public List<AccessRule> getAccessRuleList() {
        return accessRuleList;
    }

    /**
     * @param replicationPolicy the replicationPolicy to set
     */
    public void setReplicationPolicy(ReplicationPolicy replicationPolicy) {
        this.replicationPolicy = replicationPolicy;
    }

    /**
     * @return the replicationPolicy
     */
    public ReplicationPolicy getReplicationPolicy() {
        return replicationPolicy;
    }

    /**
     * @return the dateUploaded
     */
    public Date getDateUploaded() {
        return dateUploaded;
    }

    /**
     * @param dateUploaded the dateUploaded to set
     */
    public void setDateUploaded(Date dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    /**
     * @return the dateSysMetadataModified
     */
    public Date getDateSysMetadataModified() {
        return dateSysMetadataModified;
    }

    /**
     * @param dateSysMetadataModified the dateSysMetadataModified to set
     */
    public void setDateSysMetadataModified(Date dateSysMetadataModified) {
        this.dateSysMetadataModified = dateSysMetadataModified;
    }

    /**
     * @return the originMemberNode
     */
    public NodeReferenceType getOriginMemberNode() {
        return originMemberNode;
    }

    /**
     * @param originMemberNode the originMemberNode to set
     */
    public void setOriginMemberNode(NodeReferenceType originMemberNode) {
        this.originMemberNode = originMemberNode;
    }

    /**
     * @return the authoritativeMemberNode
     */
    public NodeReferenceType getAuthoritativeMemberNode() {
        return authoritativeMemberNode;
    }

    /**
     * @param authoritativeMemberNode the authoritativeMemberNode to set
     */
    public void setAuthoritativeMemberNode(NodeReferenceType authoritativeMemberNode) {
        this.authoritativeMemberNode = authoritativeMemberNode;
    }

    /**
     * @param replicaList the replicaList to set
     */
    public void setReplicaList(List<Replica> replicaList) {
        this.replicaList = replicaList;
    }

    /**
     * @return the replicaList
     */
    public List<Replica> getReplicaList() {
        return replicaList;
    }
}
