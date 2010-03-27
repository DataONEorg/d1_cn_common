package org.dataone.service.cn;

import org.dataone.service.exceptions.AuthenticationTimeout;
import org.dataone.service.exceptions.IdentifierNotUnique;
import org.dataone.service.exceptions.InvalidCredentials;
import org.dataone.service.exceptions.InvalidToken;
import org.dataone.service.exceptions.NotAuthorized;
import org.dataone.service.exceptions.NotFound;
import org.dataone.service.types.AccessRule;
import org.dataone.service.types.AuthToken;
import org.dataone.service.types.IdentifierType;
import org.dataone.service.types.PrincipalType;

/**
 * The DataONE Coordinating Node Authorization programmatic interface.  This defines an
 * implementation interface for Coordinating Nodes that wish to build an
 * implementation that is compliant with the DataONE service definitions.
 *
 * @author Matthew Jones
 */
public interface CoordinatingNodeAuthorization 
{
    public AuthToken login(String user, String password)
        throws InvalidCredentials, AuthenticationTimeout;
    
    //public void logout(AuthToken token) 
    //    throws InvalidCredentials, AuthenticationTimeout;
    
    public boolean setAccess(AuthToken token, IdentifierType guid, AccessRule accessLevel)
        throws NotFound, NotAuthorized;
    
    public IdentifierType setOwner(AuthToken token, IdentifierType guid, PrincipalType userId)
        throws InvalidToken, NotAuthorized, NotFound;
    
    public PrincipalType newAccount(String username, String password)
        throws IdentifierNotUnique, InvalidCredentials;
    
    public boolean verify(AuthToken token) throws NotAuthorized;
    
    public boolean isAuthorized(AuthToken token, IdentifierType guid, String operation)
        throws InvalidToken, NotFound, NotAuthorized;
}
