
package org.dataone.service.types;

/** 
 * The schedule on which MnSynchronization will run for a particular run

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/0.6.2" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="NodeHealth">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:Ping" name="ping"/>
 *     &lt;xs:element type="ns:Status" name="status"/>
 *   &lt;/xs:sequence>
 *   &lt;xs:attribute type="ns:NodeState" use="required" name="state"/>
 * &lt;/xs:complexType>
 * </pre>
 */
public class NodeHealth
{
    private Ping ping;
    private Status status;
    private NodeState state;

    /** 
     * Get the 'ping' element value.
     * 
     * @return value
     */
    public Ping getPing() {
        return ping;
    }

    /** 
     * Set the 'ping' element value.
     * 
     * @param ping
     */
    public void setPing(Ping ping) {
        this.ping = ping;
    }

    /** 
     * Get the 'status' element value.
     * 
     * @return value
     */
    public Status getStatus() {
        return status;
    }

    /** 
     * Set the 'status' element value.
     * 
     * @param status
     */
    public void setStatus(Status status) {
        this.status = status;
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
