
package org.dataone.service.types;

/** 
 * Name and version of a DataONE software stack component.

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/0.6.2" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Component">
 *   &lt;xs:attribute type="ns:ComponentName" use="required" name="name"/>
 *   &lt;xs:attribute type="xs:string" use="required" name="version"/>
 * &lt;/xs:complexType>
 * </pre>
 */
public class Component
{
    private ComponentName name;
    private String version;

    /** 
     * Get the 'name' attribute value.
     * 
     * @return value
     */
    public ComponentName getName() {
        return name;
    }

    /** 
     * Set the 'name' attribute value.
     * 
     * @param name
     */
    public void setName(ComponentName name) {
        this.name = name;
    }

    /** 
     * Get the 'version' attribute value.
     * 
     * @return value
     */
    public String getVersion() {
        return version;
    }

    /** 
     * Set the 'version' attribute value.
     * 
     * @param version
     */
    public void setVersion(String version) {
        this.version = version;
    }
}
