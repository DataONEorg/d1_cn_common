
package org.dataone.service.types;

import java.util.Date;

/** 
 * The schedule on which MnSynchronization will run for a particular run

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://dataone.org/service/types/0.5.1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="NodeHealth">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:Ping" name="ping"/>
 *     &lt;xs:element type="ns:Status" name="status"/>
 *   &lt;/xs:sequence>
 *   &lt;xs:attribute type="ns:NodeState" use="required" name="state"/>
 * &lt;/xs:complexType>
 * 
 * &lt;xs:complexType xmlns:ns="http://dataone.org/service/types/0.5.1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Ping">
 *   &lt;xs:attribute type="xs:boolean" name="success"/>
 *   &lt;xs:attribute type="xs:dateTime" name="lastSuccess"/>
 * &lt;/xs:complexType>
 * 
 * &lt;xs:complexType xmlns:ns="http://dataone.org/service/types/0.5.1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Status">
 *   &lt;xs:attribute type="xs:boolean" name="success"/>
 *   &lt;xs:attribute type="xs:dateTime" use="required" name="dateChecked"/>
 * &lt;/xs:complexType>
 * </pre>
 */
public class NodeHealth
{
    private Boolean pingSuccess;
    private Date pingLastSuccess;
    private Boolean statusSuccess;
    private Date statusDateChecked;
    private NodeState state;

    /** 
     * Get the 'success' attribute value.
     * 
     * @return value
     */
    public Boolean getPingSuccess() {
        return pingSuccess;
    }

    /** 
     * Set the 'success' attribute value.
     * 
     * @param pingSuccess
     */
    public void setPingSuccess(Boolean pingSuccess) {
        this.pingSuccess = pingSuccess;
    }

    /** 
     * Get the 'lastSuccess' attribute value.
     * 
     * @return value
     */
    public Date getPingLastSuccess() {
        return pingLastSuccess;
    }

    /** 
     * Set the 'lastSuccess' attribute value.
     * 
     * @param pingLastSuccess
     */
    public void setPingLastSuccess(Date pingLastSuccess) {
        this.pingLastSuccess = pingLastSuccess;
    }

    /** 
     * Get the 'success' attribute value.
     * 
     * @return value
     */
    public Boolean getStatusSuccess() {
        return statusSuccess;
    }

    /** 
     * Set the 'success' attribute value.
     * 
     * @param statusSuccess
     */
    public void setStatusSuccess(Boolean statusSuccess) {
        this.statusSuccess = statusSuccess;
    }

    /** 
     * Get the 'dateChecked' attribute value.
     * 
     * @return value
     */
    public Date getStatusDateChecked() {
        return statusDateChecked;
    }

    /** 
     * Set the 'dateChecked' attribute value.
     * 
     * @param statusDateChecked
     */
    public void setStatusDateChecked(Date statusDateChecked) {
        this.statusDateChecked = statusDateChecked;
    }

    /** 
     * Get the 'state' attribute value.
     * 
     * @return value
     */
    public NodeState getState() {
        return state;
    }

    /** 
     * Set the 'state' attribute value.
     * 
     * @param state
     */
    public void setState(NodeState state) {
        this.state = state;
    }
}
