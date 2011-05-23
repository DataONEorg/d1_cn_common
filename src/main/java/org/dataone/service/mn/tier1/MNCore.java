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

package org.dataone.service.mn.tier1;

import org.dataone.service.exceptions.InvalidRequest;
import org.dataone.service.exceptions.InvalidToken;
import org.dataone.service.exceptions.NotAuthorized;
import org.dataone.service.exceptions.NotFound;
import org.dataone.service.exceptions.NotImplemented;
import org.dataone.service.exceptions.ServiceFailure;
import org.dataone.service.exceptions.InsufficientResources;
import org.dataone.service.exceptions.UnsupportedType;

import org.dataone.service.types.Log;
import org.dataone.service.types.Session;
import org.dataone.service.types.Event;
import org.dataone.service.types.MonitorList;
import org.dataone.service.types.ObjectFormat;
import org.dataone.service.types.NodeList;
import org.dataone.service.types.Subject;
//import org.dataone.service.types.ObjectStatistics;
//import org.dataone.service.types.StatusResponse;

import org.joda.time.DateTime;

/**
 * The DataONE Member Node Tier 1 Core interface.  This defines an
 * implementation interface for Member Nodes that wish to build an
 * implementation that is compliant with the DataONE service definitions.
 *
 * @author Matthew Jones
 */
public interface MNCore {

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/MN_APIs.html#MN_core.ping
     */
    public boolean ping() throws NotImplemented, ServiceFailure, NotAuthorized, 
           InvalidRequest, InsufficientResources, UnsupportedType;

    /** 
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/MN_APIs.html#MN_core.getLogRecords
     */
    public Log getLogRecords(Session cert, DateTime fromDate, DateTime toDate, 
            Event event, Integer start, Integer count) throws InvalidToken, 
            ServiceFailure, NotAuthorized, InvalidRequest, NotImplemented;

    /** TODO: Add ObjectStatistics class
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/MN_APIs.html#MN_core.getObjectStatistics
     *
    public ObjectStatistics getObjectStatistics(String format, String pid) 
            throws NotImplemented, ServiceFailure, NotAuthorized, 
            InvalidRequest, InsufficientResources, UnsupportedType;
    */

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/MN_APIs.html#MN_core.getOperationStatistics
     */
    public MonitorList getOperationStatistics(Session cert, Integer period, 
            Subject requestor, Event event, ObjectFormat format) throws 
            NotImplemented, ServiceFailure, NotAuthorized, InvalidRequest, 
            InsufficientResources, UnsupportedType;

    /** TODO: Add StatusResponse class
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/MN_APIs.html#MN_core.getStatus
     *
    public StatusResponse getStatus() throws NotImplemented, ServiceFailure, 
            NotAuthorized, InvalidRequest, InsufficientResources, 
            UnsupportedType;
    */

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/MN_APIs.html#MN_core.getCapabilities
     */
    public NodeList getCapabilities() throws NotImplemented, NotAuthorized, 
            ServiceFailure, InvalidRequest; 
}
