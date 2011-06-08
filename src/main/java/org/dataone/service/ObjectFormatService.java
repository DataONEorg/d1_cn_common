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

package org.dataone.service;

import org.dataone.service.exceptions.InsufficientResources;
import org.dataone.service.exceptions.InvalidRequest;
import org.dataone.service.exceptions.NotFound;
import org.dataone.service.exceptions.NotImplemented;
import org.dataone.service.exceptions.ServiceFailure;
import org.dataone.service.types.ObjectFormat;
import org.dataone.service.types.ObjectFormatIdentifier;
import org.dataone.service.types.ObjectFormatList;

/**
 * The DataONE ObjectFormatService interface defines methods
 * for retrieving ObjectFormat information
 *
 * @author leinfelder
 */
public interface ObjectFormatService 
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
    
    
}
