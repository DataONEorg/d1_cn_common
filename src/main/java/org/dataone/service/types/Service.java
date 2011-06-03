
package org.dataone.service.types;

import java.util.ArrayList;
import java.util.List;

/** 
 * Name and version of a DataONE software stack component are
 equivalent to the statusresponselist.xsd name and version. 
 (-rpw notes from meeting 9/25/2010, but Component Name and Version is different than
 Service API name and version!)
 A process should check  MN_health.getStatus()  periodically and  
 update the version, availability and dateChecked for each service.
 May need to update method definitions at same time

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/0.6.2" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Service">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="xs:string" name="name" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="ns:ServiceMethod" name="method" minOccurs="0" maxOccurs="unbounded"/>
 *   &lt;/xs:sequence>
 *   &lt;xs:attribute type="xs:string" use="required" name="version"/>
 *   &lt;xs:attribute type="xs:boolean" name="available"/>
 * &lt;/xs:complexType>
 * </pre>
 */
public class Service
{
    private String name;
    private List<ServiceMethod> methodList = new ArrayList<ServiceMethod>();
    private String version;
    private Boolean available;

    /** 
     * Get the 'name' element value.
     * 
     * @return value
     */
    public String getName() {
        return name;
    }

    /** 
     * Set the 'name' element value.
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** 
     * Get the list of 'method' element items.
     * 
     * @return list
     */
    public List<ServiceMethod> getMethodList() {
        return methodList;
    }

    /** 
     * Set the list of 'method' element items.
     * 
     * @param list
     */
    public void setMethodList(List<ServiceMethod> list) {
        methodList = list;
    }

    /** 
     * Get the number of 'method' element items.
     * @return count
     */
    public int sizeMethodList() {
        return methodList.size();
    }

    /** 
     * Add a 'method' element item.
     * @param item
     */
    public void addMethod(ServiceMethod item) {
        methodList.add(item);
    }

    /** 
     * Get 'method' element item by position.
     * @return item
     * @param index
     */
    public ServiceMethod getMethod(int index) {
        return methodList.get(index);
    }

    /** 
     * Remove all 'method' element items.
     */
    public void clearMethodList() {
        methodList.clear();
    }

    /** 
     * Get the 'version' attribute value.
     * 
     * @return value
     */
    public String getVersion() {
        return version;
    }

    /** 
     * Set the 'version' attribute value.
     * 
     * @param version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /** 
     * Get the 'available' attribute value.
     * 
     * @return value
     */
    public Boolean getAvailable() {
        return available;
    }

    /** 
     * Set the 'available' attribute value.
     * 
     * @param available
     */
    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
