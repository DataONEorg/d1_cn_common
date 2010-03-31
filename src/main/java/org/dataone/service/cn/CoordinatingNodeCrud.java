package org.dataone.service.cn;

import java.io.InputStream;
import java.util.List;

import org.dataone.service.exceptions.InvalidRequest;
import org.dataone.service.exceptions.InvalidToken;
import org.dataone.service.exceptions.NotAuthorized;
import org.dataone.service.exceptions.NotFound;
import org.dataone.service.exceptions.NotImplemented;
import org.dataone.service.exceptions.ServiceFailure;
import org.dataone.service.types.AuthToken;
import org.dataone.service.types.IdentifierFormatType;
import org.dataone.service.types.IdentifierType;
import org.dataone.service.types.SystemMetadata;

/**
 * The DataONE CoordinatingNode CRUD programmatic interface.  This defines an
 * implementation interface for Coordinating Nodes that wish to build an
 * implementation that is compliant with the DataONE service definitions.
 *
 * @author Matthew Jones
 */
public interface CoordinatingNodeCrud 
{
    public InputStream get(AuthToken token, IdentifierType guid)
        throws InvalidToken, ServiceFailure, NotAuthorized, NotFound, 
        NotImplemented;
    public SystemMetadata getSystemMetadata(AuthToken token, IdentifierType guid)
        throws InvalidToken, ServiceFailure, NotAuthorized, NotFound, 
        InvalidRequest, NotImplemented;
    public List<String> resolve(AuthToken token, IdentifierType guid)
        throws InvalidToken, ServiceFailure, NotAuthorized, NotFound, 
        InvalidRequest, NotImplemented;
    public IdentifierType reserveId(AuthToken token, String scope, IdentifierFormatType format) 
        throws InvalidToken, ServiceFailure, NotAuthorized, InvalidRequest, 
        NotImplemented;
    public IdentifierType reserveId(AuthToken token, String scope) 
        throws InvalidToken, ServiceFailure, NotAuthorized, InvalidRequest, 
        NotImplemented;
    public IdentifierType reserveId(AuthToken token, IdentifierFormatType format) 
        throws InvalidToken, ServiceFailure, NotAuthorized, InvalidRequest, 
        NotImplemented;
    public IdentifierType reserveId(AuthToken token) 
        throws InvalidToken, ServiceFailure, NotAuthorized, InvalidRequest, 
        NotImplemented;
    public boolean assertRelation(AuthToken token, IdentifierType subjectId, 
        String relationship, IdentifierType objectId) 
        throws InvalidToken, ServiceFailure, NotAuthorized, NotFound, 
        InvalidRequest, NotImplemented;
}
