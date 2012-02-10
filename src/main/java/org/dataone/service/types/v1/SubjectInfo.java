
package org.dataone.service.types.v1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** 
 * A list of :term:`Subjects`, including both
 :class:`Types.Person`s and :class:`Types.Group`s, that is returned from
 the :func:`CNIdentity.getSubjectInfo` service and
 :func:`CNIdentity.listSubjects` service.
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="SubjectInfo">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:Person" name="person" minOccurs="0" maxOccurs="unbounded"/>
 *     &lt;xs:element type="ns:Group" name="group" minOccurs="0" maxOccurs="unbounded"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class SubjectInfo implements Serializable
{
    private static final long serialVersionUID = 10000000;
    private List<Person> personList = new ArrayList<Person>();
    private List<Group> groupList = new ArrayList<Group>();

    /** 
     * Get the list of 'person' element items.
     * 
     * @return list
     */
    public List<Person> getPersonList() {
        return personList;
    }

    /** 
     * Set the list of 'person' element items.
     * 
     * @param list
     */
    public void setPersonList(List<Person> list) {
        personList = list;
    }

    /** 
     * Get the number of 'person' element items.
     * @return count
     */
    public int sizePersonList() {
        if (personList == null) {
            personList = new ArrayList<Person>();
        }
        return personList.size();
    }

    /** 
     * Add a 'person' element item.
     * @param item
     */
    public void addPerson(Person item) {
        if (personList == null) {
            personList = new ArrayList<Person>();
        }
        personList.add(item);
    }

    /** 
     * Get 'person' element item by position.
     * @return item
     * @param index
     */
    public Person getPerson(int index) {
        if (personList == null) {
            personList = new ArrayList<Person>();
        }
        return personList.get(index);
    }

    /** 
     * Remove all 'person' element items.
     */
    public void clearPersonList() {
        if (personList == null) {
            personList = new ArrayList<Person>();
        }
        personList.clear();
    }

    /** 
     * Get the list of 'group' element items.
     * 
     * @return list
     */
    public List<Group> getGroupList() {
        return groupList;
    }

    /** 
     * Set the list of 'group' element items.
     * 
     * @param list
     */
    public void setGroupList(List<Group> list) {
        groupList = list;
    }

    /** 
     * Get the number of 'group' element items.
     * @return count
     */
    public int sizeGroupList() {
        if (groupList == null) {
            groupList = new ArrayList<Group>();
        }
        return groupList.size();
    }

    /** 
     * Add a 'group' element item.
     * @param item
     */
    public void addGroup(Group item) {
        if (groupList == null) {
            groupList = new ArrayList<Group>();
        }
        groupList.add(item);
    }

    /** 
     * Get 'group' element item by position.
     * @return item
     * @param index
     */
    public Group getGroup(int index) {
        if (groupList == null) {
            groupList = new ArrayList<Group>();
        }
        return groupList.get(index);
    }

    /** 
     * Remove all 'group' element items.
     */
    public void clearGroupList() {
        if (groupList == null) {
            groupList = new ArrayList<Group>();
        }
        groupList.clear();
    }
}
