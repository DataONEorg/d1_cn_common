
package org.dataone.service.types;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://dataone.org/service/types/NodeRegistry/0.1" xmlns:ns1="http://dataone.org/service/types/common/0.1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Node">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns1:Identifier" name="identifier" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="ns1:NodeReference" name="name"/>
 *     &lt;xs:element type="xs:string" name="baseURL"/>
 *     &lt;xs:element type="ns:Services" name="services" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="ns:Synchronization" name="synchronization" minOccurs="0" maxOccurs="1"/>
 *   &lt;/xs:sequence>
 *   &lt;xs:attribute type="xs:boolean" use="required" name="replicate"/>
 *   &lt;xs:attribute type="xs:boolean" use="required" name="synchronize"/>
 *   &lt;xs:attribute type="xs:string" use="required" name="type"/>
 * &lt;/xs:complexType>
 * </pre>
 */
public class Node
{
    private Identifier identifier;
    private NodeReference name;
    private String baseURL;
    private Services services;
    private Synchronization synchronization;
    private boolean replicate;
    private boolean synchronize;
    private String type;

    /** 
     * Get the 'identifier' element value.
     * 
     * @return value
     */
    public Identifier getIdentifier() {
        return identifier;
    }

    /** 
     * Set the 'identifier' element value.
     * 
     * @param identifier
     */
    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    /** 
     * Get the 'name' element value.
     * 
     * @return value
     */
    public NodeReference getName() {
        return name;
    }

    /** 
     * Set the 'name' element value.
     * 
     * @param name
     */
    public void setName(NodeReference name) {
        this.name = name;
    }

    /** 
     * Get the 'baseURL' element value.
     * 
     * @return value
     */
    public String getBaseURL() {
        return baseURL;
    }

    /** 
     * Set the 'baseURL' element value.
     * 
     * @param baseURL
     */
    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    /** 
     * Get the 'services' element value.
     * 
     * @return value
     */
    public Services getServices() {
        return services;
    }

    /** 
     * Set the 'services' element value.
     * 
     * @param services
     */
    public void setServices(Services services) {
        this.services = services;
    }

    /** 
     * Get the 'synchronization' element value.
     * 
     * @return value
     */
    public Synchronization getSynchronization() {
        return synchronization;
    }

    /** 
     * Set the 'synchronization' element value.
     * 
     * @param synchronization
     */
    public void setSynchronization(Synchronization synchronization) {
        this.synchronization = synchronization;
    }

    /** 
     * Get the 'replicate' attribute value.
     * 
     * @return value
     */
    public boolean isReplicate() {
        return replicate;
    }

    /** 
     * Set the 'replicate' attribute value.
     * 
     * @param replicate
     */
    public void setReplicate(boolean replicate) {
        this.replicate = replicate;
    }

    /** 
     * Get the 'synchronize' attribute value.
     * 
     * @return value
     */
    public boolean isSynchronize() {
        return synchronize;
    }

    /** 
     * Set the 'synchronize' attribute value.
     * 
     * @param synchronize
     */
    public void setSynchronize(boolean synchronize) {
        this.synchronize = synchronize;
    }

    /** 
     * Get the 'type' attribute value.
     * 
     * @return value
     */
    public String getType() {
        return type;
    }

    /** 
     * Set the 'type' attribute value.
     * 
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }
}
