
package org.dataone.service.types;

import java.util.Date;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/0.6.2" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="LogEntry">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:Identifier" name="entryId" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="ns:Identifier" name="identifier" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="ipAddress" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="userAgent" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="ns:Subject" name="subject" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="ns:Event" name="event" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:dateTime" name="dateLogged" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="ns:NodeReference" name="memberNode" minOccurs="1" maxOccurs="1"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class LogEntry
{
    private Identifier entryId;
    private Identifier identifier;
    private String ipAddress;
    private String userAgent;
    private Subject subject;
    private Event event;
    private Date dateLogged;
    private NodeReference memberNode;

    /** 
     * Get the 'entryId' element value.
     * 
     * @return value
     */
    public Identifier getEntryId() {
        return entryId;
    }

    /** 
     * Set the 'entryId' element value.
     * 
     * @param entryId
     */
    public void setEntryId(Identifier entryId) {
        this.entryId = entryId;
    }

    /** 
     * Get the 'identifier' element value.
     * 
     * @return value
     */
    public Identifier getIdentifier() {
        return identifier;
    }

    /** 
     * Set the 'identifier' element value.
     * 
     * @param identifier
     */
    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    /** 
     * Get the 'ipAddress' element value.
     * 
     * @return value
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /** 
     * Set the 'ipAddress' element value.
     * 
     * @param ipAddress
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /** 
     * Get the 'userAgent' element value.
     * 
     * @return value
     */
    public String getUserAgent() {
        return userAgent;
    }

    /** 
     * Set the 'userAgent' element value.
     * 
     * @param userAgent
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

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
     * Get the 'event' element value.
     * 
     * @return value
     */
    public Event getEvent() {
        return event;
    }

    /** 
     * Set the 'event' element value.
     * 
     * @param event
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /** 
     * Get the 'dateLogged' element value.
     * 
     * @return value
     */
    public Date getDateLogged() {
        return dateLogged;
    }

    /** 
     * Set the 'dateLogged' element value.
     * 
     * @param dateLogged
     */
    public void setDateLogged(Date dateLogged) {
        this.dateLogged = dateLogged;
    }

    /** 
     * Get the 'memberNode' element value.
     * 
     * @return value
     */
    public NodeReference getMemberNode() {
        return memberNode;
    }

    /** 
     * Set the 'memberNode' element value.
     * 
     * @param memberNode
     */
    public void setMemberNode(NodeReference memberNode) {
        this.memberNode = memberNode;
    }
}
