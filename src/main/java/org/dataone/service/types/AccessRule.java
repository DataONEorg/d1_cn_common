
package org.dataone.service.types;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/0.6.1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="AccessRule">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:Principal" name="principal" minOccurs="1" maxOccurs="unbounded"/>
 *     &lt;xs:element type="ns:Permission" name="permission" minOccurs="1" maxOccurs="unbounded"/>
 *     &lt;xs:element type="ns:Identifier" name="resource" minOccurs="1" maxOccurs="unbounded"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * 
 * &lt;xs:simpleType xmlns:ns="http://ns.dataone.org/service/types/0.6.1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Permission">
 *   &lt;!-- Reference to inner class Permission -->
 * &lt;/xs:simpleType>
 * </pre>
 */
public class AccessRule
{
    private List<Principal> principalList = new ArrayList<Principal>();
    private List<Permission> permissionList = new ArrayList<Permission>();
    private List<Identifier> resourceList = new ArrayList<Identifier>();

    /** 
     * Get the list of 'principal' element items.
     * 
     * @return list
     */
    public List<Principal> getPrincipals() {
        return principalList;
    }

    /** 
     * Set the list of 'principal' element items.
     * 
     * @param list
     */
    public void setPrincipals(List<Principal> list) {
        principalList = list;
    }

    /** 
     * Get the number of 'principal' element items.
     * @return count
     */
    public int sizePrincipals() {
        return principalList.size();
    }

    /** 
     * Add a 'principal' element item.
     * @param item
     */
    public void addPrincipal(Principal item) {
        principalList.add(item);
    }

    /** 
     * Get 'principal' element item by position.
     * @return item
     * @param index
     */
    public Principal getPrincipal(int index) {
        return principalList.get(index);
    }

    /** 
     * Remove all 'principal' element items.
     */
    public void clearPrincipals() {
        principalList.clear();
    }

    /** 
     * Get the list of 'permission' element items.
     * 
     * @return list
     */
    public List<Permission> getPermissions() {
        return permissionList;
    }

    /** 
     * Set the list of 'permission' element items.
     * 
     * @param list
     */
    public void setPermissions(List<Permission> list) {
        permissionList = list;
    }

    /** 
     * Get the number of 'permission' element items.
     * @return count
     */
    public int sizePermissions() {
        return permissionList.size();
    }

    /** 
     * Add a 'permission' element item.
     * @param item
     */
    public void addPermission(Permission item) {
        permissionList.add(item);
    }

    /** 
     * Get 'permission' element item by position.
     * @return item
     * @param index
     */
    public Permission getPermission(int index) {
        return permissionList.get(index);
    }

    /** 
     * Remove all 'permission' element items.
     */
    public void clearPermissions() {
        permissionList.clear();
    }

    /** 
     * Get the list of 'resource' element items.
     * 
     * @return list
     */
    public List<Identifier> getResources() {
        return resourceList;
    }

    /** 
     * Set the list of 'resource' element items.
     * 
     * @param list
     */
    public void setResources(List<Identifier> list) {
        resourceList = list;
    }

    /** 
     * Get the number of 'resource' element items.
     * @return count
     */
    public int sizeResources() {
        return resourceList.size();
    }

    /** 
     * Add a 'resource' element item.
     * @param item
     */
    public void addResource(Identifier item) {
        resourceList.add(item);
    }

    /** 
     * Get 'resource' element item by position.
     * @return item
     * @param index
     */
    public Identifier getResource(int index) {
        return resourceList.get(index);
    }

    /** 
     * Remove all 'resource' element items.
     */
    public void clearResources() {
        resourceList.clear();
    }
    /** 
     * A string value indicating the set of actions that can be performed on a
    resource as specified in an access policy.  The set of permissions include
    the ability to read a resource, modify a resource (write), and to change
    the set of access control policies for a resource (changePermission).  In
    addition, there is a permission that controls ability to execute a service
    (execute). Permissions are cumulative, in that higher level permissions
    include all of the priveledges of lower levels (e.g., given write access, one
    also implicitly has read access).
     * 
     * Schema fragment(s) for this class:
     * <pre>
     * &lt;xs:simpleType xmlns:ns="http://ns.dataone.org/service/types/0.6.1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Permission">
     *   &lt;xs:restriction base="xs:string">
     *     &lt;xs:enumeration value="read"/>
     *     &lt;xs:enumeration value="write"/>
     *     &lt;xs:enumeration value="changePermission"/>
     *     &lt;xs:enumeration value="execute"/>
     *   &lt;/xs:restriction>
     * &lt;/xs:simpleType>
     * </pre>
     */
    public static enum Permission {
        READ("read"), WRITE("write"), CHANGE_PERMISSION("changePermission"), EXECUTE(
                "execute");
        private final String value;

        private Permission(String value) {
            this.value = value;
        }

        public String toString() {
            return value;
        }

        public static Permission convert(String value) {
            for (Permission inst : values()) {
                if (inst.toString().equals(value)) {
                    return inst;
                }
            }
            return null;
        }
    }
}
