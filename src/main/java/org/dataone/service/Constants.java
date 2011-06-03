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
 * 
 * $Id$
 */
package org.dataone.service;

/**
 * @author berkley
 * A class to contain constants used in clients 
 */
public class Constants {

    /** HTTP Verb GET */
    public static final String GET = "GET";
    /** HTTP Verb POST */
    public static final String POST = "POST";
    /** HTTP Verb PUT */
    public static final String PUT = "PUT";
    /** HTTP Verb HEAD*/
    public static final String HEAD = "HEAD";
    /** HTTP Verb DELETE */
    public static final String DELETE = "DELETE";
    /** API OBJECTS Resource which handles with document operations */
    public static final String RESOURCE_OBJECTS = "object";
    /** API META Resource which handles SystemMetadata operations */
    public static final String RESOURCE_META = "meta";
    /** API SESSION Resource which handles with user session operations */
    public static final String RESOURCE_SESSION = "session";
    /** API FORMATS Resource which handles with object formats operations */
    public static final String RESOURCE_FORMATS = "formats";
    /** API RESOLVE Resource which handles resolve operations */
    public static final String RESOURCE_RESOLVE = "resolve";
    /** API NODE Resource which handles node operations */
    public static final String RESOURCE_NODE = "node";
    /** API IDENTIFIER Resource which controls object identifier operations */
    public static final String RESOURCE_IDENTIFIER = "identifier";
    /** API LOG controls logging events */
    public static final String RESOURCE_LOG = "log";
    /** API checksum resource*/
    public static final String RESOURCE_CHECKSUM = "checksum";
    /** Temporary file directory */
    public static final String TEMP_DIR = "/tmp";
}
