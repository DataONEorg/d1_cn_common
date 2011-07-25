
package org.dataone.service.types.v1;

import java.io.Serializable;

/** 
 *  An ObjectFormatIdentifier is a string identifying
 the object format. It must be unique in the containing ObjectFormatList.

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:simpleType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="ObjectFormatIdentifier">
 *   &lt;xs:restriction base="xs:string"/>
 * &lt;/xs:simpleType>
 * </pre>
 */
public class ObjectFormatIdentifier implements Serializable
{
    private String value;

    /** 
     * Get the 'ObjectFormatIdentifier' simpleType value.
     * 
     * @return value
     */
    public String getValue() {
        return value;
    }

    /** 
     * Set the 'ObjectFormatIdentifier' simpleType value.
     * 
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }
}
