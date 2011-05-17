
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
    public List<Principal> getPrincipalList() {
        return principalList;
    }

    /** 
     * Set the list of 'principal' element items.
     * 
     * @param list
     */
    public void setPrincipalList(List<Principal> list) {
        principalList = list;
    }

    /** 
     * Get the number of 'principal' element items.
     * @return count
     */
    public int sizePrincipalList() {
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
    public void clearPrincipalList() {
        principalList.clear();
    }

    /** 
     * Get the list of 'permission' element items.
     * 
     * @return list
     */
    public List<Permission> getPermissionList() {
        return permissionList;
    }

    /** 
     * Set the list of 'permission' element items.
     * 
     * @param list
     */
    public void setPermissionList(List<Permission> list) {
        permissionList = list;
    }

    /** 
     * Get the number of 'permission' element items.
     * @return count
     */
    public int sizePermissionList() {
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
    public void clearPermissionList() {
        permissionList.clear();
    }

    /** 
     * Get the list of 'resource' element items.
     * 
     * @return list
     */
    public List<Identifier> getResourceList() {
        return resourceList;
    }

    /** 
     * Set the list of 'resource' element items.
     * 
     * @param list
     */
    public void setResourceList(List<Identifier> list) {
        resourceList = list;
    }

    /** 
     * Get the number of 'resource' element items.
     * @return count
     */
    public int sizeResourceList() {
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
    public void clearResourceList() {
        resourceList.clear();
    }
}
