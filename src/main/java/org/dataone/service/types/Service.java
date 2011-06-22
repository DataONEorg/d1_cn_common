
package org.dataone.service.types;

/** 
 * Name and version of a DataONE software stack component are
 equivalent to the statusresponselist.xsd name and version. 
 (-rpw notes from meeting 9/25/2010, but Component Name and Version is different than
 Service API name and version!)
 A process should check  MN_health.getStatus()  periodically and  
 update the version, availability and dateChecked for each service.
 May need to update method definitions at same time

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/0.6.2" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Service">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="xs:string" name="name" minOccurs="1" maxOccurs="1"/>
 *   &lt;/xs:sequence>
 *   &lt;xs:attribute type="xs:string" use="required" name="version"/>
 *   &lt;xs:attribute type="xs:boolean" name="available"/>
 * &lt;/xs:complexType>
 * </pre>
 */
public class Service
{
    private String name;
    private String version;
    private Boolean available;

    /** 
     * Get the 'name' element value.
     * 
     * @return value
     */
    public String getName() {
        return name;
    }

    /** 
     * Set the 'name' element value.
     * 
     * @param name
     */
    public void setName(String name) {
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

    /** 
     * Get the 'available' attribute value.
     * 
     * @return value
     */
    public Boolean getAvailable() {
        return available;
    }

    /** 
     * Set the 'available' attribute value.
     * 
     * @param available
     */
    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
