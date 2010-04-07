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


/**
 * The DataONE Type to represent a principal, which is a user, group, or role.
 *
 * @author Matthew Jones
 */
public class PrincipalType 
{
    private String dn;
    
    /**
     * Construct a new principal
     * @param dn the distinguished name of the principal
     */
    public PrincipalType(String dn) {
        this.dn = dn;
    }
    
    /**
     * @param dn the dn to set
     */
    public void setDn(String dn) {
        this.dn = dn;
    }

    /**
     * @return the dn
     */
    public String getDn() {
        return dn;
    }

}
