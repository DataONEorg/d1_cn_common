
package org.dataone.service.types.v1;

import java.io.Serializable;
import java.util.Date;

/** 
 * Configuration information for the process by which
 metadata is harvested from Member Nodes to Coordinating Nodes, including
 the schedule on which harvesting should occur, and information about the
 last :term:`synchronization` attempts for the node. Member Nodes
 providing *Synchronization* information only need to provide the
 *schedule*. Coordinating Nodes must set values for the *lastHarvested*
 and *lastCompleteHarvest* fields.
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Synchronization">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:Schedule" name="schedule" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:dateTime" name="lastHarvested" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:dateTime" name="lastCompleteHarvest" minOccurs="0" maxOccurs="1"/>
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
     * Get the 'schedule' element value. An entry set by the Member Node indicating the
            frequency for which synchronization should occur. This setting will
            be influenced by the frequency with which content is updated on the
            Member Node and the acceptable latency for detection and subsequent
            processing of new content.
     * 
     * @return value
     */
    public Schedule getSchedule() {
        return schedule;
    }

    /** 
     * Set the 'schedule' element value. An entry set by the Member Node indicating the
            frequency for which synchronization should occur. This setting will
            be influenced by the frequency with which content is updated on the
            Member Node and the acceptable latency for detection and subsequent
            processing of new content.
     * 
     * @param schedule
     */
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    /** 
     * Get the 'lastHarvested' element value. The most recent modification date (UTC) of objects
            checked during the last harvest of the node.
     * 
     * @return value
     */
    public Date getLastHarvested() {
        return lastHarvested;
    }

    /** 
     * Set the 'lastHarvested' element value. The most recent modification date (UTC) of objects
            checked during the last harvest of the node.
     * 
     * @param lastHarvested
     */
    public void setLastHarvested(Date lastHarvested) {
        this.lastHarvested = lastHarvested;
    }

    /** 
     * Get the 'lastCompleteHarvest' element value. The last time (UTC) all the data from a node was
            pulled from a member node during a complete synchronization
            process.
     * 
     * @return value
     */
    public Date getLastCompleteHarvest() {
        return lastCompleteHarvest;
    }

    /** 
     * Set the 'lastCompleteHarvest' element value. The last time (UTC) all the data from a node was
            pulled from a member node during a complete synchronization
            process.
     * 
     * @param lastCompleteHarvest
     */
    public void setLastCompleteHarvest(Date lastCompleteHarvest) {
        this.lastCompleteHarvest = lastCompleteHarvest;
    }
}
