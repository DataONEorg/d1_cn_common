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

package org.dataone.service.mn.tier2;

import org.dataone.service.exceptions.InvalidRequest;
import org.dataone.service.exceptions.InvalidToken;
import org.dataone.service.exceptions.NotAuthorized;
import org.dataone.service.exceptions.NotFound;
import org.dataone.service.exceptions.NotImplemented;
import org.dataone.service.exceptions.ServiceFailure;

import org.dataone.service.types.Session;
import org.dataone.service.types.Identifier;
import org.dataone.service.types.Permission;
import org.dataone.service.types.AccessPolicy;

/**
 * The DataONE Member Node Tier 2 Authorization interface.  This defines an
 * implementation interface for Member Nodes that wish to build an
 * implementation that is compliant with the DataONE service definitions.
 *
 * @author Matthew Jones
 */
@Deprecated
public interface MNAuthorization {

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/MN_APIs.html#MN_auth.isAuthorized
     */
    public boolean isAuthorized(Session cert, Identifier pid, Permission action)
            throws ServiceFailure, InvalidRequest, InvalidToken, NotFound, 
                   NotAuthorized, NotImplemented;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/MN_APIs.html#MN_auth.setAccess
     */
    public boolean setAccessPolicy(Session cert, Identifier pid, AccessPolicy accessPolicy)
            throws InvalidToken, ServiceFailure, NotFound, NotAuthorized, 
                   NotImplemented, InvalidRequest;
}
