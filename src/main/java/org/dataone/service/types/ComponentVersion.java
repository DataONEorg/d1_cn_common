
package org.dataone.service.types;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:simpleType xmlns:ns="http://dataone.org/service/types/0.5.1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="ComponentVersion">
 *   &lt;xs:restriction base="xs:string"/>
 * &lt;/xs:simpleType>
 * </pre>
 */
public class ComponentVersion
{
    private String string;

    /** 
     * Get the 'ComponentVersion' simpleType value.
     * 
     * @return value
     */
    public String getValue() {
        return string;
    }

    /** 
     * Set the 'ComponentVersion' simpleType value.
     * 
     * @param string
     */
    public void setValue(String string) {
        this.string = string;
    }
}
