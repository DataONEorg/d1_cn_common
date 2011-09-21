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
        System.out.println("equals");
        IdentifierBase base = (IdentifierBase)other;
        return getValue().equals(base.getValue());
    }

    @Override
    public int hashCode()
    {
        System.out.println("hashcode");
        return getValue().hashCode();
    }

    public int compareTo(Object identifier) throws ClassCastException {
        IdentifierBase id = (IdentifierBase)identifier;
        return getValue().compareTo(id.getValue());
    }

}
