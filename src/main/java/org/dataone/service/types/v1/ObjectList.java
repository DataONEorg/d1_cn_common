
package org.dataone.service.types.v1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** 
 * A list of object locations (nodes) from which the
 object can be retrieved. 
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="ObjectList">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns:Slice">
 *       &lt;xs:sequence>
 *         &lt;xs:element type="ns:ObjectInfo" name="objectInfo" minOccurs="0" maxOccurs="unbounded"/>
 *       &lt;/xs:sequence>
 *     &lt;/xs:extension>
 *   &lt;/xs:complexContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class ObjectList extends Slice implements Serializable
{
    private static final long serialVersionUID = 10000000;
    private List<ObjectInfo> objectInfoList = new ArrayList<ObjectInfo>();

    /** 
     * Get the list of 'objectInfo' element items.
     * 
     * @return list
     */
    public List<ObjectInfo> getObjectInfoList() {
        return objectInfoList;
    }

    /** 
     * Set the list of 'objectInfo' element items.
     * 
     * @param list
     */
    public void setObjectInfoList(List<ObjectInfo> list) {
        objectInfoList = list;
    }

    /** 
     * Get the number of 'objectInfo' element items.
     * @return count
     */
    public int sizeObjectInfoList() {
        if (objectInfoList == null) {
            objectInfoList = new ArrayList<ObjectInfo>();
        }
        return objectInfoList.size();
    }

    /** 
     * Add a 'objectInfo' element item.
     * @param item
     */
    public void addObjectInfo(ObjectInfo item) {
        if (objectInfoList == null) {
            objectInfoList = new ArrayList<ObjectInfo>();
        }
        objectInfoList.add(item);
    }

    /** 
     * Get 'objectInfo' element item by position.
     * @return item
     * @param index
     */
    public ObjectInfo getObjectInfo(int index) {
        if (objectInfoList == null) {
            objectInfoList = new ArrayList<ObjectInfo>();
        }
        return objectInfoList.get(index);
    }

    /** 
     * Remove all 'objectInfo' element items.
     */
    public void clearObjectInfoList() {
        if (objectInfoList == null) {
            objectInfoList = new ArrayList<ObjectInfo>();
        }
        objectInfoList.clear();
    }
}
