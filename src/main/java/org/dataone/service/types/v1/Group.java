
package org.dataone.service.types.v1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** 
 * Group represents metadata about a :term:`Subject` that
 represents a collection of other Subjects. Groups provide a convenient
 mechanism to express access rules for certain roles that are not
 necessarily tied to particular :term:`principals` over
 time.
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Group">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:Subject" name="subject" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="groupName" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="ns:Subject" name="hasMember" minOccurs="0" maxOccurs="unbounded"/>
 *     &lt;xs:element type="ns:Subject" name="rightsHolder" minOccurs="1" maxOccurs="unbounded"/>
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
    private List<Subject> rightsHolderList = new ArrayList<Subject>();

    /** 
     * Get the 'subject' element value. The unique, immutable identifier of the
            :term:`group`. Group subjects must not be reused, and so they are
            both immutable and can not be deleted from the DataONE
            system.
     * 
     * @return value
     */
    public Subject getSubject() {
        return subject;
    }

    /** 
     * Set the 'subject' element value. The unique, immutable identifier of the
            :term:`group`. Group subjects must not be reused, and so they are
            both immutable and can not be deleted from the DataONE
            system.
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
     * Get the list of 'hasMember' element items. A :term:`Subject` that is a member of this
              group, expressed using the unique identifier for that
              Subject.
     * 
     * @return list
     */
    public List<Subject> getHasMemberList() {
        return hasMemberList;
    }

    /** 
     * Set the list of 'hasMember' element items. A :term:`Subject` that is a member of this
              group, expressed using the unique identifier for that
              Subject.
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

    /** 
     * Get the list of 'rightsHolder' element items. Represents the list of owners of this :term:`group`.
          All groups are readable by anyone in the DataONE system, but can only
          be modified by subjects listed in *rightsHolder* fields. Designation
          as a :term:`rightsHolder` allows the subject, or their equivalent
          identities, to make changes to the mutable properties of the group,
          including its name, membership list and rights holder list. The
          subject of the group itself is immutable. 
     * 
     * @return list
     */
    public List<Subject> getRightsHolderList() {
        return rightsHolderList;
    }

    /** 
     * Set the list of 'rightsHolder' element items. Represents the list of owners of this :term:`group`.
          All groups are readable by anyone in the DataONE system, but can only
          be modified by subjects listed in *rightsHolder* fields. Designation
          as a :term:`rightsHolder` allows the subject, or their equivalent
          identities, to make changes to the mutable properties of the group,
          including its name, membership list and rights holder list. The
          subject of the group itself is immutable. 
     * 
     * @param list
     */
    public void setRightsHolderList(List<Subject> list) {
        rightsHolderList = list;
    }

    /** 
     * Get the number of 'rightsHolder' element items.
     * @return count
     */
    public int sizeRightsHolderList() {
        return rightsHolderList.size();
    }

    /** 
     * Add a 'rightsHolder' element item.
     * @param item
     */
    public void addRightsHolder(Subject item) {
        rightsHolderList.add(item);
    }

    /** 
     * Get 'rightsHolder' element item by position.
     * @return item
     * @param index
     */
    public Subject getRightsHolder(int index) {
        return rightsHolderList.get(index);
    }

    /** 
     * Remove all 'rightsHolder' element items.
     */
    public void clearRightsHolderList() {
        rightsHolderList.clear();
    }
}
