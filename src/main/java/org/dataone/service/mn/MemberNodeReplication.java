package org.dataone.service.mn;

import org.dataone.service.exceptions.InvalidRequest;
import org.dataone.service.exceptions.NotAuthorized;
import org.dataone.service.exceptions.NotFound;
import org.dataone.service.types.AuthToken;
import org.dataone.service.types.IdentifierType;

/**
 * The DataONE MemberNode Replication programmatic interface.  This defines an
 * implementation interface for Member Nodes that wish to build an
 * implementation that is compliant with the DataONE service definitions.
 *
 * @author Matthew Jones
 */
public interface MemberNodeReplication 
{
    public void listObjects(AuthToken token, String query) 
        throws NotAuthorized, InvalidRequest;
    public void synchronizationComplete(AuthToken token, IdentifierType guid)
        throws NotAuthorized, NotFound;
    public void synchronizationFailed(AuthToken token, IdentifierType guid, 
            String problemDescription) throws NotAuthorized, NotFound;
}
