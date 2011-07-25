
package org.dataone.service.types.v1;

import java.io.Serializable;

/** 
 * Describes the restriction policy for a given method.
 Only subjects listed in the allowed list are allowed to invoke the method.

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="ServiceMethodRestriction">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:SubjectList" name="allowed" minOccurs="1" maxOccurs="1"/>
 *   &lt;/xs:sequence>
 *   &lt;xs:attribute type="xs:string" name="name"/>
 *   &lt;xs:attribute type="xs:string" use="required" name="rest"/>
 * &lt;/xs:complexType>
 * </pre>
 */
public class ServiceMethodRestriction implements Serializable
{
    private SubjectList allowed;
    private String name;
    private String rest;

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
     * Get the 'name' attribute value.
     * 
     * @return value
     */
    public String getName() {
        return name;
    }

    /** 
     * Set the 'name' attribute value.
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** 
     * Get the 'rest' attribute value.
     * 
     * @return value
     */
    public String getRest() {
        return rest;
    }

    /** 
     * Set the 'rest' attribute value.
     * 
     * @param rest
     */
    public void setRest(String rest) {
        this.rest = rest;
    }
}
