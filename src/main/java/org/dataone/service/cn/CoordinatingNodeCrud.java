package org.dataone.service.cn;

import java.io.InputStream;
import java.util.List;

import org.dataone.service.exceptions.InvalidRequest;
import org.dataone.service.exceptions.NotAuthorized;
import org.dataone.service.exceptions.NotFound;
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
        throws NotAuthorized, NotFound;
    public SystemMetadata getSystemMetadata(AuthToken token, IdentifierType guid)
        throws NotAuthorized, NotFound, InvalidRequest;
    public List<String> resolve(AuthToken token, IdentifierType guid)
        throws NotAuthorized, NotFound, InvalidRequest;
    public IdentifierType reserveId(AuthToken token, String scope, IdentifierFormatType format) 
        throws NotAuthorized, InvalidRequest;
    public IdentifierType reserveId(AuthToken token, String scope) 
        throws NotAuthorized, InvalidRequest;
    public IdentifierType reserveId(AuthToken token, IdentifierFormatType format) 
        throws NotAuthorized, InvalidRequest;
    public IdentifierType reserveId(AuthToken token) 
        throws NotAuthorized, InvalidRequest;
    public boolean assertRelation(AuthToken token, IdentifierType subjectId, 
        String relationship, IdentifierType objectId) 
        throws NotAuthorized, NotFound, InvalidRequest;
}
