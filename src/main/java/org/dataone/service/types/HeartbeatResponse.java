/**
 * This work was created by participants in the DataONE project, and is jointly
 * copyrighted by participating institutions in DataONE. For more information on
 * DataONE, see our web site at http://dataone.org.
 *
 *   Copyright ${year}
 *
 * This work is Licensed under the Apache License, Version 2.0 (the "License");
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

package org.dataone.service.types;

import java.util.Date;

/**
 * The DataONE Type to represent the metadata returned from a 'heartbeat' request.
 *
 * @author Matthew Jones
 */
public class HeartbeatResponse 
{
    private String apiVersion;
    private boolean operational;
    private Date heartbeatTime;
    
    /**
     * Construct a repsonse to the heartbeat call, providing required fields.
     * @param apiVersion the version of the API supported by this node
     * @param operational a boolean flag indicating if the service is available
     * @param heartbeatTime the time at which the heartbeat was checked
     */
    public HeartbeatResponse(String apiVersion, boolean operational,
            Date heartbeatTime) {
        super();
        this.apiVersion = apiVersion;
        this.operational = operational;
        this.heartbeatTime = heartbeatTime;
    }
    
    /**
     * @return the apiVersion
     */
    public String getApiVersion() {
        return apiVersion;
    }
    /**
     * @param apiVersion the apiVersion to set
     */
    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }
    /**
     * @return the operational
     */
    public boolean isOperational() {
        return operational;
    }
    /**
     * @param operational the operational to set
     */
    public void setOperational(boolean operational) {
        this.operational = operational;
    }
    /**
     * @return the heartbeatTime
     */
    public Date getHeartbeatTime() {
        return heartbeatTime;
    }
    /**
     * @param heartbeatTime the heartbeatTime to set
     */
    public void setHeartbeatTime(Date heartbeatTime) {
        this.heartbeatTime = heartbeatTime;
    }
}
