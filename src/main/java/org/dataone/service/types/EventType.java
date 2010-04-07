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
 * The DataONE Type to represent a checksum and its algorithm.
 *
 * @author Matthew Jones
 */
public class EventType 
{
    public static final int CREATE = 0;
    public static final int READ = 1;
    public static final int UPDATE = 2;
    public static final int DELETE = 3;
    public static final int REPLICATE = 4;


    private int eventType;
    
    /**
     * Construct a new Checksum with the given algorithm and value.
     * @param algorithm an integer code representing the algorithm used
     * @param value the String vlaue resulting from the checksum algorithm
     */
    public EventType(int eventType) {
        this.eventType = eventType;
    }

    /**
     * @return the eventType
     */
    public int getEventType() {
        return eventType;
    }
}
