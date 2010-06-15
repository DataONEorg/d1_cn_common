/**
 * Copyright 2010 Regents of the University of California and the
 *                National Center for Ecological Analysis and Synthesis
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.dataone.service.mn;

import java.io.InputStream;
import java.util.Date;

import org.dataone.service.exceptions.IdentifierNotUnique;
import org.dataone.service.exceptions.InsufficientResources;
import org.dataone.service.exceptions.InvalidRequest;
import org.dataone.service.exceptions.InvalidSystemMetadata;
import org.dataone.service.exceptions.InvalidToken;
import org.dataone.service.exceptions.NotAuthorized;
import org.dataone.service.exceptions.NotFound;
import org.dataone.service.exceptions.NotImplemented;
import org.dataone.service.exceptions.ServiceFailure;
import org.dataone.service.exceptions.UnsupportedType;
import org.dataone.service.types.AuthToken;
import org.dataone.service.types.Checksum;
import org.dataone.service.types.DescribeResponse;
import org.dataone.service.types.Identifier;
import org.dataone.service.types.Log;
import org.dataone.service.types.SystemMetadata;
import org.dataone.service.types.Event;

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
    public InputStream get(AuthToken token, Identifier guid)
        throws InvalidToken, ServiceFailure, NotAuthorized, NotFound, NotImplemented;
    public SystemMetadata getSystemMetadata(AuthToken token, Identifier guid)
        throws InvalidToken, ServiceFailure, NotAuthorized, NotFound, 
        InvalidRequest, NotImplemented;
    public Log getLogRecords(AuthToken token, Date fromDate, Date toDate, Event event)
        throws InvalidToken, ServiceFailure, NotAuthorized, InvalidRequest, 
        NotImplemented;
    public DescribeResponse describe(AuthToken token, Identifier guid)
        throws InvalidToken, ServiceFailure, NotAuthorized, NotFound, 
        NotImplemented;
    
    // For 0.4 milestone
    public Identifier create(AuthToken token, Identifier guid,
        InputStream object, SystemMetadata sysmeta) throws InvalidToken, 
        ServiceFailure, NotAuthorized, IdentifierNotUnique, UnsupportedType, 
        InsufficientResources, InvalidSystemMetadata, NotImplemented;
    public Identifier update(AuthToken token, Identifier guid,
        InputStream object, Identifier obsoletedGuid, SystemMetadata sysmeta)
        throws InvalidToken, ServiceFailure, NotAuthorized, IdentifierNotUnique, 
        UnsupportedType, InsufficientResources, NotFound, InvalidSystemMetadata, 
        NotImplemented;
    public Identifier delete(AuthToken token, Identifier guid)
        throws InvalidToken, ServiceFailure, NotAuthorized, NotFound, 
        NotImplemented;
    public Checksum getChecksum(AuthToken token, Identifier guid)
        throws InvalidToken, ServiceFailure, NotAuthorized, NotFound, 
        InvalidRequest, NotImplemented;
    public Checksum getChecksum(AuthToken token, Identifier guid,
        String checksumAlgorithm) throws InvalidToken, ServiceFailure, 
        NotAuthorized, NotFound, InvalidRequest, NotImplemented;
}
