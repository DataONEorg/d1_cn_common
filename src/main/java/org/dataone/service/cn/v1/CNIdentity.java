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

import org.dataone.service.exceptions.IdentifierNotUnique;
import org.dataone.service.exceptions.InvalidCredentials;
import org.dataone.service.exceptions.InvalidRequest;
import org.dataone.service.exceptions.InvalidToken;
import org.dataone.service.exceptions.NotAuthorized;
import org.dataone.service.exceptions.NotFound;
import org.dataone.service.exceptions.NotImplemented;
import org.dataone.service.exceptions.ServiceFailure;

import org.dataone.service.types.v1.Group;
import org.dataone.service.types.v1.Person;
import org.dataone.service.types.v1.Session;
import org.dataone.service.types.v1.Subject;
import org.dataone.service.types.v1.SubjectInfo;
import org.dataone.service.types.v1.SubjectList;

/**
 * The DataONE CoordinatingNode Tier2 Identity interface.  This defines an
 * implementation interface for Coordinating Nodes that wish to build an
 * implementation that is compliant with the DataONE service definitions.
 *
 * @author Matthew Jones
 */
public interface CNIdentity {

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNIdentity.registerAccount
     */
    public Subject registerAccount(Person person) 
        throws ServiceFailure, NotAuthorized, IdentifierNotUnique, InvalidCredentials, 
        NotImplemented, InvalidRequest, InvalidToken;
    
    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNIdentity.updateAccount
     */
    public Subject updateAccount(Person person) 
    	throws ServiceFailure, NotAuthorized, InvalidCredentials, 
        NotImplemented, InvalidRequest, InvalidToken, NotFound;
    
    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNIdentity.verifyAccount
     */
    public boolean verifyAccount(Subject subject) 
        throws ServiceFailure, NotAuthorized, NotImplemented, InvalidToken, 
        InvalidRequest, NotFound;

    /** 
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNIdentity.getSubjectInfo
     */
    public SubjectInfo getSubjectInfo(Subject subject)
        throws ServiceFailure, NotAuthorized, NotImplemented, NotFound, InvalidToken;
  
    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNIdentity.listSubjects
     */
    public SubjectInfo listSubjects(String query, String status, Integer start, 
        Integer count) throws InvalidRequest, ServiceFailure, InvalidToken, NotAuthorized, 
        NotImplemented;
        
    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNIdentity.mapIdentity
     */
    public boolean mapIdentity(Subject primarySubject, Subject secondarySubject) 
        throws ServiceFailure, InvalidToken, NotAuthorized, NotFound, 
        NotImplemented, InvalidRequest, IdentifierNotUnique;
    
    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNIdentity.requestMapIdentity
     */
    public boolean requestMapIdentity(Subject subject) 
        throws ServiceFailure, InvalidToken, NotAuthorized, NotFound, 
        NotImplemented, InvalidRequest, IdentifierNotUnique;
    
    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNIdentity.confirmMapIdentity
     */
    public boolean confirmMapIdentity(Subject subject) 
        throws ServiceFailure, InvalidToken, NotAuthorized, NotFound, 
        NotImplemented;
    
    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNIdentity.getPendingMapIdentity
     */
    public SubjectInfo getPendingMapIdentity(Subject subject) 
        throws ServiceFailure, InvalidToken, NotAuthorized, NotFound, 
        NotImplemented;
    
    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNIdentity.denyMapIdentity
     */
    public boolean denyMapIdentity(Subject subject) 
        throws ServiceFailure, InvalidToken, NotAuthorized, NotFound, 
        NotImplemented;
    
    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNIdentity.removeMapIdentity
     */
    public boolean removeMapIdentity(Subject subject) 
        throws ServiceFailure, InvalidToken, NotAuthorized, NotFound, 
        NotImplemented;
    
    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNIdentity.createGroup
     */
    public Subject createGroup(Group group) 
        throws ServiceFailure, InvalidToken, NotAuthorized, NotImplemented, IdentifierNotUnique, InvalidRequest;
    
    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNIdentity.updateGroup
     */
    public boolean updateGroup(Group group) 
        throws ServiceFailure, InvalidToken, NotAuthorized, NotFound, NotImplemented, InvalidRequest;
 
///////
    
    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNIdentity.registerAccount
     */
    @Deprecated
    public Subject registerAccount(Session session, Person person) 
        throws ServiceFailure, NotAuthorized, IdentifierNotUnique, InvalidCredentials, 
        NotImplemented, InvalidRequest, InvalidToken;
    
    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNIdentity.updateAccount
     */
    @Deprecated
    public Subject updateAccount(Session session, Person person) 
    	throws ServiceFailure, NotAuthorized, InvalidCredentials, 
        NotImplemented, InvalidRequest, InvalidToken, NotFound;
    
    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNIdentity.verifyAccount
     */
    @Deprecated
    public boolean verifyAccount(Session session, Subject subject) 
        throws ServiceFailure, NotAuthorized, NotImplemented, InvalidToken, 
        InvalidRequest, NotFound;

