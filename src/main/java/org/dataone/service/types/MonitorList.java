
package org.dataone.service.types;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/0.6.2" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="MonitorList">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:MonitorInfo" name="monitorInfo" minOccurs="0" maxOccurs="unbounded"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class MonitorList
{
    private List<MonitorInfo> monitorInfoList = new ArrayList<MonitorInfo>();

    /** 
     * Get the list of 'monitorInfo' element items.
     * 
     * @return list
     */
    public List<MonitorInfo> getMonitorInfoList() {
        return monitorInfoList;
    }

    /** 
     * Set the list of 'monitorInfo' element items.
     * 
     * @param list
     */
    public void setMonitorInfoList(List<MonitorInfo> list) {
        monitorInfoList = list;
    }

    /** 
     * Get the number of 'monitorInfo' element items.
     * @return count
     */
    public int sizeMonitorInfoList() {
        return monitorInfoList.size();
    }

    /** 
     * Add a 'monitorInfo' element item.
     * @param item
     */
    public void addMonitorInfo(MonitorInfo item) {
        monitorInfoList.add(item);
    }

    /** 
     * Get 'monitorInfo' element item by position.
     * @return item
     * @param index
     */
    public MonitorInfo getMonitorInfo(int index) {
        return monitorInfoList.get(index);
    }

    /** 
     * Remove all 'monitorInfo' element items.
     */
    public void clearMonitorInfoList() {
        monitorInfoList.clear();
    }
}
