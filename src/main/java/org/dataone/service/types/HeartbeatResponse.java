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
