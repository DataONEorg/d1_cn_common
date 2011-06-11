
package org.dataone.service.types;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/0.6.2" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="SuccessFlag">
 *   &lt;xs:simpleContent>
 *     &lt;xs:extension base="xs:boolean"/>
 *   &lt;/xs:simpleContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class SuccessFlag
{
    private boolean _boolean;

    /** 
     * Get the 'SuccessFlag' complexType value.
     * 
     * @return value
     */
    public boolean isBoolean() {
        return _boolean;
    }

    /** 
     * Set the 'SuccessFlag' complexType value.
     * 
     * @param _boolean
     */
    public void setBoolean(boolean _boolean) {
        this._boolean = _boolean;
    }
}
