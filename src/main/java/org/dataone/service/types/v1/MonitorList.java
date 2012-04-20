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
 * 
 * $Id$
 */

package org.dataone.service.types.v1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="MonitorList">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:MonitorInfo" name="monitorInfo" minOccurs="0" maxOccurs="unbounded"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class MonitorList implements Serializable
{
    private static final long serialVersionUID = 10000000;
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
