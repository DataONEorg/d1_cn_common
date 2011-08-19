
package org.dataone.service.types.v1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** 
 * The available Dataone Service APIs that are exposed on a Node
 Name and version of a DataONE software stack component are
 equivalent to the statusresponselist.xsd name and version. 
 Without a restriction, all service methods are available to all callers.
 Restrictions may be placed on individual methods of the service to limit 
 the service to a certain set of Subjects.

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Service">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:ServiceMethodRestriction" name="restriction" minOccurs="0" maxOccurs="unbounded"/>
 *   &lt;/xs:sequence>
 *   &lt;xs:attribute type="xs:string" use="required" name="name"/>
 *   &lt;xs:attribute type="xs:string" use="required" name="version"/>
 *   &lt;xs:attribute type="xs:boolean" name="available"/>
 * &lt;/xs:complexType>
 * </pre>
 */
public class Service implements Serializable
{
    private List<ServiceMethodRestriction> restrictionList = new ArrayList<ServiceMethodRestriction>();
    private String name;
    private String version;
    private Boolean available;

    /** 
     * Get the list of 'restriction' element items.
     * 
     * @return list
     */
    public List<ServiceMethodRestriction> getRestrictionList() {
        return restrictionList;
    }

    /** 
     * Set the list of 'restriction' element items.
     * 
     * @param list
     */
    public void setRestrictionList(List<ServiceMethodRestriction> list) {
        restrictionList = list;
    }

    /** 
     * Get the number of 'restriction' element items.
     * @return count
     */
    public int sizeRestrictionList() {
        return restrictionList.size();
    }

    /** 
     * Add a 'restriction' element item.
     * @param item
     */
    public void addRestriction(ServiceMethodRestriction item) {
        restrictionList.add(item);
    }

    /** 
     * Get 'restriction' element item by position.
     * @return item
     * @param index
     */
    public ServiceMethodRestriction getRestriction(int index) {
        return restrictionList.get(index);
    }

    /** 
     * Remove all 'restriction' element items.
     */
    public void clearRestrictionList() {
        restrictionList.clear();
    }

    /** 
     * Get the 'name' attribute value.
     * 
     * @return value
     */
    public String getName() {
        return name;
    }

    /** 
     * Set the 'name' attribute value.
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
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
