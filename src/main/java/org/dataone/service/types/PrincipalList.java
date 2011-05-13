
package org.dataone.service.types;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:d1="http://ns.dataone.org/service/types/0.6.1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="PrincipalList">
 *   &lt;xs:choice>
 *     &lt;xs:element type="d1:Person" name="person" minOccurs="0" maxOccurs="unbounded"/>
 *     &lt;xs:element type="d1:Group" name="group" minOccurs="0" maxOccurs="unbounded">
 *       &lt;!-- Reference to inner class Group -->
 *     &lt;/xs:element>
 *   &lt;/xs:choice>
 * &lt;/xs:complexType>
 * </pre>
 */
public class PrincipalList
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
     * Check if Persons is current selection for choice.
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
    public List<Person> getPersons() {
        return personList;
    }

    /** 
     * Set the list of 'person' element items.
     * 
     * @param list
     */
    public void setPersons(List<Person> list) {
        setChoiceSelect(PERSON_CHOICE);
        personList = list;
    }

    /** 
     * Get the number of 'person' element items.
     * @return count
     */
    public int sizePersons() {
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
    public void clearPersons() {
        personList.clear();
    }

    /** 
     * Check if Groups is current selection for choice.
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
    public List<Group> getGroups() {
        return groupList;
    }

    /** 
     * Set the list of 'group' element items.
     * 
     * @param list
     */
    public void setGroups(List<Group> list) {
        setChoiceSelect(GROUP_CHOICE);
        groupList = list;
    }

    /** 
     * Get the number of 'group' element items.
     * @return count
     */
    public int sizeGroups() {
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
    public void clearGroups() {
        groupList.clear();
    }
    /** 
     * Schema fragment(s) for this class:
     * <pre>
     * &lt;xs:element xmlns:ns="http://ns.dataone.org/service/types/0.6.1" xmlns:xs="http://www.w3.org/2001/XMLSchema" type="ns:Group" name="group" minOccurs="0" maxOccurs="unbounded"/>
     * 
     * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/0.6.1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Group">
     *   &lt;xs:sequence>
     *     &lt;xs:element type="ns:Principal" name="principal" minOccurs="1" maxOccurs="1"/>
     *     &lt;xs:element type="xs:string" name="groupName" minOccurs="1" maxOccurs="1"/>
     *     &lt;xs:element type="ns:Principal" name="hasMember" minOccurs="0" maxOccurs="unbounded"/>
     *   &lt;/xs:sequence>
     * &lt;/xs:complexType>
     * </pre>
     */
    public static class Group
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
        public List<Principal> getHasMembers() {
            return hasMemberList;
        }

        /** 
         * Set the list of 'hasMember' element items.
         * 
         * @param list
         */
        public void setHasMembers(List<Principal> list) {
            hasMemberList = list;
        }

        /** 
         * Get the number of 'hasMember' element items.
         * @return count
         */
        public int sizeHasMembers() {
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
        public void clearHasMembers() {
            hasMemberList.clear();
        }
    }
}
