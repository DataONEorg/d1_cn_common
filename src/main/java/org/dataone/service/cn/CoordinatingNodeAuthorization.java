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

import org.dataone.service.exceptions.AuthenticationTimeout;
import org.dataone.service.exceptions.IdentifierNotUnique;
import org.dataone.service.exceptions.InvalidCredentials;
import org.dataone.service.exceptions.InvalidToken;
import org.dataone.service.exceptions.NotAuthorized;
import org.dataone.service.exceptions.NotFound;
import org.dataone.service.exceptions.ServiceFailure;
import org.dataone.service.types.AccessRule;
import org.dataone.service.types.AuthToken;
import org.dataone.service.types.Identifier;
import org.dataone.service.types.Principal;

/**
 * The DataONE Coordinating Node Authorization programmatic interface.  This defines an
 * implementation interface for Coordinating Nodes that wish to build an
 * implementation that is compliant with the DataONE service definitions.
 *
 * @author Matthew Jones
 */
public interface CoordinatingNodeAuthorization 
{
    public AuthToken login(String user, String password)
        throws InvalidCredentials, AuthenticationTimeout, ServiceFailure;
    
    //public void logout(AuthToken token) 
    //    throws InvalidCredentials, AuthenticationTimeout;
    
    public boolean setAccess(AuthToken token, Identifier id, String principal,
            String permission, String permissionType, String permissionOrder)
            throws ServiceFailure;
    
    public Identifier setOwner(AuthToken token, Identifier guid, Principal userId)
        throws InvalidToken, NotAuthorized, NotFound;
    
    public Principal newAccount(String username, String password)
        throws IdentifierNotUnique, InvalidCredentials;
    
    public boolean verify(AuthToken token) throws NotAuthorized;
    
    public boolean isAuthorized(AuthToken token, Identifier guid, String operation)
        throws InvalidToken, NotFound, NotAuthorized;
}
