
package org.dataone.service.types.v1;

import java.io.Serializable;
import java.util.Date;

/** 
 * Configuration information for the process by which data is harvested from membernodes
 to Coordinating Nodes, including the schedule on which harvesting should occur, and metadata about
 the last synchronization attempts for the node.  Clients providing Synchronization
 information only need to provide the schedule, but must provide placeholder values that will be overridden 
 by the Coordinating Nodes for the lastHarvested and lastCompleteHarvest fields.

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Synchronization">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:Schedule" name="schedule" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:dateTime" name="lastHarvested" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:dateTime" name="lastCompleteHarvest" minOccurs="1" maxOccurs="1"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class Synchronization implements Serializable
{
    private static final long serialVersionUID = 10000000;
    private Schedule schedule;
    private Date lastHarvested;
    private Date lastCompleteHarvest;

    /** 
     * Get the 'schedule' element value.
     * 
     * @return value
     */
    public Schedule getSchedule() {
        return schedule;
    }

    /** 
     * Set the 'schedule' element value.
     * 
     * @param schedule
     */
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    /** 
     * Get the 'lastHarvested' element value. The most recent modification date of objects checked during the last harvest of the node.
                      
     * 
     * @return value
     */
    public Date getLastHarvested() {
        return lastHarvested;
    }

    /** 
     * Set the 'lastHarvested' element value. The most recent modification date of objects checked during the last harvest of the node.
                      
     * 
     * @param lastHarvested
     */
    public void setLastHarvested(Date lastHarvested) {
        this.lastHarvested = lastHarvested;
    }

    /** 
     * Get the 'lastCompleteHarvest' element value. The last time all the data from a node was pulled from a member node. 
                      
     * 
     * @return value
     */
    public Date getLastCompleteHarvest() {
        return lastCompleteHarvest;
    }

    /** 
     * Set the 'lastCompleteHarvest' element value. The last time all the data from a node was pulled from a member node. 
                      
     * 
     * @param lastCompleteHarvest
     */
    public void setLastCompleteHarvest(Date lastCompleteHarvest) {
        this.lastCompleteHarvest = lastCompleteHarvest;
    }
}
