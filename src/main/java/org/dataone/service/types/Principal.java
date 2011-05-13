
package org.dataone.service.types;

/** 
 * Person (user), Group, or Organization, or System.The formal name identifiying a user or group from the DataONE 
 Identity Management Service. The principal is represented by a unique,
 persistent, non-reassignable identifier value that follows the same
 constraints as the Identifier.Used by :mod:`SystemMetadata`
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:simpleType xmlns:ns="http://ns.dataone.org/service/types/0.6.1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Principal">
 *   &lt;xs:restriction base="xs:value"/>
 * &lt;/xs:simpleType>
 * </pre>
 */
public class Principal
{
    private String value;

    /** 
     * Get the 'Principal' simpleType value.
     * 
     * @return value
     */
    public String getValue() {
        return value;
    }

    /** 
     * Set the 'Principal' simpleType value.
     * 
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }
}
