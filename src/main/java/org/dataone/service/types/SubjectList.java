
package org.dataone.service.types;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/0.6.2" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="SubjectList">
 *   &lt;xs:choice>
 *     &lt;xs:element type="ns:Person" name="person" minOccurs="0" maxOccurs="unbounded"/>
 *     &lt;xs:element type="ns:Group" name="group" minOccurs="0" maxOccurs="unbounded"/>
 *   &lt;/xs:choice>
 * &lt;/xs:complexType>
 * </pre>
 */
public class SubjectList
{
    private int choiceSelect = -1;
    private static final int PERSON_CHOICE = 0;
    private static final int GROUP_CHOICE = 1;
    private List<Person> personList = new ArrayList<Person>();
    private List<Group> groupList = new ArrayList<Group>();

    private void setChoiceSelect(int choice) {
        if (choiceSelect == -1) {
            choiceSelect = choice;
        } else if (choiceSelect != choice) {
            throw new IllegalStateException(
                    "Need to call clearChoiceSelect() before changing existing choice");
        }
    }

    /** 
     * Clear the choice selection.
     */
    public void clearChoiceSelect() {
        choiceSelect = -1;
    }

    /** 
     * Check if PersonList is current selection for choice.
     * 
     * @return <code>true</code> if selection, <code>false</code> if not
     */
    public boolean ifPerson() {
        return choiceSelect == PERSON_CHOICE;
    }

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
        setChoiceSelect(PERSON_CHOICE);
        personList = list;
    }

    /** 
     * Get the number of 'person' element items.
     * @return count
     */
    public int sizePersonList() {
        return personList.size();
    }

    /** 
     * Add a 'person' element item.
     * @param item
     */
    public void addPerson(Person item) {
        personList.add(item);
    }

    /** 
     * Get 'person' element item by position.
     * @return item
     * @param index
     */
    public Person getPerson(int index) {
        return personList.get(index);
    }

    /** 
     * Remove all 'person' element items.
     */
    public void clearPersonList() {
        personList.clear();
    }

    /** 
     * Check if GroupList is current selection for choice.
     * 
     * @return <code>true</code> if selection, <code>false</code> if not
     */
    public boolean ifGroup() {
        return choiceSelect == GROUP_CHOICE;
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
        setChoiceSelect(GROUP_CHOICE);
        groupList = list;
    }

    /** 
     * Get the number of 'group' element items.
     * @return count
     */
    public int sizeGroupList() {
        return groupList.size();
    }

    /** 
     * Add a 'group' element item.
     * @param item
     */
    public void addGroup(Group item) {
        groupList.add(item);
    }

    /** 
     * Get 'group' element item by position.
     * @return item
     * @param index
     */
    public Group getGroup(int index) {
        return groupList.get(index);
    }

    /** 
     * Remove all 'group' element items.
     */
    public void clearGroupList() {
        groupList.clear();
    }
}
