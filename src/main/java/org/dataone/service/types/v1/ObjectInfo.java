
package org.dataone.service.types.v1;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="ObjectInfo">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:Identifier" name="identifier" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="ns:ObjectFormatIdentifier" name="formatId"/>
 *     &lt;xs:element type="ns:Checksum" name="checksum" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:dateTime" name="dateSysMetadataModified"/>
 *     &lt;xs:element type="xs:long" name="size"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class ObjectInfo implements Serializable
{
    private static final long serialVersionUID = 10000000;
    private Identifier identifier;
    private ObjectFormatIdentifier formatId;
    private Checksum checksum;
    private Date dateSysMetadataModified;
    private BigInteger size;

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
}
