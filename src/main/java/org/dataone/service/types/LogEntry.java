
package org.dataone.service.types;

import java.util.Date;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://dataone.org/service/types/common/0.5" xmlns:ns1="http://dataone.org/service/types/logging/0.5" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="LogEntry">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:Identifier" name="entryId" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="ns:Identifier" name="identifier" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="ipAddress" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="userAgent" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="ns:Principal" name="principal" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="ns1:Event" name="event" minOccurs="1" maxOccurs="1"/>
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
    private Principal principal;
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
