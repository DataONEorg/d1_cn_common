package org.dataone.service.types;

/**
 * The DataONE Type to represent an authentication token.
 *
 * @author Matthew Jones
 */
public class AuthToken 
{
    private String token;

    /**
     * @param token the token to set
     */
    public AuthToken(String token) {
        this.token = token;
    }

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }
}
