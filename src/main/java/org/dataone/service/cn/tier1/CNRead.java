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

package org.dataone.service.cn.tier1;

import java.io.InputStream;

import org.dataone.service.exceptions.IdentifierNotUnique;
import org.dataone.service.exceptions.InvalidRequest;
import org.dataone.service.exceptions.InvalidToken;
import org.dataone.service.exceptions.NotAuthorized;
import org.dataone.service.exceptions.NotFound;
import org.dataone.service.exceptions.NotImplemented;
import org.dataone.service.exceptions.ServiceFailure;
import org.dataone.service.exceptions.UnsupportedQueryType;
import org.dataone.service.types.Checksum;
import org.dataone.service.types.Identifier;
import org.dataone.service.types.ObjectList;
import org.dataone.service.types.ObjectLocationList;
import org.dataone.service.types.QueryType;
import org.dataone.service.types.SystemMetadata;

/**
 * The DataONE CoordinatingNode Tier1 Read interface.  This defines an
 * implementation interface for Coordinating Nodes that wish to build an
 * implementation that is compliant with the DataONE service definitions.
 *
 * @author Matthew Jones
 */
public interface CNRead 
{
    public InputStream get(Identifier pid)
        throws InvalidRequest, InvalidToken, ServiceFailure, NotAuthorized, NotFound, 
        NotImplemented;
    
    public SystemMetadata getSystemMetadata(Identifier pid)
        throws InvalidRequest, InvalidToken, ServiceFailure, NotAuthorized, NotFound, 
        NotImplemented;
    
    public ObjectLocationList resolve(Identifier pid)
        throws InvalidRequest, InvalidToken, ServiceFailure, NotAuthorized, NotFound, 
        NotImplemented;
    
    public Identifier reserveIdentifier(Identifier pid, String scope, String format)
        throws InvalidToken, ServiceFailure, NotAuthorized, InvalidRequest, IdentifierNotUnique,
        NotImplemented;
        
    public boolean assertRelation(Identifier pidOfSubject, String relationship, Identifier pidOfObject)
        throws InvalidToken, ServiceFailure, NotAuthorized, NotFound, 
        InvalidRequest, NotImplemented;
    
    public Checksum getChecksum(Identifier guid)
        throws InvalidToken, ServiceFailure, NotAuthorized, NotFound,
        InvalidRequest, NotImplemented;
    
    public ObjectList search(QueryType queryType, String query)
        throws InvalidToken, ServiceFailure, NotAuthorized, NotFound,
        InvalidRequest, NotImplemented, UnsupportedQueryType;
}
