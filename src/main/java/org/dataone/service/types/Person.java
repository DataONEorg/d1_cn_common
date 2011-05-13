
package org.dataone.service.types;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/0.6.1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Person">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:Principal" name="principal" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="givenName" minOccurs="1" maxOccurs="unbounded"/>
 *     &lt;xs:element type="xs:string" name="familyName" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="email" minOccurs="1" maxOccurs="unbounded"/>
 *     &lt;xs:element type="ns:Principal" name="isMemberOf" minOccurs="0" maxOccurs="unbounded"/>
 *     &lt;xs:element type="ns:Principal" name="equivalentIdentity" minOccurs="0" maxOccurs="unbounded"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class Person
{
    private Principal principal;
    private List<String> givenNameList = new ArrayList<String>();
    private String familyName;
    private List<String> emailList = new ArrayList<String>();
    private List<Principal> isMemberOfList = new ArrayList<Principal>();
    private List<Principal> equivalentIdentityList = new ArrayList<Principal>();

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
     * Get the list of 'givenName' element items.
     * 
     * @return list
     */
    public List<String> getGivenNames() {
        return givenNameList;
    }

    /** 
     * Set the list of 'givenName' element items.
     * 
     * @param list
     */
    public void setGivenNames(List<String> list) {
        givenNameList = list;
    }

    /** 
     * Get the number of 'givenName' element items.
     * @return count
     */
    public int sizeGivenNames() {
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
    public void clearGivenNames() {
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
    public List<String> getEmails() {
        return emailList;
    }

    /** 
     * Set the list of 'email' element items.
     * 
     * @param list
     */
    public void setEmails(List<String> list) {
        emailList = list;
    }

    /** 
     * Get the number of 'email' element items.
     * @return count
     */
    public int sizeEmails() {
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
    public void clearEmails() {
        emailList.clear();
    }

    /** 
     * Get the list of 'isMemberOf' element items.
     * 
     * @return list
     */
    public List<Principal> getIsMemberOfs() {
        return isMemberOfList;
    }

    /** 
     * Set the list of 'isMemberOf' element items.
     * 
     * @param list
     */
    public void setIsMemberOfs(List<Principal> list) {
        isMemberOfList = list;
    }

    /** 
     * Get the number of 'isMemberOf' element items.
     * @return count
     */
    public int sizeIsMemberOfs() {
        return isMemberOfList.size();
    }

    /** 
     * Add a 'isMemberOf' element item.
     * @param item
     */
    public void addIsMemberOf(Principal item) {
        isMemberOfList.add(item);
    }

    /** 
     * Get 'isMemberOf' element item by position.
     * @return item
     * @param index
     */
    public Principal getIsMemberOf(int index) {
        return isMemberOfList.get(index);
    }

    /** 
     * Remove all 'isMemberOf' element items.
     */
    public void clearIsMemberOfs() {
        isMemberOfList.clear();
    }

    /** 
     * Get the list of 'equivalentIdentity' element items.
     * 
     * @return list
     */
    public List<Principal> getEquivalentIdentities() {
        return equivalentIdentityList;
    }

    /** 
     * Set the list of 'equivalentIdentity' element items.
     * 
     * @param list
     */
    public void setEquivalentIdentities(List<Principal> list) {
        equivalentIdentityList = list;
    }

    /** 
     * Get the number of 'equivalentIdentity' element items.
     * @return count
     */
    public int sizeEquivalentIdentities() {
        return equivalentIdentityList.size();
    }

    /** 
     * Add a 'equivalentIdentity' element item.
     * @param item
     */
    public void addEquivalentIdentity(Principal item) {
        equivalentIdentityList.add(item);
    }

    /** 
     * Get 'equivalentIdentity' element item by position.
     * @return item
     * @param index
     */
    public Principal getEquivalentIdentity(int index) {
        return equivalentIdentityList.get(index);
    }

    /** 
     * Remove all 'equivalentIdentity' element items.
     */
    public void clearEquivalentIdentities() {
        equivalentIdentityList.clear();
    }
}