    /** 
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNIdentity.getSubjectInfo
     */
    @Deprecated
    public SubjectInfo getSubjectInfo(Session session, Subject subject)
        throws ServiceFailure, NotAuthorized, NotImplemented, NotFound, InvalidToken;
  
    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNIdentity.listSubjects
     */
    @Deprecated
    public SubjectInfo listSubjects(Session session, String query, String status, Integer start, 
        Integer count) throws InvalidRequest, ServiceFailure, InvalidToken, NotAuthorized, 
        NotImplemented;
        
    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNIdentity.mapIdentity
     */
    @Deprecated
    public boolean mapIdentity(Session session, Subject primarySubject, Subject secondarySubject) 
        throws ServiceFailure, InvalidToken, NotAuthorized, NotFound, 
        NotImplemented, InvalidRequest, IdentifierNotUnique;
    
    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNIdentity.requestMapIdentity
     */
    @Deprecated
    public boolean requestMapIdentity(Session session, Subject subject) 
        throws ServiceFailure, InvalidToken, NotAuthorized, NotFound, 
        NotImplemented, InvalidRequest, IdentifierNotUnique;
    
    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNIdentity.confirmMapIdentity
     */
    @Deprecated
    public boolean confirmMapIdentity(Session session, Subject subject) 
        throws ServiceFailure, InvalidToken, NotAuthorized, NotFound, 
        NotImplemented;
    
    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNIdentity.getPendingMapIdentity
     */
    @Deprecated
    public SubjectInfo getPendingMapIdentity(Session session, Subject subject) 
        throws ServiceFailure, InvalidToken, NotAuthorized, NotFound, 
        NotImplemented;
    
    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNIdentity.denyMapIdentity
     */
    @Deprecated
    public boolean denyMapIdentity(Session session, Subject subject) 
        throws ServiceFailure, InvalidToken, NotAuthorized, NotFound, 
        NotImplemented;
    
    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNIdentity.removeMapIdentity
     */
    @Deprecated
    public boolean removeMapIdentity(Session session, Subject subject) 
        throws ServiceFailure, InvalidToken, NotAuthorized, NotFound, 
        NotImplemented;
    
    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNIdentity.createGroup
     */
    @Deprecated
    public Subject createGroup(Session session, Group group) 
        throws ServiceFailure, InvalidToken, NotAuthorized, NotImplemented, IdentifierNotUnique, InvalidRequest;
    
    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNIdentity.updateGroup
     */
    @Deprecated
    public boolean updateGroup(Session session, Group group) 
        throws ServiceFailure, InvalidToken, NotAuthorized, NotFound, NotImplemented, InvalidRequest;
 
    

//    /**
//     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNIdentity.isGroup
//     */
//    public boolean isGroup(Subject subject) 
//        throws ServiceFailure, InvalidToken, NotAuthorized, NotFound, 
//        NotImplemented, InvalidRequest;
//    
//    /**
//     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNIdentity.isPublic
//     */
//    public boolean isPublic(Subject subject) 
//        throws ServiceFailure, InvalidToken, NotAuthorized, NotFound, 
//        NotImplemented, InvalidRequest;
}
