
package org.dataone.service.types.v1;

import java.io.Serializable;

/** 
 * Describes an optional restriction policy for a given method.
 If this element exists for a service method, its use is restricted.
 Only subjects listed in the allowed list are allowed to invoke the method.

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="ServiceMethodRestriction">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:SubjectList" name="allowed" minOccurs="0" maxOccurs="1"/>
 *   &lt;/xs:sequence>
 *   &lt;xs:attribute type="xs:string" use="required" name="methodName"/>
 * &lt;/xs:complexType>
 * </pre>
 */
public class ServiceMethodRestriction implements Serializable
{
    private static final long serialVersionUID = 10000000;
    private SubjectList allowed;
    private String methodName;

    /** 
     * Get the 'allowed' element value.
     * 
     * @return value
     */
    public SubjectList getAllowed() {
        return allowed;
    }

    /** 
     * Set the 'allowed' element value.
     * 
     * @param allowed
     */
    public void setAllowed(SubjectList allowed) {
        this.allowed = allowed;
    }

    /** 
     * Get the 'methodName' attribute value.
     * 
     * @return value
     */
    public String getMethodName() {
        return methodName;
    }

    /** 
     * Set the 'methodName' attribute value.
     * 
     * @param methodName
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
