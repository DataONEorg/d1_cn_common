package org.dataone.service.types;


/**
 * The DataONE Type to represent the format of an object.
 *
 * @author Matthew Jones
 */
public class ObjectFormat 
{
    private String format;  // TODO: Constrain to fixed list of types

    /**
     * @param format the format to set
     */
    public ObjectFormat(String format) {
        this.format = format;
    }

    /**
     * @return the format
     */
    public String getFormat() {
        return format;
    }
}
