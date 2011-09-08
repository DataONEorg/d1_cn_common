
package org.dataone.service.types.v1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** 
 * An ObjectFormatList is the structure returned from 
 the :func:`CNCore.listFormats()` method of the CN REST interface.  It 
 provides a list of named objectformats defined in the DataONE system.  
 Each ObjectFormat returned in the list describes the object format via 
 its  name, and future versions may contain additional structured content 
 from common external typing systems.

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="ObjectFormatList">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns:Slice">
 *       &lt;xs:sequence>
 *         &lt;xs:element type="ns:ObjectFormat" name="objectFormat" minOccurs="1" maxOccurs="unbounded"/>
 *       &lt;/xs:sequence>
 *     &lt;/xs:extension>
 *   &lt;/xs:complexContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class ObjectFormatList extends Slice implements Serializable
{
    private List<ObjectFormat> objectFormatList = new ArrayList<ObjectFormat>();

    /** 
     * Get the list of 'objectFormat' element items.
     * 
     * @return list
     */
    public List<ObjectFormat> getObjectFormatList() {
        return objectFormatList;
    }

    /** 
     * Set the list of 'objectFormat' element items.
     * 
     * @param list
     */
    public void setObjectFormatList(List<ObjectFormat> list) {
        objectFormatList = list;
    }

    /** 
     * Get the number of 'objectFormat' element items.
     * @return count
     */
    public int sizeObjectFormatList() {
        return objectFormatList.size();
    }

    /** 
     * Add a 'objectFormat' element item.
     * @param item
     */
    public void addObjectFormat(ObjectFormat item) {
        objectFormatList.add(item);
    }

    /** 
     * Get 'objectFormat' element item by position.
     * @return item
     * @param index
     */
    public ObjectFormat getObjectFormat(int index) {
        return objectFormatList.get(index);
    }

    /** 
     * Remove all 'objectFormat' element items.
     */
    public void clearObjectFormatList() {
        objectFormatList.clear();
    }
}
