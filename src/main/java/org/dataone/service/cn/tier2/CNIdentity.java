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

package org.dataone.service.cn.tier2;

import org.dataone.service.exceptions.IdentifierNotUnique;
import org.dataone.service.exceptions.InvalidCredentials;
import org.dataone.service.exceptions.InvalidRequest;
import org.dataone.service.exceptions.InvalidToken;
import org.dataone.service.exceptions.NotAuthorized;
import org.dataone.service.exceptions.NotFound;
import org.dataone.service.exceptions.NotImplemented;
import org.dataone.service.exceptions.ServiceFailure;
import org.dataone.service.types.AuthToken;
import org.dataone.service.types.Person;
import org.dataone.service.types.Session;
import org.dataone.service.types.Subject;
import org.dataone.service.types.SubjectList;

/**
 * The DataONE CoordinatingNode Tier2 Identity interface.  This defines an
 * implementation interface for Coordinating Nodes that wish to build an
 * implementation that is compliant with the DataONE service definitions.
 *
 * @author Matthew Jones
 */
public interface CNIdentity {

    public Subject registerAccount(Session session, Person person) 
        throws ServiceFailure, IdentifierNotUnique, InvalidCredentials, NotImplemented, InvalidRequest;
    
    public boolean verifyAccount(Session session, Subject subject) 
        throws ServiceFailure, NotAuthorized, NotImplemented, InvalidToken, InvalidRequest;

    public SubjectList getSubjectInfo(Session session, Subject subject)
        throws ServiceFailure, InvalidToken, NotAuthorized, NotImplemented;
  
    public SubjectList listSubjects(Session session, String query, int start, int count)
        throws ServiceFailure, InvalidToken, NotAuthorized, NotImplemented;
        
    public boolean mapIdentity(Session session, Subject secondarySubject) 
        throws ServiceFailure, InvalidToken, NotAuthorized, NotFound, NotImplemented, InvalidRequest;
    
    public boolean createGroup(Session session, Subject groupName) 
        throws ServiceFailure, InvalidToken, NotAuthorized, NotFound, NotImplemented,
            InvalidRequest, IdentifierNotUnique;

    public boolean addGroupMembers(Session session, Subject groupName, SubjectList members) 
        throws ServiceFailure, InvalidToken, NotAuthorized, NotFound, NotImplemented, InvalidRequest;
    
    public boolean removeGroupMembers(Session session, Subject groupName, SubjectList members) 
        throws ServiceFailure, InvalidToken, NotAuthorized, NotFound, NotImplemented, InvalidRequest;   
}
