
package org.dataone.service.types;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/0.6.2" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="ObjectList">
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
public class ObjectList extends Slice
{
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
        return objectInfoList.size();
    }

    /** 
     * Add a 'objectInfo' element item.
     * @param item
     */
    public void addObjectInfo(ObjectInfo item) {
        objectInfoList.add(item);
    }

    /** 
     * Get 'objectInfo' element item by position.
     * @return item
     * @param index
     */
    public ObjectInfo getObjectInfo(int index) {
        return objectInfoList.get(index);
    }

    /** 
     * Remove all 'objectInfo' element items.
     */
    public void clearObjectInfoList() {
        objectInfoList.clear();
    }
}
