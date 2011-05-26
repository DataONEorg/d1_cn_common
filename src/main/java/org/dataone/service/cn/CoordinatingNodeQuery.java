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

import java.util.Date;
import java.util.Map;


import org.dataone.service.exceptions.InvalidRequest;
import org.dataone.service.exceptions.NotAuthorized;
import org.dataone.service.types.AuthToken;
import org.dataone.service.types.ObjectList;
import org.dataone.service.types.Log;

/**
 * The DataONE CoordinatingNode CRUD programmatic interface.  This defines an
 * implementation interface for Coordinating Nodes that wish to build an
 * implementation that is compliant with the DataONE service definitions.
 *
 * @author Matthew Jones
 * @deprecated
 */
public interface CoordinatingNodeQuery 
{
    // until we define a dataone query type, (some kind of triplestores tied with
    // nested boolean logic structure?) we should just pass a Map
    // since it is a lot easier to manipulate and comforms to
    // the parameter map that will contain the query fields from the Request
    public ObjectList search(AuthToken token, Map query)
        throws NotAuthorized, InvalidRequest;
    public Log getLogRecords(AuthToken token, 
            Date fromDate, Date toDate)
        throws NotAuthorized, InvalidRequest;
}
