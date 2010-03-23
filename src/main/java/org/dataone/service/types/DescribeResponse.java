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
