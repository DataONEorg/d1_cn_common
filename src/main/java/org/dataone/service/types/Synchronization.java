
package org.dataone.service.types;

import java.util.Date;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://dataone.org/service/types/NodeList/0.1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Synchronization">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:Schedule" name="schedule"/>
 *     &lt;xs:element type="xs:dateTime" name="lastHarvested"/>
 *     &lt;xs:element type="xs:dateTime" name="lastCompleteHarvest"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class Synchronization
{
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
     * Get the 'lastHarvested' element value.
     * 
     * @return value
     */
    public Date getLastHarvested() {
        return lastHarvested;
    }

    /** 
     * Set the 'lastHarvested' element value.
     * 
     * @param lastHarvested
     */
    public void setLastHarvested(Date lastHarvested) {
        this.lastHarvested = lastHarvested;
    }

    /** 
     * Get the 'lastCompleteHarvest' element value.
     * 
     * @return value
     */
    public Date getLastCompleteHarvest() {
        return lastCompleteHarvest;
    }

    /** 
     * Set the 'lastCompleteHarvest' element value.
     * 
     * @param lastCompleteHarvest
     */
    public void setLastCompleteHarvest(Date lastCompleteHarvest) {
        this.lastCompleteHarvest = lastCompleteHarvest;
    }
}
