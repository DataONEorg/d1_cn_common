
package org.dataone.service.types;

import java.util.ArrayList;
import java.util.List;

/** 
 * An ObjectLocationList is the structure returned
 from the resolve() method of the CN REST interface. It provides a
 list of locations from which the specified object can be retrieved.

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/0.6.2" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="ObjectLocationList">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:Identifier" name="identifier" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="ns:ObjectLocation" name="objectLocation" minOccurs="0" maxOccurs="unbounded"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class ObjectLocationList
{
    private Identifier identifier;
    private List<ObjectLocation> objectLocationList = new ArrayList<ObjectLocation>();

    /** 
     * Get the 'identifier' element value. The identifier of the object being resolved.
                      
     * 
     * @return value
     */
    public Identifier getIdentifier() {
        return identifier;
    }

    /** 
     * Set the 'identifier' element value. The identifier of the object being resolved.
                      
     * 
     * @param identifier
     */
    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    /** 
     * Get the list of 'objectLocation' element items. List of nodes from which the object can be
                          retrieved
     * 
     * @return list
     */
    public List<ObjectLocation> getObjectLocationList() {
        return objectLocationList;
    }

    /** 
     * Set the list of 'objectLocation' element items. List of nodes from which the object can be
                          retrieved
     * 
     * @param list
     */
    public void setObjectLocationList(List<ObjectLocation> list) {
        objectLocationList = list;
    }

    /** 
     * Get the number of 'objectLocation' element items.
     * @return count
     */
    public int sizeObjectLocationList() {
        return objectLocationList.size();
    }

    /** 
     * Add a 'objectLocation' element item.
     * @param item
     */
    public void addObjectLocation(ObjectLocation item) {
        objectLocationList.add(item);
    }

    /** 
     * Get 'objectLocation' element item by position.
     * @return item
     * @param index
     */
    public ObjectLocation getObjectLocation(int index) {
        return objectLocationList.get(index);
    }

    /** 
     * Remove all 'objectLocation' element items.
     */
    public void clearObjectLocationList() {
        objectLocationList.clear();
    }
}
