
package org.dataone.service.types;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/0.6.2" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Group">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:Subject" name="subject" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="groupName" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="ns:Subject" name="hasMember" minOccurs="0" maxOccurs="unbounded"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class Group
{
    private Subject subject;
    private String groupName;
    private List<Subject> hasMemberList = new ArrayList<Subject>();

    /** 
     * Get the 'subject' element value.
     * 
     * @return value
     */
    public Subject getSubject() {
        return subject;
    }

    /** 
     * Set the 'subject' element value.
     * 
     * @param subject
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
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
    public List<Subject> getHasMemberList() {
        return hasMemberList;
    }

    /** 
     * Set the list of 'hasMember' element items.
     * 
     * @param list
     */
    public void setHasMemberList(List<Subject> list) {
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
    public void addHasMember(Subject item) {
        hasMemberList.add(item);
    }

    /** 
     * Get 'hasMember' element item by position.
     * @return item
     * @param index
     */
    public Subject getHasMember(int index) {
        return hasMemberList.get(index);
    }

    /** 
     * Remove all 'hasMember' element items.
     */
    public void clearHasMemberList() {
        hasMemberList.clear();
    }
}
