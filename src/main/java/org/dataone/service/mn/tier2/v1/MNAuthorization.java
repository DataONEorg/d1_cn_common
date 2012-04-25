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

package org.dataone.service.mn.tier2.v1;

import java.util.Date;

import org.dataone.service.exceptions.InvalidRequest;
import org.dataone.service.exceptions.InvalidToken;
import org.dataone.service.exceptions.NotAuthorized;
import org.dataone.service.exceptions.NotFound;
import org.dataone.service.exceptions.NotImplemented;
import org.dataone.service.exceptions.ServiceFailure;
import org.dataone.service.types.v1.Identifier;
import org.dataone.service.types.v1.Permission;
import org.dataone.service.types.v1.Session;

/**
 * The DataONE Member Node Tier 2 Authorization interface.  This defines an
 * implementation interface for Member Nodes that wish to build an
 * implementation that is compliant with the DataONE service definitions.
 *
 * @author Matthew Jones
 */
public interface MNAuthorization {

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/MN_APIs.html#MN_auth.isAuthorized
     */
    public boolean isAuthorized(Identifier pid, Permission action)
            throws ServiceFailure, InvalidRequest, InvalidToken, NotFound, 
                   NotAuthorized, NotImplemented;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/MN_APIs.html#MN_auth.systemMetadataChanged
     */
    public boolean systemMetadataChanged(Identifier pid, long serialVersion,
    	Date dateSystemMetadataLastModified)
    throws InvalidToken, ServiceFailure, NotAuthorized, NotImplemented, InvalidRequest;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/MN_APIs.html#MN_auth.isAuthorized
     */
	@Deprecated
    public boolean isAuthorized(Session session, Identifier pid, Permission action)
            throws ServiceFailure, InvalidRequest, InvalidToken, NotFound, 
                   NotAuthorized, NotImplemented;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/MN_APIs.html#MN_auth.systemMetadataChanged
     */
	@Deprecated
    public boolean systemMetadataChanged(Session session, Identifier pid, long serialVersion,
    	Date dateSystemMetadataLastModified)
    throws InvalidToken, ServiceFailure, NotAuthorized, NotImplemented, InvalidRequest;

	
}
