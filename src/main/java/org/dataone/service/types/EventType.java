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
