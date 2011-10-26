
package org.dataone.service.types.v1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** 
 * Group represents metadata about a Subject that is a group of other Subjects
 and that can be used by clients and nodes for displaying AccessPolicy
 information.
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Group">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:Subject" name="subject" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="groupName" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="ns:Subject" name="hasMember" minOccurs="0" maxOccurs="unbounded"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class Group implements Serializable
{
    private static final long serialVersionUID = 10000000;
    private Subject subject;
    private String groupName;
    private List<Subject> hasMemberList = new ArrayList<Subject>();

    /** 
     * Get the 'subject' element value. The unique identifier of the group.
     * 
     * @return value
     */
    public Subject getSubject() {
        return subject;
    }

    /** 
     * Set the 'subject' element value. The unique identifier of the group.
     * 
     * @param subject
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    /** 
     * Get the 'groupName' element value. The name of the Group.
     * 
     * @return value
     */
    public String getGroupName() {
        return groupName;
    }

    /** 
     * Set the 'groupName' element value. The name of the Group.
     * 
     * @param groupName
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /** 
     * Get the list of 'hasMember' element items. A Subject that is a member of this group, expressed using the
                      unique identifier for that Subject.
     * 
     * @return list
     */
    public List<Subject> getHasMemberList() {
        return hasMemberList;
    }

    /** 
     * Set the list of 'hasMember' element items. A Subject that is a member of this group, expressed using the
                      unique identifier for that Subject.
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
