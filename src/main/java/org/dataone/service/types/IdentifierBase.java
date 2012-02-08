/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dataone.service.types;

import java.io.Serializable;

/**
 *
 * @author waltz
 */
public class IdentifierBase implements Serializable, Comparable {

    String value;
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
    @Override
    public boolean equals(Object other)
    {
    	// enforce type equality first
    	if ( other == null || other.getClass() != this.getClass() ) return false;
    	
    	IdentifierBase base = (IdentifierBase)other;
        return getValue().equals(base.getValue());
    }

    @Override
    public int hashCode()
    {
        return getValue().hashCode();
    }

    /**
     * Compares order based on the String value of the IdentifierBase or sub-class
     * So, objects of different subclasses will sort together, and (sub)class type
     * does not contribute to the overall ordering, if the comparison is between
     * objects of different types.
     */
    public int compareTo(Object identifier) throws ClassCastException {
        IdentifierBase id = (IdentifierBase)identifier;
        return getValue().compareTo(id.getValue());
    }

}
