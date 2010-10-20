
package org.dataone.service.types;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://dataone.org/service/types/NodeList/0.5" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Services">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:Service" name="service" minOccurs="1" maxOccurs="unbounded"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class Services
{
    private List<Service> serviceList = new ArrayList<Service>();

    /** 
     * Get the list of 'service' element items.
     * 
     * @return list
     */
    public List<Service> getServices() {
        return serviceList;
    }

    /** 
     * Set the list of 'service' element items.
     * 
     * @param list
     */
    public void setServices(List<Service> list) {
        serviceList = list;
    }

    /** 
     * Get the number of 'service' element items.
     * @return count
     */
    public int sizeServices() {
        return serviceList.size();
    }

    /** 
     * Add a 'service' element item.
     * @param item
     */
    public void addService(Service item) {
        serviceList.add(item);
    }

    /** 
     * Get 'service' element item by position.
     * @return item
     * @param index
     */
    public Service getService(int index) {
        return serviceList.get(index);
    }

    /** 
     * Remove all 'service' element items.
     */
    public void clearServices() {
        serviceList.clear();
    }
}
