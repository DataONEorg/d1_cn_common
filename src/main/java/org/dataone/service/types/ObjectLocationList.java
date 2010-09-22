/**
 * This work was created by participants in the DataONE project, and is
 * jointly copyrighted by participating institutions in DataONE. For
 * more information on DataONE, see our web site at http://dataone.org.
 *
 *   Copyright ${year}
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dataone.service.types;

import java.util.Set;
import java.util.TreeSet;

/**
 * The DataONE Type to represent a list of locations on which replicas of an 
 * object are stored.
 *
 * @author Matthew Jones
 */
public class ObjectLocationList 
{
    private Set<ObjectLocation> locations;
    
    /**
     * Construct a list using a default empty set of locations.
     */
    public ObjectLocationList() {
        locations = new TreeSet<ObjectLocation>();
    }
    
    /**
     * Construct a list using the provided set of locations.
     * @param locations the Set of locations
     */
    public ObjectLocationList(Set<ObjectLocation> locations) {
        this.locations = locations;
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

    /**
     * Add a new location to the list of object locations.
     * @param l the location to be added.
     */
    public void add(ObjectLocation l) {
        locations.add(l);
    }
}
