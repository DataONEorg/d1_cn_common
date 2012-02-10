
package org.dataone.service.types.v1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** 
 * The available Dataone Service APIs that are exposed on
 a Node. Without a restriction, all service methods are available to all
 callers. Restrictions may be placed on individual methods of the service
 to limit the service to a certain set of :term:`Subjects`. Enforcement
 of these service restrictions is incumbent on the Node service
 implementation.
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
    private static final long serialVersionUID = 10000000;
    private List<ServiceMethodRestriction> restrictionList = new ArrayList<ServiceMethodRestriction>();
    private String name;
    private String version;
    private Boolean available;

    /** 
     * Get the list of 'restriction' element items. A list of method names and :term:`Subjects` with
            permission to invoke those methods.
     * 
     * @return list
     */
    public List<ServiceMethodRestriction> getRestrictionList() {
        return restrictionList;
    }

    /** 
     * Set the list of 'restriction' element items. A list of method names and :term:`Subjects` with
            permission to invoke those methods.
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
        if (restrictionList == null) {
            restrictionList = new ArrayList<ServiceMethodRestriction>();
        }
        return restrictionList.size();
    }

    /** 
     * Add a 'restriction' element item.
     * @param item
     */
    public void addRestriction(ServiceMethodRestriction item) {
        if (restrictionList == null) {
            restrictionList = new ArrayList<ServiceMethodRestriction>();
        }
        restrictionList.add(item);
    }

    /** 
     * Get 'restriction' element item by position.
     * @return item
     * @param index
     */
    public ServiceMethodRestriction getRestriction(int index) {
        if (restrictionList == null) {
            restrictionList = new ArrayList<ServiceMethodRestriction>();
        }
        return restrictionList.get(index);
    }

    /** 
     * Remove all 'restriction' element items.
     */
    public void clearRestrictionList() {
        if (restrictionList == null) {
            restrictionList = new ArrayList<ServiceMethodRestriction>();
        }
        restrictionList.clear();
    }

    /** 
     * Get the 'name' attribute value. The name of the service. The valid list of entries
          for Member Nodes includes: *MNCore*, *MNRead*, *MNAuthorization*,
     *MNStorage*, and *MNReplication*. The valid list of entries for
          Coordinating Nodes includes: *CNCore*, *CNRead*, *CNAuthorization*,
     *CNIdentity*, *CNReplication*, and *CNRegister*.
     * 
     * @return value
     */
    public String getName() {
        return name;
    }

    /** 
     * Set the 'name' attribute value. The name of the service. The valid list of entries
          for Member Nodes includes: *MNCore*, *MNRead*, *MNAuthorization*,
     *MNStorage*, and *MNReplication*. The valid list of entries for
          Coordinating Nodes includes: *CNCore*, *CNRead*, *CNAuthorization*,
     *CNIdentity*, *CNReplication*, and *CNRegister*.
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** 
     * Get the 'version' attribute value. Version of the service supported by the node.
          Version is expressed in whole steps, no minor version identifiers are
          used. For example, the version 1.0.0 API would be indicated by the
          value "v1"
     * 
     * @return value
     */
    public String getVersion() {
        return version;
    }

    /** 
     * Set the 'version' attribute value. Version of the service supported by the node.
          Version is expressed in whole steps, no minor version identifiers are
          used. For example, the version 1.0.0 API would be indicated by the
          value "v1"
     * 
     * @param version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /** 
     * Get the 'available' attribute value. A boolean flag indicating if the service is
          available (*true*, default) or otherwise (*false*).
          
     * 
     * @return value
     */
    public Boolean getAvailable() {
        return available;
    }

    /** 
     * Set the 'available' attribute value. A boolean flag indicating if the service is
          available (*true*, default) or otherwise (*false*).
          
     * 
     * @param available
     */
    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
