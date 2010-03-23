package org.dataone.service.types;

/**
 * The DataONE Type to represent system metadata as an object.
 *
 * @author Matthew Jones
 */
public class SystemMetadata 
{
    private IdentifierType identifier;
    private ObjectFormat format;
    private long size;
    
    // TODO: complete remainder of the sysmeta fields and accessors
    
    // TODO: add serialization and deserialization to XML {and JSON, CSV, ...}
    
    /**
     * @param token the token to set
     */
    public SystemMetadata() {
    }

    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(IdentifierType identifier) {
        this.identifier = identifier;
    }

    /**
     * @return the identifier
     */
    public IdentifierType getIdentifier() {
        return identifier;
    }

    /**
     * @param format the format to set
     */
    public void setFormat(ObjectFormat format) {
        this.format = format;
    }

    /**
     * @return the format
     */
    public ObjectFormat getFormat() {
        return format;
    }

    /**
     * @param size the size to set
     */
    public void setSize(long size) {
        this.size = size;
    }

    /**
     * @return the size
     */
    public long getSize() {
        return size;
    }
}
