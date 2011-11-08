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

package org.dataone.service.cn.v1;

import org.dataone.service.exceptions.InvalidRequest;
import org.dataone.service.exceptions.InvalidToken;
import org.dataone.service.exceptions.NotAuthorized;
import org.dataone.service.exceptions.NotFound;
import org.dataone.service.exceptions.NotImplemented;
import org.dataone.service.exceptions.ServiceFailure;
import org.dataone.service.exceptions.VersionMismatch;

import org.dataone.service.types.v1.Permission;
import org.dataone.service.types.v1.Session;
import org.dataone.service.types.v1.Identifier;
import org.dataone.service.types.v1.Subject;
import org.dataone.service.types.v1.AccessPolicy;

/**
 * The DataONE CoordinatingNode Tier2 Authorization interface.  This defines an
 * implementation interface for Coordinating Nodes that wish to build an
 * implementation that is compliant with the DataONE service definitions.
 *
 * @author Matthew Jones
 */
public interface CNAuthorization {

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNAuthorization.setOwner
     */
    public Identifier setOwner(Session session, Identifier pid, Subject userId, 
         long serialVersion)
        throws InvalidToken, ServiceFailure, NotFound, NotAuthorized, 
        NotImplemented, InvalidRequest, VersionMismatch;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNAuthorization.isAuthorized
     */
    public boolean isAuthorized(Session session, Identifier pid, Permission permission)
        throws ServiceFailure, InvalidToken, NotFound, NotAuthorized, 
        NotImplemented, InvalidRequest;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNAuthorization.setAccessPolicy
     */
    public boolean setAccessPolicy(Session session, Identifier pid, 
        AccessPolicy policy, long serialVersion) 
        throws InvalidToken, NotFound, NotImplemented, NotAuthorized, 
        ServiceFailure, InvalidRequest, VersionMismatch;
}
