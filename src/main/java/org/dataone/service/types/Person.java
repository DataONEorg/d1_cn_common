
package org.dataone.service.types;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/0.6.2" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Person">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:Subject" name="subject" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="givenName" minOccurs="1" maxOccurs="unbounded"/>
 *     &lt;xs:element type="xs:string" name="familyName" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="email" minOccurs="1" maxOccurs="unbounded"/>
 *     &lt;xs:element type="ns:Subject" name="isMemberOf" minOccurs="0" maxOccurs="unbounded"/>
 *     &lt;xs:element type="ns:Subject" name="equivalentIdentity" minOccurs="0" maxOccurs="unbounded"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class Person
{
    private Subject subject;
    private List<String> givenNameList = new ArrayList<String>();
    private String familyName;
    private List<String> emailList = new ArrayList<String>();
    private List<Subject> isMemberOfList = new ArrayList<Subject>();
    private List<Subject> equivalentIdentityList = new ArrayList<Subject>();

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
     * Get the list of 'givenName' element items.
     * 
     * @return list
     */
    public List<String> getGivenNameList() {
        return givenNameList;
    }

    /** 
     * Set the list of 'givenName' element items.
     * 
     * @param list
     */
    public void setGivenNameList(List<String> list) {
        givenNameList = list;
    }

    /** 
     * Get the number of 'givenName' element items.
     * @return count
     */
    public int sizeGivenNameList() {
        return givenNameList.size();
    }

    /** 
     * Add a 'givenName' element item.
     * @param item
     */
    public void addGivenName(String item) {
        givenNameList.add(item);
    }

    /** 
     * Get 'givenName' element item by position.
     * @return item
     * @param index
     */
    public String getGivenName(int index) {
        return givenNameList.get(index);
    }

    /** 
     * Remove all 'givenName' element items.
     */
    public void clearGivenNameList() {
        givenNameList.clear();
    }

    /** 
     * Get the 'familyName' element value.
     * 
     * @return value
     */
    public String getFamilyName() {
        return familyName;
    }

    /** 
     * Set the 'familyName' element value.
     * 
     * @param familyName
     */
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /** 
     * Get the list of 'email' element items.
     * 
     * @return list
     */
    public List<String> getEmailList() {
        return emailList;
    }

    /** 
     * Set the list of 'email' element items.
     * 
     * @param list
     */
    public void setEmailList(List<String> list) {
        emailList = list;
    }

    /** 
     * Get the number of 'email' element items.
     * @return count
     */
    public int sizeEmailList() {
        return emailList.size();
    }

    /** 
     * Add a 'email' element item.
     * @param item
     */
    public void addEmail(String item) {
        emailList.add(item);
    }

    /** 
     * Get 'email' element item by position.
     * @return item
     * @param index
     */
    public String getEmail(int index) {
        return emailList.get(index);
    }

    /** 
     * Remove all 'email' element items.
     */
    public void clearEmailList() {
        emailList.clear();
    }

    /** 
     * Get the list of 'isMemberOf' element items.
     * 
     * @return list
     */
    public List<Subject> getIsMemberOfList() {
        return isMemberOfList;
    }

    /** 
     * Set the list of 'isMemberOf' element items.
     * 
     * @param list
     */
    public void setIsMemberOfList(List<Subject> list) {
        isMemberOfList = list;
    }

    /** 
     * Get the number of 'isMemberOf' element items.
     * @return count
     */
    public int sizeIsMemberOfList() {
        return isMemberOfList.size();
    }

    /** 
     * Add a 'isMemberOf' element item.
     * @param item
     */
    public void addIsMemberOf(Subject item) {
        isMemberOfList.add(item);
    }

    /** 
     * Get 'isMemberOf' element item by position.
     * @return item
     * @param index
     */
    public Subject getIsMemberOf(int index) {
        return isMemberOfList.get(index);
    }

    /** 
     * Remove all 'isMemberOf' element items.
     */
    public void clearIsMemberOfList() {
        isMemberOfList.clear();
    }

    /** 
     * Get the list of 'equivalentIdentity' element items.
     * 
     * @return list
     */
    public List<Subject> getEquivalentIdentityList() {
        return equivalentIdentityList;
    }

    /** 
     * Set the list of 'equivalentIdentity' element items.
     * 
     * @param list
     */
    public void setEquivalentIdentityList(List<Subject> list) {
        equivalentIdentityList = list;
    }

    /** 
     * Get the number of 'equivalentIdentity' element items.
     * @return count
     */
    public int sizeEquivalentIdentityList() {
        return equivalentIdentityList.size();
    }

    /** 
     * Add a 'equivalentIdentity' element item.
     * @param item
     */
    public void addEquivalentIdentity(Subject item) {
        equivalentIdentityList.add(item);
    }

    /** 
     * Get 'equivalentIdentity' element item by position.
     * @return item
     * @param index
     */
    public Subject getEquivalentIdentity(int index) {
        return equivalentIdentityList.get(index);
    }

    /** 
     * Remove all 'equivalentIdentity' element items.
     */
    public void clearEquivalentIdentityList() {
        equivalentIdentityList.clear();
    }
}
