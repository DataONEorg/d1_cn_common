package org.dataone.service.mn;

import org.dataone.service.exceptions.InvalidRequest;
import org.dataone.service.exceptions.NotAuthorized;
import org.dataone.service.types.AuthToken;

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
}
