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

import java.security.cert.X509Extension;
import java.util.List;

import org.dataone.service.exceptions.AuthenticationTimeout;
import org.dataone.service.exceptions.IdentifierNotUnique;
import org.dataone.service.exceptions.InvalidCredentials;
import org.dataone.service.exceptions.InvalidRequest;
import org.dataone.service.exceptions.InvalidToken;
import org.dataone.service.exceptions.NotAuthorized;
import org.dataone.service.exceptions.NotFound;
import org.dataone.service.exceptions.NotImplemented;
import org.dataone.service.exceptions.ServiceFailure;
import org.dataone.service.types.AuthToken;
import org.dataone.service.types.AuthType;
import org.dataone.service.types.Identifier;
import org.dataone.service.types.Subject;

/**
 * The DataONE Coordinating Node Authorization programmatic interface.  This defines an
 * implementation interface for Coordinating Nodes that wish to build an
 * implementation that is compliant with the DataONE service definitions.
 *
 * @author Matthew Jones
 * @deprecated
 */
public interface CoordinatingNodeAuthentication
{
    public AuthToken login(String user, String password)
        throws InvalidCredentials, AuthenticationTimeout, ServiceFailure, NotImplemented, InvalidRequest;

    public AuthToken login(String user, String password, AuthType type)
            throws InvalidCredentials, AuthenticationTimeout, NotImplemented, InvalidRequest, ServiceFailure;

    public AuthToken getAuthToken(X509Extension cert)
            throws InvalidCredentials, AuthenticationTimeout, ServiceFailure, NotImplemented, InvalidRequest ;
    //public void logout(AuthToken token) 
    //    throws InvalidCredentials, AuthenticationTimeout;
  
    public Identifier setOwner(AuthToken token, Identifier pid, Subject userId)
            throws ServiceFailure, InvalidToken, NotAuthorized, NotFound, NotImplemented, InvalidRequest;

     public Subject newAccount(String username, String password)
            throws ServiceFailure, IdentifierNotUnique, InvalidCredentials, NotImplemented, InvalidRequest;

     public Subject newAccount(String username, String password, AuthType type)
            throws ServiceFailure, IdentifierNotUnique, InvalidCredentials, NotImplemented, InvalidRequest;

     public boolean verifyToken(AuthToken token)
             throws ServiceFailure, NotAuthorized, NotImplemented, InvalidToken, InvalidRequest;

     public boolean mapIdentity(AuthToken token1, AuthToken token2)
             throws ServiceFailure, InvalidToken, NotAuthorized, NotFound, NotImplemented, InvalidRequest;

     public Identifier createGroup(AuthToken token, Identifier groupName)
             throws ServiceFailure, InvalidToken, NotAuthorized, NotFound, NotImplemented, InvalidRequest, IdentifierNotUnique;

     public Identifier createGroup(AuthToken token, Identifier groupName, List<Identifier> members)
             throws ServiceFailure, InvalidToken, NotAuthorized, NotFound, NotImplemented, InvalidRequest;
    

}
