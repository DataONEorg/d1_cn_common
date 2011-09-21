
package org.dataone.service.types.v1;

import java.io.Serializable;
import org.dataone.service.types.IdentifierBase;

/** 
 * Member Node identifier drawn from the DataONE
 :mod:`cn_register &lt;registry service&gt;`.
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="NodeReference">
 *   &lt;xs:simpleContent>
 *     &lt;xs:extension base="xs:string"/>
 *   &lt;/xs:simpleContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class NodeReference extends org.dataone.service.types.IdentifierBase
    implements  Serializable, Comparable
{
    private String value;

    /** 
     * Get the 'NodeReference' complexType value.
     * 
     * @return value
     */
    public String getValue() {
        return value;
    }

    /** 
     * Set the 'NodeReference' complexType value.
     * 
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }
}
