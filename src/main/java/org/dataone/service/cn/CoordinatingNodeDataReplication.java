/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dataone.service.cn;

import org.dataone.service.exceptions.InvalidRequest;
import org.dataone.service.exceptions.InvalidToken;
import org.dataone.service.exceptions.NotAuthorized;
import org.dataone.service.exceptions.NotFound;
import org.dataone.service.exceptions.NotImplemented;
import org.dataone.service.exceptions.ServiceFailure;
import org.dataone.service.types.AuthToken;
import org.dataone.service.types.Identifier;
import org.dataone.service.types.ReplicationStatus;

/**
 *
 * @author waltz
 * @deprecated
 */
public interface CoordinatingNodeDataReplication {
    public boolean setReplicationStatus(AuthToken token, Identifier pid, ReplicationStatus status)
            throws ServiceFailure, NotImplemented, InvalidToken, NotAuthorized, InvalidRequest, NotFound;
}
