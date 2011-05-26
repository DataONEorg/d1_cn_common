/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dataone.service.mn;

import org.dataone.service.exceptions.AuthenticationTimeout;
import org.dataone.service.exceptions.InvalidCredentials;
import org.dataone.service.exceptions.InvalidRequest;
import org.dataone.service.exceptions.InvalidToken;
import org.dataone.service.exceptions.NotImplemented;
import org.dataone.service.exceptions.ServiceFailure;
import org.dataone.service.types.AuthToken;
import org.dataone.service.types.AuthType;

/**
 *
 * @author waltz
 * @deprecated
 */
public interface MemberNodeAuthentication {

    public AuthToken login(String user, String password)
            throws InvalidCredentials, AuthenticationTimeout, NotImplemented, InvalidRequest, ServiceFailure;

    public AuthToken login(String user, String password, AuthType type)
            throws InvalidCredentials, AuthenticationTimeout, NotImplemented, InvalidRequest, ServiceFailure;

    public void logout(AuthToken token) throws NotImplemented, InvalidToken, InvalidRequest, ServiceFailure;
}
