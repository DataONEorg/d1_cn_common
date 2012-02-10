
package org.dataone.service.types.v1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** 
 * *Person* represents metadata about a :term:`Principal`
 that is a person and that can be used by clients and nodes for
 :class:`Types.AccessPolicy` information. The mutable properties of a
 *Person* instance can only be changed by itself (i.e., the Subject
 identifying the Person instance) and by the Coordinating Node identity,
 but can be read by any identity in the DataONE system.

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Person">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:Subject" name="subject" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="givenName" minOccurs="1" maxOccurs="unbounded"/>
 *     &lt;xs:element type="xs:string" name="familyName" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="email" minOccurs="0" maxOccurs="unbounded"/>
 *     &lt;xs:element type="ns:Subject" name="isMemberOf" minOccurs="0" maxOccurs="unbounded"/>
 *     &lt;xs:element type="ns:Subject" name="equivalentIdentity" minOccurs="0" maxOccurs="unbounded"/>
 *     &lt;xs:element type="xs:boolean" name="verified" minOccurs="0" maxOccurs="1"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class Person implements Serializable
{
    private static final long serialVersionUID = 10000000;
    private Subject subject;
    private List<String> givenNameList = new ArrayList<String>();
    private String familyName;
    private List<String> emailList = new ArrayList<String>();
    private List<Subject> isMemberOfList = new ArrayList<Subject>();
    private List<Subject> equivalentIdentityList = new ArrayList<Subject>();
    private Boolean verified;

    /** 
     * Get the 'subject' element value. The unique, immutable identifier for the
            person.
     * 
     * @return value
     */
    public Subject getSubject() {
        return subject;
    }

    /** 
     * Set the 'subject' element value. The unique, immutable identifier for the
            person.
     * 
     * @param subject
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    /** 
     * Get the list of 'givenName' element items. The given name of the Person, repeatable if they
            have more than one given name.
     * 
     * @return list
     */
    public List<String> getGivenNameList() {
        return givenNameList;
    }

    /** 
     * Set the list of 'givenName' element items. The given name of the Person, repeatable if they
            have more than one given name.
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
        if (givenNameList == null) {
            givenNameList = new ArrayList<String>();
        }
        return givenNameList.size();
    }

    /** 
     * Add a 'givenName' element item.
     * @param item
     */
    public void addGivenName(String item) {
        if (givenNameList == null) {
            givenNameList = new ArrayList<String>();
        }
        givenNameList.add(item);
    }

    /** 
     * Get 'givenName' element item by position.
     * @return item
     * @param index
     */
    public String getGivenName(int index) {
        if (givenNameList == null) {
            givenNameList = new ArrayList<String>();
        }
        return givenNameList.get(index);
    }

    /** 
     * Remove all 'givenName' element items.
     */
    public void clearGivenNameList() {
        if (givenNameList == null) {
            givenNameList = new ArrayList<String>();
        }
        givenNameList.clear();
    }

    /** 
     * Get the 'familyName' element value. The family name of the *Person*.
     * 
     * @return value
     */
    public String getFamilyName() {
        return familyName;
    }

    /** 
     * Set the 'familyName' element value. The family name of the *Person*.
     * 
     * @param familyName
     */
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /** 
     * Get the list of 'email' element items. The email address of the *Person*, repeatable if
            they have more than one email address. 
     * 
     * @return list
     */
    public List<String> getEmailList() {
        return emailList;
    }

    /** 
     * Set the list of 'email' element items. The email address of the *Person*, repeatable if
            they have more than one email address. 
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
        if (emailList == null) {
            emailList = new ArrayList<String>();
        }
        return emailList.size();
    }

    /** 
     * Add a 'email' element item.
     * @param item
     */
    public void addEmail(String item) {
        if (emailList == null) {
            emailList = new ArrayList<String>();
        }
        emailList.add(item);
    }

    /** 
     * Get 'email' element item by position.
     * @return item
     * @param index
     */
    public String getEmail(int index) {
        if (emailList == null) {
            emailList = new ArrayList<String>();
        }
        return emailList.get(index);
    }

    /** 
     * Remove all 'email' element items.
     */
    public void clearEmailList() {
        if (emailList == null) {
            emailList = new ArrayList<String>();
        }
        emailList.clear();
    }

    /** 
     * Get the list of 'isMemberOf' element items. A group or role in which the *Person* is a member,
            expressed using the unique :class:`Types.Subject` identifier for
            that :class:`Types.Group`, and repeatable if they are a member of
            more than one group. 
     * 
     * @return list
     */
    public List<Subject> getIsMemberOfList() {
        return isMemberOfList;
    }

    /** 
     * Set the list of 'isMemberOf' element items. A group or role in which the *Person* is a member,
            expressed using the unique :class:`Types.Subject` identifier for
            that :class:`Types.Group`, and repeatable if they are a member of
            more than one group. 
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
        if (isMemberOfList == null) {
            isMemberOfList = new ArrayList<Subject>();
        }
        return isMemberOfList.size();
    }

    /** 
     * Add a 'isMemberOf' element item.
     * @param item
     */
    public void addIsMemberOf(Subject item) {
        if (isMemberOfList == null) {
            isMemberOfList = new ArrayList<Subject>();
        }
        isMemberOfList.add(item);
    }

    /** 
     * Get 'isMemberOf' element item by position.
     * @return item
     * @param index
     */
    public Subject getIsMemberOf(int index) {
        if (isMemberOfList == null) {
            isMemberOfList = new ArrayList<Subject>();
        }
        return isMemberOfList.get(index);
    }

    /** 
     * Remove all 'isMemberOf' element items.
     */
    public void clearIsMemberOfList() {
        if (isMemberOfList == null) {
            isMemberOfList = new ArrayList<Subject>();
        }
        isMemberOfList.clear();
    }

    /** 
     * Get the list of 'equivalentIdentity' element items. An alternative but equivalent identity for the
            :term:`principal` that has been used in alternate identity systems,
            repeatable if more than one equivalent identity applies.
            
     * 
     * @return list
     */
    public List<Subject> getEquivalentIdentityList() {
        return equivalentIdentityList;
    }

    /** 
     * Set the list of 'equivalentIdentity' element items. An alternative but equivalent identity for the
            :term:`principal` that has been used in alternate identity systems,
            repeatable if more than one equivalent identity applies.
            
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
        if (equivalentIdentityList == null) {
            equivalentIdentityList = new ArrayList<Subject>();
        }
        return equivalentIdentityList.size();
    }

    /** 
     * Add a 'equivalentIdentity' element item.
     * @param item
     */
    public void addEquivalentIdentity(Subject item) {
        if (equivalentIdentityList == null) {
            equivalentIdentityList = new ArrayList<Subject>();
        }
        equivalentIdentityList.add(item);
    }

    /** 
     * Get 'equivalentIdentity' element item by position.
     * @return item
     * @param index
     */
    public Subject getEquivalentIdentity(int index) {
        if (equivalentIdentityList == null) {
            equivalentIdentityList = new ArrayList<Subject>();
        }
        return equivalentIdentityList.get(index);
    }

    /** 
     * Remove all 'equivalentIdentity' element items.
     */
    public void clearEquivalentIdentityList() {
        if (equivalentIdentityList == null) {
            equivalentIdentityList = new ArrayList<Subject>();
        }
        equivalentIdentityList.clear();
    }

    /** 
     * Get the 'verified' element value. *true* if the name and email address of the
     *Person* have been :term:`verified` to ensure that the *givenName*
            and *familyName* represent the real person's legal name, and that
            the email address is correct for that person and is in the control
            of the indicated individual. Verification occurs through a
            established procedure within DataONE as part of the Identity
            Management system. A Person can not change their own *verified*
            field, but rather must be verified and changed through this DataONE
            established process. 
     * 
     * @return value
     */
    public Boolean getVerified() {
        return verified;
    }

    /** 
     * Set the 'verified' element value. *true* if the name and email address of the
     *Person* have been :term:`verified` to ensure that the *givenName*
            and *familyName* represent the real person's legal name, and that
            the email address is correct for that person and is in the control
            of the indicated individual. Verification occurs through a
            established procedure within DataONE as part of the Identity
            Management system. A Person can not change their own *verified*
            field, but rather must be verified and changed through this DataONE
            established process. 
     * 
     * @param verified
     */
    public void setVerified(Boolean verified) {
        this.verified = verified;
    }
}
