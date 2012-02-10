
package org.dataone.service.types.v1;

import java.io.Serializable;

/** 
 * A string used to identify an instance of
 :class:`Types.ObjectFormat` and MUST be unique within an instance of
 :class:`Types.ObjectFormatList`. 
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:simpleType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="ObjectFormatIdentifier">
 *   &lt;xs:restriction base="xs:string"/>
 * &lt;/xs:simpleType>
 * </pre>
 */
public class ObjectFormatIdentifier implements Serializable, Comparable
{
    private static final long serialVersionUID = 10000000;
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

    /** 
     * 1 value 2 Value 3 String 4 (java.lang.String) 5 ObjectFormatIdentifier  value is a string, override equals of ObjectFormatIdentifier.
     * @param other
     * @return boolean
     */
    @Override
    public boolean equals(Object other) {
        if (other == null || other.getClass() != this.getClass())
            return false;
        ObjectFormatIdentifier otherObjectFormatIdentifier = (ObjectFormatIdentifier) other;
        return value.equals(otherObjectFormatIdentifier.getValue());
    }

    /** 
     * return the hashcode of ObjectFormatIdentifier's string value.
     * @return int
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /** 
     * Compares order based on the String value of two objects of the same type.
     * @param other
     * @return int
     * @throws ClassCastException 
     */
    @Override
    public int compareTo(Object other) throws ClassCastException {
        ObjectFormatIdentifier otherObjectFormatIdentifier = (ObjectFormatIdentifier) other;
        return value.compareTo(otherObjectFormatIdentifier.getValue());
    }
}
