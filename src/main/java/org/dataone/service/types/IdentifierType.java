package org.dataone.service.types;

/**
 * The DataONE Type to represent an identifier.
 *
 * @author Matthew Jones
 */
public class IdentifierType 
{
    public final static int OID = 0;
    public final static int LSID = 1;
    public final static int UUID = 2;
    public final static int LSRN = 3;
    public final static int DOI = 4;
    public final static int URI = 5;
    public final static int STRING = 6;

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
