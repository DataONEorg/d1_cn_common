package org.dataone.service.types;


/**
 * The DataONE Type to represent a principal, which is a user, group, or role.
 *
 * @author Matthew Jones
 */
public class PrincipalType 
{
    private String dn;
    
    /**
     * Construct a new principal
     * @param dn the distinguished name of the principal
     */
    public PrincipalType(String dn) {
        this.dn = dn;
    }
    
    /**
     * @param dn the dn to set
     */
    public void setDn(String dn) {
        this.dn = dn;
    }

    /**
     * @return the dn
     */
    public String getDn() {
        return dn;
    }

}
