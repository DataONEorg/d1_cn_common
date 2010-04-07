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

import java.util.Set;

/**
 * The DataONE Type to represent a set of logging records from CRUD operations.
 *
 * @author Matthew Jones
 */
public class LogRecordSet 
{
    private Set<LogRecord> records;
    
    public LogRecordSet(Set<LogRecord> logRecords) {
    }

    /**
     * @param records the records to set
     */
    public void setRecords(Set<LogRecord> records) {
        this.records = records;
    }

    /**
     * @return the records
     */
    public Set<LogRecord> getRecords() {
        return records;
    }
}
