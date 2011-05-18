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

import org.dataone.service.types.Identifier;

/**
 * The DataONE UnsupportedQueryType exception, raised when a search request is
 * made that is not recognized by the DataONE system.
 *  
 * @author Matthew Jones
 */
public class UnsupportedQueryType extends BaseException {

    /** Fix the errorCode in this exception. */
    private static final int errorCode=400;
    
    public UnsupportedQueryType(String detailCode, String description) {
        super(errorCode, detailCode, description);
    }

    public UnsupportedQueryType(String detailCode, String description, 
            TreeMap<String, String> trace_information) {
        super(errorCode, detailCode, description, trace_information);
    }
    
    public UnsupportedQueryType(String detailCode, String description, 
    		Identifier pid,
            TreeMap<String, String> trace_information) {
        super(errorCode, detailCode, pid, description, trace_information);
    }
}
