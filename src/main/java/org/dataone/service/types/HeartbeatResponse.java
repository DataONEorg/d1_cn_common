/**
 * Copyright 2010 Regents of the University of California and the
 *                National Center for Ecological Analysis and Synthesis
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
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
