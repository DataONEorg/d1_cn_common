
package org.dataone.service.types;

/** 
 * Store last time checked information from the Mn_Health.status method
 Other information from status will be stored in the Service Node
 A process should check  MN_health.getStatus()  periodically and  
 update the version and availability  for each service.

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/0.6.2" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="SuccessFlag">
 *   &lt;xs:attribute type="xs:boolean" name="value"/>
 * &lt;/xs:complexType>
 * </pre>
 */
public class SuccessFlag
{
    private Boolean value;

    /** 
     * Get the 'value' attribute value.
     * 
     * @return value
     */
    public Boolean getValue() {
        return value;
    }

    /** 
     * Set the 'value' attribute value.
     * 
     * @param value
     */
    public void setValue(Boolean value) {
        this.value = value;
    }
}
