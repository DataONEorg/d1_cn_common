
package org.dataone.service.types;

import java.util.Date;

/** 
 * The process by which data is pulled from membernodes down
 to the coordinating node

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/0.6.2" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Synchronization">
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
     * Get the 'lastHarvested' element value. The last time the mn sychronization daemon ran and found new data to synchronize
                      
     * 
     * @return value
     */
    public Date getLastHarvested() {
        return lastHarvested;
    }

    /** 
     * Set the 'lastHarvested' element value. The last time the mn sychronization daemon ran and found new data to synchronize
                      
     * 
     * @param lastHarvested
     */
    public void setLastHarvested(Date lastHarvested) {
        this.lastHarvested = lastHarvested;
    }

    /** 
     * Get the 'lastCompleteHarvest' element value. The last time all the data from a node was pulled from a member node
                      
     * 
     * @return value
     */
    public Date getLastCompleteHarvest() {
        return lastCompleteHarvest;
    }

    /** 
     * Set the 'lastCompleteHarvest' element value. The last time all the data from a node was pulled from a member node
                      
     * 
     * @param lastCompleteHarvest
     */
    public void setLastCompleteHarvest(Date lastCompleteHarvest) {
        this.lastCompleteHarvest = lastCompleteHarvest;
    }
}
