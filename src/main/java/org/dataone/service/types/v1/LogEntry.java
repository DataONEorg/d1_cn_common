
package org.dataone.service.types.v1;

import java.io.Serializable;
import java.util.Date;

/** 
 * A single log entry as reported by a Member Node or Coordinating Node through
 the :func:`MNCore.getLogRecords` and :func:`CNCore.getLogRecords` methods.
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="LogEntry">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="xs:string" name="entryId" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="ns:Identifier" name="identifier" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="ipAddress" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="userAgent" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="ns:Subject" name="subject" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="ns:Event" name="event" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:dateTime" name="dateLogged" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="ns:NodeReference" name="nodeIdentifier" minOccurs="1" maxOccurs="1"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class LogEntry implements Serializable
{
    private static final long serialVersionUID = 10000000;
    private String entryId;
    private Identifier identifier;
    private String ipAddress;
    private String userAgent;
    private Subject subject;
    private Event event;
    private Date dateLogged;
    private NodeReference nodeIdentifier;

    /** 
     * Get the 'entryId' element value.
     * 
     * @return value
     */
    public String getEntryId() {
        return entryId;
    }

    /** 
     * Set the 'entryId' element value.
     * 
     * @param entryId
     */
    public void setEntryId(String entryId) {
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
     * Get the 'nodeIdentifier' element value.
     * 
     * @return value
     */
    public NodeReference getNodeIdentifier() {
        return nodeIdentifier;
    }

    /** 
     * Set the 'nodeIdentifier' element value.
     * 
     * @param nodeIdentifier
     */
    public void setNodeIdentifier(NodeReference nodeIdentifier) {
        this.nodeIdentifier = nodeIdentifier;
    }
}
