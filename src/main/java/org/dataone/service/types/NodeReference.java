
package org.dataone.service.types;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:simpleType xmlns:ns="http://dataone.org/service/types/common/0.5" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="NodeReference">
 *   &lt;xs:restriction base="xs:value"/>
 * &lt;/xs:simpleType>
 * </pre>
 */
public class NodeReference
{
    private String value;

    /** 
     * Get the 'NodeReference' simpleType value.
     * 
     * @return value
     */
    public String getValue() {
        return value;
    }

    /** 
     * Set the 'NodeReference' simpleType value.
     * 
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }
}
