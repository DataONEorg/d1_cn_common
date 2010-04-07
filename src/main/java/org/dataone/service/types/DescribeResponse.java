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
 * The DataONE Type to represent the metadata returned from a 'describe' request.
 *
 * @author Matthew Jones
 */
public class DescribeResponse 
{
    private ObjectFormat format;
    private long size;
    private Date last_modified;
    
    /**
     * @param format the format to set
     */
    public DescribeResponse(ObjectFormat format, long size, Date last_modified) {
        this.format = format;
        this.size = size;
        this.last_modified = last_modified;
    }
    
    /**
     * @return the format
     */
    public ObjectFormat getFormat() {
        return format;
    }
    
    /**
     * @return the size
     */
    public long getSize() {
        return size;
    }

    /**
     * @return the last_modified
     */
    public Date getLast_modified() {
        return last_modified;
    }
}
