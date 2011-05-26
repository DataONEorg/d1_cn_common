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

package org.dataone.service.cn;

import java.io.InputStream;
import java.util.List;
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
import org.dataone.service.types.IdentifierFormat;
import org.dataone.service.types.Identifier;
import org.dataone.service.types.ObjectLocationList;
import org.dataone.service.types.SystemMetadata;

/**
 * The DataONE CoordinatingNode CRUD programmatic interface.  This defines an
 * implementation interface for Coordinating Nodes that wish to build an
 * implementation that is compliant with the DataONE service definitions.
 *
 * @author Matthew Jones
 * @deprecated
 */
public interface CoordinatingNodeCrud 
{
    public InputStream get(AuthToken token, Identifier guid)
        throws InvalidToken, ServiceFailure, NotAuthorized, NotFound, 
        NotImplemented;
    
    public SystemMetadata getSystemMetadata(AuthToken token, Identifier guid)
        throws InvalidToken, ServiceFailure, NotAuthorized, NotFound, 
        InvalidRequest, NotImplemented;
    
    public ObjectLocationList resolve(AuthToken token, Identifier guid)
        throws InvalidToken, ServiceFailure, NotAuthorized, NotFound, 
        InvalidRequest, NotImplemented;
    
    public Identifier create(AuthToken token, Identifier guid,
            InputStream object, SystemMetadata sysmeta) throws InvalidToken,
            ServiceFailure, NotAuthorized, IdentifierNotUnique,
            UnsupportedType, InsufficientResources, InvalidSystemMetadata,
            NotImplemented;
    
    // new implementation matches MNcrud, but this method probably going away with the refactoring of the api's
    public Identifier update(AuthToken token, Identifier pid,
            InputStream object, Identifier newPid, SystemMetadata sysmeta)
            throws InvalidToken, ServiceFailure, NotAuthorized, IdentifierNotUnique,
            UnsupportedType, InsufficientResources, NotFound, InvalidSystemMetadata,
            NotImplemented, InvalidRequest;

    public Identifier reserveIdentifier(AuthToken token, String scope, IdentifierFormat format)
        throws InvalidToken, ServiceFailure, NotAuthorized, InvalidRequest, 
        NotImplemented;
    
    public Identifier reserveIdentifier(AuthToken token, String scope)
        throws InvalidToken, ServiceFailure, NotAuthorized, InvalidRequest, 
        NotImplemented;
    
    public Identifier reserveIdentifier(AuthToken token, IdentifierFormat format)
        throws InvalidToken, ServiceFailure, NotAuthorized, InvalidRequest, 
        NotImplemented;
    
    public Identifier reserveIdentifier (AuthToken token)
        throws InvalidToken, ServiceFailure, NotAuthorized, InvalidRequest, 
        NotImplemented;
    
    public boolean assertRelation(AuthToken token, Identifier subjectId,
        String relationship, Identifier objectId)
        throws InvalidToken, ServiceFailure, NotAuthorized, NotFound, 
        InvalidRequest, NotImplemented;
}
