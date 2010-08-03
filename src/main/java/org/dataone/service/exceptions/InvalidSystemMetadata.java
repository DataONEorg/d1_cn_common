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

package org.dataone.service.exceptions;

import java.util.TreeMap;

/**
 * The DataONE InvalidSystemMetadata exception, raised when a request 
 * is issued and the supplied system metadata is invalid. This could be 
 * because some required field is not set, the metadata document is malformed, 
 * or the value of some field is not valid.
 * 
 * @author Matthew Jones
 */
public class InvalidSystemMetadata extends BaseException {

    /** Fix the errorCode in this exception. */
    private static final int errorCode=400;
    
    public InvalidSystemMetadata(String detailCode, String description) {
        super(errorCode, detailCode, description);
    }

    public InvalidSystemMetadata(String detailCode, String description, 
            TreeMap<String, String> trace_information) {
        super(errorCode, detailCode, description);
    }
}
