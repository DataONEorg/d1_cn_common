package org.dataone.service.types;

/**
 * The DataONE Type to represent an identifier.
 *
 * @author Matthew Jones
 */
public class IdentifierType 
{
    private String id;

    /**
     * @param token the token to set
     */
    public IdentifierType(String id) {
        this.id = id;
    }

    /**
     * @return the identifier
     */
    public String getIdentifier() {
        return id;
    }
}
