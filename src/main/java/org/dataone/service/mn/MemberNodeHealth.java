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

package org.dataone.service.mn;

import java.util.*;

import org.dataone.service.types.Identifier;
import org.dataone.service.types.ObjectFormat;
import org.dataone.service.types.ObjectInfo;


/**
 * The DataONE MemberNode Health programmatic interface.  This defines an
 * implementation interface for Member Nodes that wish to build an
 * implementation that is compliant with the DataONE service definitions.
 *
 * @author Matthew Jones
 * @deprecated
 */
public interface MemberNodeHealth 
{   
    /**
     * Low level are you alive operation. Response is simple ACK, but may be 
     * reasonable to overload with a couple of flags that could indicate 
     * availability of new data or change in capabilities.
     * 
     * The Member Node should perform some minimal internal functionality 
     * testing before answering. However, ping checks will be frequent 
     * (every few minutes) so the internal functionality test should 
     * not be high impact.
     * 
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/REST_interface.html#get-monitor-object
     */
    public void ping();
    
    /**
     * This function is similar to MN_health.ping() but returns a more complete 
     * status which may include information such as planned service outages.
     * 
     * This method provide a mechanism for notification to Coordinating Nodes at a 
     * higher frequency than through setting properties in the node registry information.
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/REST_interface.html#get-monitor-object
     */
    public void getStatus();
    
    /**
     * Called by the monitoring system to find how many objects are stored on 
     * the MN, cumulative or for each day in a given range.
     * 
     * @param reportDaily   (optional) Flag that causes the number of objects 
     *      that were created to be reported for each day in which one or 
     *      more objects were created. Without this flag the total number of 
     *      objects is reported.
     * @param guid  (optional) (wildcards supported) Filter on identifier.
     * @param url   (optional) (wildcards supported) Filter on URL. The URL 
     *      points to the object bytes when GMN is used as a DataONE adapter 
     *      for an existing repository.
     * @param format    (optional) (wildcards supported) Filter on objectFormat.
     * @param time  (optional) (range operators supported) Filter on created 
     *      date-time ranges. Date-times must be ISO8601 compatible.
     *      
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/REST_interface.html#get-monitor-object
     */
    public void getObjectStatistics(boolean reportDaily, 
            Identifier guid, String url, ObjectFormat format, Date time);
    
    /**
     * Returns the number of object related events that have occurred.
     * 
     * @param reportDaily   (optional) Flag that causes the number of object 
     *      related events to be reported for each day in which one or more 
     *      events occurred. Without this flag, the number of events is cumulative.
     * @param pid   (optional) (wildcards supported) Filter on identifier.
     * @param format   (optional) (wildcards supported) Filter on objectFormat
     * @param createDate    (optional) (range operators supported) Filter on 
     *      create date-time ranges. Date-times must be ISO8601 compatible.
     * @param eventTime     (optional) (range operators supported) Filter on 
     *      event date-time. Date-times must be ISO8601 compatible.
     * @param ipAddress     (optional) (wildcards supported) Filter on IP address.
     * @param event     (optional) (wildcards supported) Filter on operation type.
     * 
     * @see http://mule1.dataone.org/ArchitectureDocs-current/apis/REST_interface.html#get-monitor-object
     */
    public void getOperationStatistics(boolean reportDaily, Identifier pid, 
            ObjectFormat format, Date createDate, Date eventTime, String ipAddress, String event);
}
