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

package org.dataone.service.cn;

import org.dataone.service.exceptions.AuthenticationTimeout;
import org.dataone.service.exceptions.IdentifierNotUnique;
import org.dataone.service.exceptions.InvalidCredentials;
import org.dataone.service.exceptions.InvalidToken;
import org.dataone.service.exceptions.NotAuthorized;
import org.dataone.service.exceptions.NotFound;
import org.dataone.service.types.AccessRule;
import org.dataone.service.types.AuthToken;
import org.dataone.service.types.Identifier;
import org.dataone.service.types.Principal;

/**
 * The DataONE Coordinating Node Authorization programmatic interface.  This defines an
 * implementation interface for Coordinating Nodes that wish to build an
 * implementation that is compliant with the DataONE service definitions.
 *
 * @author Matthew Jones
 */
public interface CoordinatingNodeAuthorization 
{
    public AuthToken login(String user, String password)
        throws InvalidCredentials, AuthenticationTimeout;
    
    //public void logout(AuthToken token) 
    //    throws InvalidCredentials, AuthenticationTimeout;
    
    public boolean setAccess(AuthToken token, Identifier guid, AccessRule accessLevel)
        throws NotFound, NotAuthorized;
    
    public Identifier setOwner(AuthToken token, Identifier guid, Principal userId)
        throws InvalidToken, NotAuthorized, NotFound;
    
    public Principal newAccount(String username, String password)
        throws IdentifierNotUnique, InvalidCredentials;
    
    public boolean verify(AuthToken token) throws NotAuthorized;
    
    public boolean isAuthorized(AuthToken token, Identifier guid, String operation)
        throws InvalidToken, NotFound, NotAuthorized;
}
