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
import org.dataone.service.types.AccessPolicy;
import org.dataone.service.types.AuthToken;
import org.dataone.service.types.Event;
import org.dataone.service.types.Identifier;

/**
 *
 * @author waltz
 * @deprecated
 */
public interface CoordinatingNodeAuthorization {


    public boolean isAuthorized(AuthToken token, Identifier pid, Event operation)
        throws ServiceFailure, InvalidToken, NotFound, NotAuthorized, NotImplemented, InvalidRequest;

    public boolean setAccess(AuthToken token, Identifier pid, AccessPolicy accessPolicy)
            throws InvalidToken, ServiceFailure, NotFound, NotAuthorized, NotImplemented, InvalidRequest;

    // this is the signature that setAcess has now, should be deprecated in future
    public boolean setAccess(AuthToken token, Identifier pid, String principal, String permission,
            String permissionType, String permissionOrder)
            throws InvalidToken, ServiceFailure, NotFound, NotAuthorized, NotImplemented, InvalidRequest;
}
