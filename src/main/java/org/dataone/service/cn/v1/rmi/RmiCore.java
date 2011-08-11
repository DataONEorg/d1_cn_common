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

package org.dataone.service.cn.v1.rmi;

import java.io.InputStream;

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
import org.dataone.service.types.v1.ObjectFormat;
import org.dataone.service.types.v1.ObjectFormatIdentifier;
import org.dataone.service.types.v1.ObjectFormatList;
import org.dataone.service.types.v1.SystemMetadata;

/**
 * The DataONE CoordinatingNode Tier1 Core interface.  This defines an
 * implementation interface for Coordinating Nodes that wish to build an
 * implementation that is compliant with the DataONE service definitions.
 *
 * @author Matthew Jones
 */
public interface RmiCore
{
	/**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.listFormats
     */
	public ObjectFormatList remoteListFormats()
        throws InvalidRequest, ServiceFailure, NotFound, InsufficientResources,
        NotImplemented;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.getFormat
     */
    public ObjectFormat remoteGetFormat(ObjectFormatIdentifier fmtid)
        throws InvalidRequest, ServiceFailure, NotFound, InsufficientResources,
        NotImplemented;


    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.create
     */
    public Identifier remoteCreate(Identifier pid, InputStream object, SystemMetadata sysmeta) throws InvalidToken, ServiceFailure,
            NotAuthorized, IdentifierNotUnique, UnsupportedType,
            InsufficientResources, InvalidSystemMetadata, NotImplemented,
            InvalidRequest;

    /**
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.registerSystemMetadata
     */
     public boolean remoteRegisterSystemMetadata(Identifier pid, SystemMetadata sysmeta) throws NotImplemented, NotAuthorized,
        ServiceFailure, InvalidRequest, InvalidSystemMetadata;
     
     /**
      * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/CN_APIs.html#CNCore.updateSystemMetadata
      */
      public boolean remoteUpdateSystemMetadata(Identifier pid, SystemMetadata sysmeta) throws NotImplemented, NotAuthorized,
         ServiceFailure, InvalidRequest, InvalidSystemMetadata, NotFound;
}
