
package org.dataone.service.types;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://dataone.org/service/types/ObjectList/0.1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="ObjectList">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:ObjectInfo" name="objectInfo" minOccurs="0" maxOccurs="unbounded"/>
 *   &lt;/xs:sequence>
 *   &lt;xs:attribute type="xs:int" use="required" name="count"/>
 *   &lt;xs:attribute type="xs:int" use="required" name="start"/>
 *   &lt;xs:attribute type="xs:int" use="required" name="total"/>
 * &lt;/xs:complexType>
 * </pre>
 */
public class ObjectList
{
    private List<ObjectInfo> objectInfoList = new ArrayList<ObjectInfo>();
    private int count;
    private int start;
    private int total;

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

    /** 
     * Get the 'count' attribute value.
     * 
     * @return value
     */
    public int getCount() {
        return count;
    }

    /** 
     * Set the 'count' attribute value.
     * 
     * @param count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /** 
     * Get the 'start' attribute value.
     * 
     * @return value
     */
    public int getStart() {
        return start;
    }

    /** 
     * Set the 'start' attribute value.
     * 
     * @param start
     */
    public void setStart(int start) {
        this.start = start;
    }

    /** 
     * Get the 'total' attribute value.
     * 
     * @return value
     */
    public int getTotal() {
        return total;
    }

    /** 
     * Set the 'total' attribute value.
     * 
     * @param total
     */
    public void setTotal(int total) {
        this.total = total;
    }
}
