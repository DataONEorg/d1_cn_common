package org.dataone.service.mn;

import org.dataone.service.exceptions.NotImplemented;
import org.dataone.service.types.HeartbeatResponse;

/**
 * The DataONE MemberNode Health programmatic interface.  This defines an
 * implementation interface for Member Nodes that wish to build an
 * implementation that is compliant with the DataONE service definitions.
 *
 * @author Matthew Jones
 */
public interface MemberNodeHealth 
{    
    public HeartbeatResponse heartbeat() throws NotImplemented;
    
    // Unclear whether these other methods are to be implemented, and if so,
    // how they differ from one another.
    //public PingResponse ping();
    //public StatusResponse getStatus(AuthToken token);
    //public void sohQuery(AuthToken token, String service);
}
