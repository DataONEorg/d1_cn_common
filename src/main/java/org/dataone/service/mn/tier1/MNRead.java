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

import java.io.InputStream;
import java.util.Date;

import org.dataone.service.exceptions.InvalidRequest;
import org.dataone.service.exceptions.InvalidToken;
import org.dataone.service.exceptions.NotAuthorized;
import org.dataone.service.exceptions.NotFound;
import org.dataone.service.exceptions.NotImplemented;
import org.dataone.service.exceptions.ServiceFailure;

import org.dataone.service.types.Session;
import org.dataone.service.types.SystemMetadata;
import org.dataone.service.types.DescribeResponse;
import org.dataone.service.types.Identifier;
import org.dataone.service.types.Checksum;
import org.dataone.service.types.ObjectList;
import org.dataone.service.types.ObjectFormat;

//import org.joda.time.DateTime;

/**
 * The DataONE Member Node Tier 1 Read interface.  This defines an
 * implementation interface for Member Nodes that wish to build an
 * implementation that is compliant with the DataONE service definitions.
 *
 * @author Matthew Jones
 */
public interface MNRead {

    /**
     * InputStream is the Java native version of D1's OctetStream
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/MN_APIs.html#MN_read.listObjects
     *
     */
    public InputStream get(Session cert, Identifier pid)
            throws InvalidToken, ServiceFailure, NotAuthorized, NotFound, 
                   NotImplemented, InvalidRequest;


    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/MN_APIs.html#MN_read.getSystemMetadata
     */
    public SystemMetadata getSystemMetadata(Session cert, Identifier pid)
            throws InvalidToken, ServiceFailure, NotAuthorized, NotFound,
            InvalidRequest, NotImplemented;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/MN_APIs.html#MN_read.describe
     */
    public DescribeResponse describe(Session cert, Identifier pid)
            throws InvalidToken, ServiceFailure, NotAuthorized, NotFound,
            NotImplemented, InvalidRequest;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/MN_APIs.html#MN_read.getChecksum
     */
    public Checksum getChecksum(Session cert, Identifier pid, 
            String checksumAlgorithm) throws InvalidToken, ServiceFailure, 
            NotAuthorized, NotFound, InvalidRequest, NotImplemented;

    /** 
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/MN_APIs.html#MN_read.listObjects
     */
    public ObjectList listObjects(Session cert, Date startTime, 
            Date endTime, ObjectFormat objectFormat, Boolean replicaStatus,
            Integer start, Integer count) throws NotAuthorized, InvalidRequest,
            NotImplemented, ServiceFailure, InvalidToken;

    /** TODO: Add Exception class to types package
     *  XXX: BaseException should do, it provides its own serializer -rpw
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/MN_APIs.html#MN_read.synchronizationFailed
     *
    public void synchronizationFailed(Session cert, BaseException message)
            throws NotImplemented, ServiceFailure, NotAuthorized, InvalidRequest;
    */

}
