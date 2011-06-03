
package org.dataone.service.types;

/** 
 * Describes individual methods and their rest paths from the version of the API. 
 Rest paths are relative to the baseURL, and determine which method has been implemented by
 hitting the url. For an MN, calling an un-implemented method of an API version will raise
 NotImplemented.

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/0.6.2" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="ServiceMethod">
 *   &lt;xs:attribute type="xs:string" name="name"/>
 *   &lt;xs:attribute type="xs:string" use="required" name="rest"/>
 *   &lt;xs:attribute type="xs:boolean" use="required" name="implemented"/>
 * &lt;/xs:complexType>
 * </pre>
 */
public class ServiceMethod
{
    private String name;
    private String rest;
    private boolean implemented;

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

    /** 
     * Get the 'implemented' attribute value.
     * 
     * @return value
     */
    public boolean isImplemented() {
        return implemented;
    }

    /** 
     * Set the 'implemented' attribute value.
     * 
     * @param implemented
     */
    public void setImplemented(boolean implemented) {
        this.implemented = implemented;
    }
}
