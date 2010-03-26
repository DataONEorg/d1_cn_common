package org.dataone.service.mn;

import java.io.InputStream;
import java.util.Date;

import org.dataone.service.exceptions.IdentifierNotUnique;
import org.dataone.service.exceptions.InsufficientResources;
import org.dataone.service.exceptions.InvalidSystemMetadata;
import org.dataone.service.exceptions.NotAuthorized;
import org.dataone.service.exceptions.NotFound;
import org.dataone.service.exceptions.UnsupportedType;
import org.dataone.service.types.AuthToken;
import org.dataone.service.types.Checksum;
import org.dataone.service.types.DescribeResponse;
import org.dataone.service.types.IdentifierType;
import org.dataone.service.types.LogRecordSet;
import org.dataone.service.types.SystemMetadata;

/**
 * The DataONE MemberNode CRUD programmatic interface.  This defines an
 * implementation interface for Member Nodes that wish to build an
 * implementation that is compliant with the DataONE service definitions.
 *
 * @author Matthew Jones
 */
public interface MemberNodeCrud 
{
    // For 0.3 milestone
    public InputStream get(AuthToken token, IdentifierType guid)
        throws NotAuthorized, NotFound;
    public SystemMetadata getSystemMetadata(AuthToken token, IdentifierType guid)
        throws NotAuthorized, NotFound;
    public LogRecordSet getLogRecords(AuthToken token, Date fromDate, Date toDate)
        throws NotAuthorized;
    public DescribeResponse describe(AuthToken token, IdentifierType guid)
        throws NotAuthorized, NotFound;
    
    // For 0.4 milestone
    public IdentifierType create(AuthToken token, IdentifierType guid, 
        InputStream object, SystemMetadata sysmeta) throws NotAuthorized, 
        IdentifierNotUnique, UnsupportedType, 
        InsufficientResources, InvalidSystemMetadata;
    public IdentifierType update(AuthToken token, IdentifierType guid, 
        InputStream object, IdentifierType obsoletedGuid, SystemMetadata sysmeta) 
        throws NotAuthorized, IdentifierNotUnique, UnsupportedType, 
        InsufficientResources, NotFound, InvalidSystemMetadata;
    public IdentifierType delete(AuthToken token, IdentifierType guid)
        throws NotAuthorized, NotFound;
    public Checksum getChecksum(AuthToken token, IdentifierType guid)
        throws NotAuthorized, NotFound;
    public Checksum getChecksum(AuthToken token, IdentifierType guid, 
        String checksumAlgorithm) throws NotAuthorized, NotFound;
}
