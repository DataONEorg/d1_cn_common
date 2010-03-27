package org.dataone.service.types;

import java.util.Set;

/**
 * The DataONE Type to represent a list of locations on which replicas of an 
 * object are stored.
 *
 * @author Matthew Jones
 */
public class ObjectLocationList 
{
    private Set<ObjectLocation> locations;
    
    public ObjectLocationList(Set<ObjectLocation> locations) {
    }

    /**
     * @return the locations
     */
    public Set<ObjectLocation> getLocations() {
        return locations;
    }

    /**
     * @param locations the locations to set
     */
    public void setLocations(Set<ObjectLocation> locations) {
        this.locations = locations;
    }

}
