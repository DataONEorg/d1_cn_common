
package org.dataone.service.types;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/0.6.2" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="AccessRule">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:Subject" name="subject" minOccurs="1" maxOccurs="unbounded"/>
 *     &lt;xs:element type="ns:Permission" name="permission" minOccurs="1" maxOccurs="unbounded"/>
 *     &lt;xs:element type="ns:Identifier" name="resource" minOccurs="1" maxOccurs="unbounded"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class AccessRule
{
    private List<Subject> subjectList = new ArrayList<Subject>();
    private List<Permission> permissionList = new ArrayList<Permission>();
    private List<Identifier> resourceList = new ArrayList<Identifier>();

    /** 
     * Get the list of 'subject' element items.
     * 
     * @return list
     */
    public List<Subject> getSubjectList() {
        return subjectList;
    }

    /** 
     * Set the list of 'subject' element items.
     * 
     * @param list
     */
    public void setSubjectList(List<Subject> list) {
        subjectList = list;
    }

    /** 
     * Get the number of 'subject' element items.
     * @return count
     */
    public int sizeSubjectList() {
        return subjectList.size();
    }

    /** 
     * Add a 'subject' element item.
     * @param item
     */
    public void addSubject(Subject item) {
        subjectList.add(item);
    }

    /** 
     * Get 'subject' element item by position.
     * @return item
     * @param index
     */
    public Subject getSubject(int index) {
        return subjectList.get(index);
    }

    /** 
     * Remove all 'subject' element items.
     */
    public void clearSubjectList() {
        subjectList.clear();
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
