
package org.dataone.service.types;

/** 
 * Member Node identifier drawn from the DataONE :mod:`cn_register &lt;registry service&gt;`.
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:simpleType xmlns:ns="http://ns.dataone.org/service/types/0.6.1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="NodeReference">
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
