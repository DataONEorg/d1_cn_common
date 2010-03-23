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
