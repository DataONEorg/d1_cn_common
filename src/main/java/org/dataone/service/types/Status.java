
package org.dataone.service.types;

import java.util.Date;

/** 
 * Store last time checked information from the Mn_Health.status method
 Other information from status will be stored in the Service Node
 A process should check  MN_health.getStatus()  periodically and  
 update the version and availability  for each service.

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/0.6.2" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Status">
 *   &lt;xs:attribute type="xs:boolean" name="success"/>
 *   &lt;xs:attribute type="xs:dateTime" use="required" name="dateChecked"/>
 * &lt;/xs:complexType>
 * </pre>
 */
public class Status
{
    private Boolean success;
    private Date dateChecked;

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
     * Get the 'dateChecked' attribute value.
     * 
     * @return value
     */
    public Date getDateChecked() {
        return dateChecked;
    }

    /** 
     * Set the 'dateChecked' attribute value.
     * 
     * @param dateChecked
     */
    public void setDateChecked(Date dateChecked) {
        this.dateChecked = dateChecked;
    }
}
