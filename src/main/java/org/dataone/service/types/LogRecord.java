/**
 * Copyright 2010 Regents of the University of California and the
 *                National Center for Ecological Analysis and Synthesis
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.dataone.service.types;

import java.util.Date;

/**
 * The DataONE Type to represent a logging record from a CRUD operation.
 *
 * @author Matthew Jones
 */
public class LogRecord 
{
    private Identifier entryId;
    private Identifier identifier;
    private String ipAddress;
    private String userAgent;
    private Principal principal;
    private Event event;
    private Date logDate;
    private NodeReference memberNode;
    
    public LogRecord() {
    }
    
    /**
     * @return the entryId
     */
    public Identifier getEntryId() {
        return entryId;
    }
    /**
     * @return the identifier
     */
    public Identifier getIdentifier() {
        return identifier;
    }
    /**
     * @return the ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }
    /**
     * @return the userAgent
     */
    public String getUserAgent() {
        return userAgent;
    }
    /**
     * @return the principal
     */
    public Principal getPrincipal() {
        return principal;
    }
    /**
     * @return the event
     */
    public Event getEvent() {
        return event;
    }
    /**
     * @return the logDate
     */
    public Date getLogDate() {
        return logDate;
    }
    /**
     * @return the memberNode
     */
    public NodeReference getMemberNode() {
        return memberNode;
    }
    /**
     * @param entryId the entryId to set
     */
    public void setEntryId(Identifier entryId) {
        this.entryId = entryId;
    }
    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }
    /**
     * @param ipAddress the ipAddress to set
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    /**
     * @param userAgent the userAgent to set
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    /**
     * @param principal the principal to set
     */
    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }
    /**
     * @param event the event to set
     */
    public void setEvent(Event event) {
        this.event = event;
    }
    /**
     * @param logDate the logDate to set
     */
    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }
    /**
     * @param memberNode the memberNode to set
     */
    public void setMemberNode(NodeReference memberNode) {
        this.memberNode = memberNode;
    }
}
