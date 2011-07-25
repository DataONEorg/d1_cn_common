
package org.dataone.service.types.v1;

import java.io.Serializable;

/** 
 * An :term:`identifier` (:term:`PID`) in the DataONE system. This is
 equivalent to a Unicode string of printable characters, excluding
 whitespace. All representations of identifiers must be encoded in 7-bit
 ASCII or UTF-8.
 Identifiers have a maximum length of 800 characters. Some discussion on this
 is described in `ticket 577`_.
 .. _ticket 577: https://redmine.dataone.org/issues/577

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Identifier">
 *   &lt;xs:simpleContent>
 *     &lt;xs:extension base="xs:string"/>
 *   &lt;/xs:simpleContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class Identifier implements Serializable
{
    private String value;

    /** 
     * Get the 'Identifier' complexType value.
     * 
     * @return value
     */
    public String getValue() {
        return value;
    }

    /** 
     * Set the 'Identifier' complexType value.
     * 
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }
}
