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
import org.dataone.service.exceptions.VersionMismatch;

import org.dataone.service.types.v1.ChecksumAlgorithmList;
import org.dataone.service.types.v1.Identifier;
import org.dataone.service.types.v1.Log;
import org.dataone.service.types.v1.ObjectFormat;
import org.dataone.service.types.v1.ObjectFormatIdentifier;
import org.dataone.service.types.v1.ObjectFormatList;
import org.dataone.service.types.v1.Session;
import org.dataone.service.types.v1.Event;
import org.dataone.service.types.v1.NodeList;
import org.dataone.service.types.v1.Subject;
import org.dataone.service.types.v1.SystemMetadata;

/**
 * The DataONE CoordinatingNode Tier1 Core interface.  This defines an
 * implementation interface for Coordinating Nodes that wish to build an
 * implementation that is compliant with the DataONE service definitions.
 *
 * @author Matthew Jones
 */
public interface CNCore
{
	public final static String SERVICE_VERSION = "v1";
	

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CN_core.ping
     */
    public Date ping() 
    throws NotImplemented, ServiceFailure, InsufficientResources;
	
	/**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.listFormats
     */
	public ObjectFormatList listFormats()
        throws ServiceFailure, NotImplemented;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.getFormat
     */
    public ObjectFormat getFormat(ObjectFormatIdentifier formatid)
        throws ServiceFailure, NotFound, NotImplemented, InvalidRequest;
    
    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.getChecksumAlgorithms
     */
     public ChecksumAlgorithmList listChecksumAlgorithms()
       throws ServiceFailure, NotImplemented;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.getLogRecords
     */
    public Log getLogRecords(Date fromDate, Date toDate,
        Event event, String pidFilter, Integer start, Integer count) 
    throws InvalidToken, InvalidRequest, ServiceFailure,
        NotAuthorized, NotImplemented, InsufficientResources;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.listNodes
     */
    public NodeList listNodes() throws NotImplemented, ServiceFailure;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.reserveIdentifier
     */
    public Identifier reserveIdentifier(Identifier pid)
    	throws InvalidToken, ServiceFailure,
            NotAuthorized, IdentifierNotUnique, NotImplemented, InvalidRequest;
    
    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.generateIdentifier
     */
    public Identifier generateIdentifier(String scheme, String fragment)
    	throws InvalidToken, ServiceFailure,
            NotAuthorized, NotImplemented, InvalidRequest;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.hasReservation
     */
    public boolean hasReservation(Subject subject, Identifier pid)
    	throws InvalidToken, ServiceFailure,  NotFound,
            NotAuthorized, NotImplemented, InvalidRequest, IdentifierNotUnique;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.create
     */
    public Identifier create(Identifier pid, InputStream object,
            SystemMetadata sysmeta) throws InvalidToken, ServiceFailure,
            NotAuthorized, IdentifierNotUnique, UnsupportedType,
            InsufficientResources, InvalidSystemMetadata, NotImplemented,
            InvalidRequest;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.registerSystemMetadata
     */
     public Identifier registerSystemMetadata(Identifier pid,
        SystemMetadata sysmeta) throws NotImplemented, NotAuthorized,
        ServiceFailure, InvalidRequest, InvalidSystemMetadata, InvalidToken;
     
     /**
      * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.setObsoletedBy
      */
     public boolean setObsoletedBy(Identifier pid,
   			Identifier obsoletedByPid, long serialVersion)
   			throws NotImplemented, NotFound, NotAuthorized, ServiceFailure,
   			InvalidRequest, InvalidToken, VersionMismatch;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.delete
     */
    public Identifier delete(Identifier pid)
    throws InvalidToken, ServiceFailure, InvalidRequest, NotAuthorized, NotFound, NotImplemented;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.archive
     */
    public Identifier archive(Identifier pid)
    throws InvalidToken, ServiceFailure, InvalidRequest, NotAuthorized, NotFound, NotImplemented;
    
    ////

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.getLogRecords
     */
     @Deprecated
    public Log getLogRecords(Session session, Date fromDate, Date toDate,
        Event event, String pidFilter, Integer start, Integer count) 
    throws InvalidToken, InvalidRequest, ServiceFailure,
        NotAuthorized, NotImplemented, InsufficientResources;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.reserveIdentifier
     */
    @Deprecated
    public Identifier reserveIdentifier(Session session, Identifier pid)
    	throws InvalidToken, ServiceFailure,
            NotAuthorized, IdentifierNotUnique, NotImplemented, InvalidRequest;
    
    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.generateIdentifier
     */
    @Deprecated
    public Identifier generateIdentifier(Session session, String scheme, String fragment)
    	throws InvalidToken, ServiceFailure,
            NotAuthorized, NotImplemented, InvalidRequest;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.hasReservation
     */
    @Deprecated
    public boolean hasReservation(Session session, Subject subject, Identifier pid)
    	throws InvalidToken, ServiceFailure,  NotFound,
            NotAuthorized, NotImplemented, InvalidRequest, IdentifierNotUnique;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.create
     */
    @Deprecated
    public Identifier create(Session session, Identifier pid, InputStream object,
            SystemMetadata sysmeta) throws InvalidToken, ServiceFailure,
            NotAuthorized, IdentifierNotUnique, UnsupportedType,
            InsufficientResources, InvalidSystemMetadata, NotImplemented,
            InvalidRequest;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.registerSystemMetadata
     */
    @Deprecated
     public Identifier registerSystemMetadata(Session session, Identifier pid,
        SystemMetadata sysmeta) throws NotImplemented, NotAuthorized,
        ServiceFailure, InvalidRequest, InvalidSystemMetadata, InvalidToken;
     
     /**
      * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.setObsoletedBy
      */
     @Deprecated
     public boolean setObsoletedBy(Session session, Identifier pid,
   			Identifier obsoletedByPid, long serialVersion)
   			throws NotImplemented, NotFound, NotAuthorized, ServiceFailure,
   			InvalidRequest, InvalidToken, VersionMismatch;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.delete
     */
     @Deprecated
    public Identifier delete(Session session, Identifier pid)
    throws InvalidToken, ServiceFailure, InvalidRequest, NotAuthorized, NotFound, NotImplemented;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.archive
     */
     @Deprecated
    public Identifier archive(Session session, Identifier pid)
    throws InvalidToken, ServiceFailure, InvalidRequest, NotAuthorized, NotFound, NotImplemented; 
    
    
    
    
}
