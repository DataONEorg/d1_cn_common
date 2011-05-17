
package org.dataone.service.types;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/0.6.1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Group">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:Principal" name="principal" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="groupName" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="ns:Principal" name="hasMember" minOccurs="0" maxOccurs="unbounded"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class Group
{
    private Principal principal;
    private String groupName;
    private List<Principal> hasMemberList = new ArrayList<Principal>();

    /** 
     * Get the 'principal' element value.
     * 
     * @return value
     */
    public Principal getPrincipal() {
        return principal;
    }

    /** 
     * Set the 'principal' element value.
     * 
     * @param principal
     */
    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    /** 
     * Get the 'groupName' element value.
     * 
     * @return value
     */
    public String getGroupName() {
        return groupName;
    }

    /** 
     * Set the 'groupName' element value.
     * 
     * @param groupName
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /** 
     * Get the list of 'hasMember' element items.
     * 
     * @return list
     */
    public List<Principal> getHasMemberList() {
        return hasMemberList;
    }

    /** 
     * Set the list of 'hasMember' element items.
     * 
     * @param list
     */
    public void setHasMemberList(List<Principal> list) {
        hasMemberList = list;
    }

    /** 
     * Get the number of 'hasMember' element items.
     * @return count
     */
    public int sizeHasMemberList() {
        return hasMemberList.size();
    }

    /** 
     * Add a 'hasMember' element item.
     * @param item
     */
    public void addHasMember(Principal item) {
        hasMemberList.add(item);
    }

    /** 
     * Get 'hasMember' element item by position.
     * @return item
     * @param index
     */
    public Principal getHasMember(int index) {
        return hasMemberList.get(index);
    }

    /** 
     * Remove all 'hasMember' element items.
     */
    public void clearHasMemberList() {
        hasMemberList.clear();
    }
}
