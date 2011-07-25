
package org.dataone.service.types.v1;

import java.io.Serializable;

/** 
 * An ObjectFormat is the structure returned from the 
 getFormat() method of the CN REST interface.  It provides the 
 unique identifierand the name associated with the object format.  
 Future versions may contain additional structured content from 
 external common typing systems.

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="ObjectFormat">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:ObjectFormatIdentifier" name="fmtid" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="formatName" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:boolean" name="scienceMetadata" minOccurs="1" maxOccurs="1"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class ObjectFormat implements Serializable
{
    private ObjectFormatIdentifier fmtid;
    private String formatName;
    private boolean scienceMetadata;

    /** 
     * Get the 'fmtid' element value. 
                    The unique identifier of the object format in the DataONE
                    Object Format Vocabulary.  The identifier should comply with
                    DataONE Identifier rules, i.e. no whitespace, UTF-8 or 
                    US-ASCII printable characters.
                
     * 
     * @return value
     */
    public ObjectFormatIdentifier getFmtid() {
        return fmtid;
    }

    /** 
     * Set the 'fmtid' element value. 
                    The unique identifier of the object format in the DataONE
                    Object Format Vocabulary.  The identifier should comply with
                    DataONE Identifier rules, i.e. no whitespace, UTF-8 or 
                    US-ASCII printable characters.
                
     * 
     * @param fmtid
     */
    public void setFmtid(ObjectFormatIdentifier fmtid) {
        this.fmtid = fmtid;
    }

    /** 
     * Get the 'formatName' element value. 
                For objects that are typed using a Document Type Definition, 
                this lists the well-known and accepted named version of the DTD.
              
     * 
     * @return value
     */
    public String getFormatName() {
        return formatName;
    }

    /** 
     * Set the 'formatName' element value. 
                For objects that are typed using a Document Type Definition, 
                this lists the well-known and accepted named version of the DTD.
              
     * 
     * @param formatName
     */
    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }

    /** 
     * Get the 'scienceMetadata' element value. 
                A boolean field indicating whether or not this format is science
                metadata describing a science data object.  If the format is a
                self-describing data format that includes science metadata, then 
                the field should also be set to true.
              
     * 
     * @return value
     */
    public boolean isScienceMetadata() {
        return scienceMetadata;
    }

    /** 
     * Set the 'scienceMetadata' element value. 
                A boolean field indicating whether or not this format is science
                metadata describing a science data object.  If the format is a
                self-describing data format that includes science metadata, then 
                the field should also be set to true.
              
     * 
     * @param scienceMetadata
     */
    public void setScienceMetadata(boolean scienceMetadata) {
        this.scienceMetadata = scienceMetadata;
    }
}
