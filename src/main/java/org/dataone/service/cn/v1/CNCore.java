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

import org.dataone.service.types.v1.Identifier;
import org.dataone.service.types.v1.Log;
import org.dataone.service.types.v1.ObjectFormat;
import org.dataone.service.types.v1.ObjectFormatIdentifier;
import org.dataone.service.types.v1.ObjectFormatList;
import org.dataone.service.types.v1.Session;
import org.dataone.service.types.v1.Event;
import org.dataone.service.types.v1.NodeList;
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
	/**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.listFormats
     */
		public ObjectFormatList listFormats()
        throws InvalidRequest, ServiceFailure, NotFound, InsufficientResources,
        NotImplemented;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.getFormat
     */
    public ObjectFormat getFormat(ObjectFormatIdentifier fmtid)
        throws InvalidRequest, ServiceFailure, NotFound, InsufficientResources,
        NotImplemented;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.getLogRecords
     */
    public Log getLogRecords(Session session, Date fromDate, Date toDate,
        Event event, Integer start, Integer count) throws InvalidToken, InvalidRequest, ServiceFailure,
        NotAuthorized, NotImplemented;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.listNodes
     */
    public NodeList listNodes() throws NotImplemented, ServiceFailure;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.reserveIdentifier
     */
    public Identifier reserveIdentifier(Session session, Identifier pid, String scope, String format)
    	throws InvalidToken, ServiceFailure,
            NotAuthorized, IdentifierNotUnique, NotImplemented, InvalidRequest;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.hasReservation
     */
    public boolean hasReservation(Session session, Identifier pid)
    	throws InvalidToken, ServiceFailure,  NotFound,
            NotAuthorized, IdentifierNotUnique, NotImplemented, InvalidRequest;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.create
     */
    public Identifier create(Session session, Identifier pid, InputStream object,
            SystemMetadata sysmeta) throws InvalidToken, ServiceFailure,
            NotAuthorized, IdentifierNotUnique, UnsupportedType,
            InsufficientResources, InvalidSystemMetadata, NotImplemented,
            InvalidRequest;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.registerSystemMetadata
     */
     public boolean registerSystemMetadata(Session session, Identifier pid,
        SystemMetadata sysmeta) throws NotImplemented, NotAuthorized,
        ServiceFailure, InvalidRequest, InvalidSystemMetadata;
}
