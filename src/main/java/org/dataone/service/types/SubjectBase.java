/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dataone.service.types;

import java.io.Serializable;

import javax.security.auth.x500.X500Principal;
/**
 *
 * @author waltz
 */
public class SubjectBase implements Serializable, Comparable {

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
        SubjectBase otherId = (SubjectBase)other;
        String standardizedOtherValue =  standardizeDN(otherId.getValue());
        String standardizedValue = standardizeDN(getValue());
        return standardizedValue.equals(standardizedOtherValue);
    }

    @Override
    public int hashCode()
    {
        String standardizedValue = standardizeDN(getValue());
        return standardizedValue.hashCode();
    }

    public int compareTo(Object other) throws ClassCastException {
        SubjectBase otherId = (SubjectBase)other;
        String standardizedOtherValue =  standardizeDN(otherId.getValue());
        String standardizedValue = standardizeDN(getValue());
        return standardizedValue.compareTo(standardizedOtherValue);
    }
    /**
     * Returns D1-wide consistent Subject DN string representations
     * @param name the [reasonable] DN representation
     * @return the standard D1 representation
     */
    private String standardizeDN(String name) {
    	String standardizedName = null;
    	try {
    		X500Principal principal = new X500Principal(name);
    		standardizedName = principal.getName(X500Principal.RFC2253);
    	} catch (IllegalArgumentException e) {
    		// it's not an X500 principal, so pass through untouched
    		standardizedName = name;
    	}
    	return standardizedName;
    }
}
