package org.dataone.service.mn;

import org.dataone.service.exceptions.AuthenticationTimeout;
import org.dataone.service.exceptions.InvalidCredentials;
import org.dataone.service.exceptions.InvalidToken;
import org.dataone.service.exceptions.NotAuthorized;
import org.dataone.service.exceptions.NotFound;
import org.dataone.service.exceptions.NotImplemented;
import org.dataone.service.types.AuthToken;
import org.dataone.service.types.IdentifierType;

/**
 * The DataONE MemberNode Authorization programmatic interface.  This defines an
 * implementation interface for Member Nodes that wish to build an
 * implementation that is compliant with the DataONE service definitions.
 *
 * @author Matthew Jones
 */
public interface MemberNodeAuthorization 
{
    public AuthToken login(String user, String password)
        throws InvalidCredentials, AuthenticationTimeout, NotImplemented;
    
    public void logout(AuthToken token) throws NotImplemented;
    
    public boolean isAuthorized(AuthToken token, IdentifierType guid, String operation)
        throws InvalidToken, NotFound, NotAuthorized, NotImplemented;
    
    public boolean verify(AuthToken token) throws NotImplemented;
}
