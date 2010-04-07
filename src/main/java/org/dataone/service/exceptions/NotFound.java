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

package org.dataone.service.exceptions;

import java.util.TreeMap;

/**
 * The DataONE NotFound exception, raised when an object is not present 
 * on the node where the exception was raised.
 *  
 * @author Matthew Jones
 */
public class NotFound extends BaseException {

    /** Fix the errorCode in this exception. */
    private static final int errorCode=404;
    
    public NotFound(int detailCode, String description) {
        super(errorCode, detailCode, description);
    }

    public NotFound(int detailCode, String description, 
            TreeMap<String, String> trace_information) {
        super(errorCode, detailCode, description);
    }
}
