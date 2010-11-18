/**
 * This work was created by participants in the DataONE project, and is
 * jointly copyrighted by participating institutions in DataONE. For
 * more information on DataONE, see our web site at http://dataone.org.
 *
 *   Copyright ${year}
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
        NotImplemented, InvalidRequest;
    
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
        NotImplemented, InvalidRequest;
    public Checksum getChecksum(AuthToken token, Identifier guid)
        throws InvalidToken, ServiceFailure, NotAuthorized, NotFound, 
        InvalidRequest, NotImplemented;
    public Checksum getChecksum(AuthToken token, Identifier guid,
        String checksumAlgorithm) throws InvalidToken, ServiceFailure, 
        NotAuthorized, NotFound, InvalidRequest, NotImplemented;

    // XXX THIS IS BROKE, NEED TO FIX
    public void setAccess(AuthToken token, Identifier id, String principal, String permission,
            String permissionType, String permissionOrder)
      throws ServiceFailure;
}
