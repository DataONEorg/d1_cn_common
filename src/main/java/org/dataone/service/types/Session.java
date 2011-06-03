
package org.dataone.service.types;

/** 
 * Information about the authenticated session for a service transaction.  Session data
 is retrieved from the SSL client certificate and populated in the Session object.  The
 subject represents the person or system that authenticated successfully, and the subjectList
 contains a listing of alternate identities (both Persons and Groups) that are also valid identities
 for this user.  The subjectList should include at least one Person or Group entry that provides
 the attributes of the subject that was authenticated. 

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/0.6.2" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Session">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:Subject" name="subject" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="ns:SubjectList" name="subjectList" minOccurs="0" maxOccurs="1"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class Session
{
    private Subject subject;
    private SubjectList subjectList;

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
     * Get the 'subjectList' element value.
     * 
     * @return value
     */
    public SubjectList getSubjectList() {
        return subjectList;
    }

    /** 
     * Set the 'subjectList' element value.
     * 
     * @param subjectList
     */
    public void setSubjectList(SubjectList subjectList) {
        this.subjectList = subjectList;
    }
}
