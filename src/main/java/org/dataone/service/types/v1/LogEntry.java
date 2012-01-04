
package org.dataone.service.types.v1;

import java.io.Serializable;
import java.util.Date;

/** 
 * A single log entry as reported by a Member Node or
 Coordinating Node through the :func:`MNCore.getLogRecords` and
 :func:`CNCore.getLogRecords` methods.
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
     * Get the 'entryId' element value. A unique identifier for this log entry. The
            identifier should be unique for a particular node; This is not drawn
            from the same value space as other identifiers in DataONE, and so is
            not subjec to the same restrictions.
     * 
     * @return value
     */
    public String getEntryId() {
        return entryId;
    }

    /** 
     * Set the 'entryId' element value. A unique identifier for this log entry. The
            identifier should be unique for a particular node; This is not drawn
            from the same value space as other identifiers in DataONE, and so is
            not subjec to the same restrictions.
     * 
     * @param entryId
     */
    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }

    /** 
     * Get the 'identifier' element value. The :term:`identifier` of the object that was the
            target of the operation which generated this log entry.
     * 
     * @return value
     */
    public Identifier getIdentifier() {
        return identifier;
    }

    /** 
     * Set the 'identifier' element value. The :term:`identifier` of the object that was the
            target of the operation which generated this log entry.
     * 
     * @param identifier
     */
    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    /** 
     * Get the 'ipAddress' element value. The IP address, as reported by the service receiving
            the request, of the request origin.
     * 
     * @return value
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /** 
     * Set the 'ipAddress' element value. The IP address, as reported by the service receiving
            the request, of the request origin.
     * 
     * @param ipAddress
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /** 
     * Get the 'userAgent' element value. The user agent of the client making the request, as
            reported in the User-Agent HTTP header.
     * 
     * @return value
     */
    public String getUserAgent() {
        return userAgent;
    }

    /** 
     * Set the 'userAgent' element value. The user agent of the client making the request, as
            reported in the User-Agent HTTP header.
     * 
     * @param userAgent
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /** 
     * Get the 'subject' element value. The :term:`Subject` used for making the request.
            This may be the DataONE *public* user if the request is not
            authenticated, otherwise it will be the Subject of the certificate
            used for authenticating the request.
     * 
     * @return value
     */
    public Subject getSubject() {
        return subject;
    }

    /** 
     * Set the 'subject' element value. The :term:`Subject` used for making the request.
            This may be the DataONE *public* user if the request is not
            authenticated, otherwise it will be the Subject of the certificate
            used for authenticating the request.
     * 
     * @param subject
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    /** 
     * Get the 'event' element value. An entry from the :class:`Types.Event` enumeration
            indicating the type of operation that triggered the log message.
     * 
     * @return value
     */
    public Event getEvent() {
        return event;
    }

    /** 
     * Set the 'event' element value. An entry from the :class:`Types.Event` enumeration
            indicating the type of operation that triggered the log message.
     * 
     * @param event
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /** 
     * Get the 'dateLogged' element value. A :class:`Types.DateTime` time stamp indicating when
            the event triggering the log message ocurred. Note that all time
            stamps in DataONE are in UTC.
     * 
     * @return value
     */
    public Date getDateLogged() {
        return dateLogged;
    }

    /** 
     * Set the 'dateLogged' element value. A :class:`Types.DateTime` time stamp indicating when
            the event triggering the log message ocurred. Note that all time
            stamps in DataONE are in UTC.
     * 
     * @param dateLogged
     */
    public void setDateLogged(Date dateLogged) {
        this.dateLogged = dateLogged;
    }

    /** 
     * Get the 'nodeIdentifier' element value. The unique identifier for the node where the log
            message was generated.
     * 
     * @return value
     */
    public NodeReference getNodeIdentifier() {
        return nodeIdentifier;
    }

    /** 
     * Set the 'nodeIdentifier' element value. The unique identifier for the node where the log
            message was generated.
     * 
     * @param nodeIdentifier
     */
    public void setNodeIdentifier(NodeReference nodeIdentifier) {
        this.nodeIdentifier = nodeIdentifier;
    }
}
