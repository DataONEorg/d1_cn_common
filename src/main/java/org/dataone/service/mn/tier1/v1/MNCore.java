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

package org.dataone.service.mn.tier1.v1;

import java.util.Date;

import org.dataone.service.exceptions.InvalidRequest;
import org.dataone.service.exceptions.InvalidToken;
import org.dataone.service.exceptions.NotAuthorized;
import org.dataone.service.exceptions.NotImplemented;
import org.dataone.service.exceptions.ServiceFailure;
import org.dataone.service.exceptions.InsufficientResources;
import org.dataone.service.exceptions.UnsupportedType;

import org.dataone.service.types.v1.Log;
import org.dataone.service.types.v1.Node;
import org.dataone.service.types.v1.ObjectFormatIdentifier;
import org.dataone.service.types.v1.Session;
import org.dataone.service.types.v1.Event;
import org.dataone.service.types.v1.MonitorList;
import org.dataone.service.types.v1.Subject;



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
    public boolean ping() 
    throws InvalidRequest, NotAuthorized, NotImplemented, ServiceFailure,
           InsufficientResources, UnsupportedType;

    /** 
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/MN_APIs.html#MN_core.getLogRecords
     */
    public Log getLogRecords(Session cert, Date fromDate, Date toDate, 
           Event event, Integer start, Integer count) 
    throws InvalidRequest, InvalidToken, NotAuthorized, NotImplemented, ServiceFailure;

    /** TODO: Add ObjectStatistics class
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/MN_APIs.html#MN_core.getObjectStatistics
     *
    public ObjectStatistics getObjectStatistics(String format, String pid) 
    throws InvalidRequest, NotAuthorized, NotImplemented, ServiceFailure,
           InsufficientResources, UnsupportedType;
    */

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/MN_APIs.html#MN_core.getOperationStatistics
     */
    public MonitorList getOperationStatistics(Session cert, Date startTime,
    		Date endTime, Subject requestor, Event event, ObjectFormatIdentifier formatId) 
    throws InvalidRequest, InvalidToken, NotAuthorized, NotImplemented, ServiceFailure,
    	   InsufficientResources, UnsupportedType;


    /** TODO: Add StatusResponse class
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/MN_APIs.html#MN_core.getStatus
     *
    public StatusResponse getStatus() 
    throws InvalidRequest, NotAuthorized, NotImplemented, ServiceFailure,
    	   InsufficientResources, UnsupportedType;

    */

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/MN_APIs.html#MN_core.getCapabilities
     */
    public Node getCapabilities() 
    throws InvalidRequest, NotAuthorized, NotImplemented, ServiceFailure;

}
