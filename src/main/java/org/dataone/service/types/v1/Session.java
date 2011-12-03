
package org.dataone.service.types.v1;

import java.io.Serializable;

/** 
 * 
 Information about the authenticated session for a service transaction.  Session data
 is retrieved from the SSL client certificate and populated in the Session object.  The
 subject represents the person or system that authenticated successfully, and the subjectInfo
 contains a listing of alternate identities (both Persons and Groups) that are also valid identities
 for this user.  The subjectInfo should include at least one Person or Group entry that provides
 the attributes of the subject that was authenticated. 

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Session">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:Subject" name="subject" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="ns:SubjectInfo" name="subjectInfo" minOccurs="0" maxOccurs="1"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class Session implements Serializable
{
    private static final long serialVersionUID = 10000000;
    private Subject subject;
    private SubjectInfo subjectInfo;

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
     * Get the 'subjectInfo' element value.
     * 
     * @return value
     */
    public SubjectInfo getSubjectInfo() {
        return subjectInfo;
    }

    /** 
     * Set the 'subjectInfo' element value.
     * 
     * @param subjectInfo
     */
    public void setSubjectInfo(SubjectInfo subjectInfo) {
        this.subjectInfo = subjectInfo;
    }
}
