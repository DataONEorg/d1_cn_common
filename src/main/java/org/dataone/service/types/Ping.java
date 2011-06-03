
package org.dataone.service.types;

import java.util.Date;

/** 
 * Store results from the Mn_Health.ping method

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/0.6.2" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Ping">
 *   &lt;xs:attribute type="xs:boolean" name="success"/>
 *   &lt;xs:attribute type="xs:dateTime" name="lastSuccess"/>
 * &lt;/xs:complexType>
 * </pre>
 */
public class Ping
{
    private Boolean success;
    private Date lastSuccess;

    /** 
     * Get the 'success' attribute value.
     * 
     * @return value
     */
    public Boolean getSuccess() {
        return success;
    }

    /** 
     * Set the 'success' attribute value.
     * 
     * @param success
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /** 
     * Get the 'lastSuccess' attribute value.
     * 
     * @return value
     */
    public Date getLastSuccess() {
        return lastSuccess;
    }

    /** 
     * Set the 'lastSuccess' attribute value.
     * 
     * @param lastSuccess
     */
    public void setLastSuccess(Date lastSuccess) {
        this.lastSuccess = lastSuccess;
    }
}
