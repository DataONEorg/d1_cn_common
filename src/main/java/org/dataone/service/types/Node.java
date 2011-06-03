
package org.dataone.service.types;

/** 
 * A unique implementation of the member node or coordination node software
 stack. several nodes may exist on a single physical device or hostname 

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/0.6.2" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Node">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:NodeReference" name="identifier" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="name" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="description"/>
 *     &lt;xs:element type="xs:string" name="baseURL"/>
 *     &lt;xs:element type="ns:Services" name="services" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="ns:Synchronization" name="synchronization" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="ns:NodeHealth" name="health" minOccurs="0" maxOccurs="1"/>
 *   &lt;/xs:sequence>
 *   &lt;xs:attribute type="xs:boolean" use="required" name="replicate"/>
 *   &lt;xs:attribute type="xs:boolean" use="required" name="synchronize"/>
 *   &lt;xs:attribute type="ns:NodeType" use="required" name="type"/>
 *   &lt;xs:attribute type="ns:Environment" use="optional" name="environment"/>
 * &lt;/xs:complexType>
 * </pre>
 */
public class Node
{
    private NodeReference identifier;
    private String name;
    private String description;
    private String baseURL;
    private Services services;
    private Synchronization synchronization;
    private NodeHealth health;
    private boolean replicate;
    private boolean synchronize;
    private NodeType type;
    private Environment environment;

    /** 
     * Get the 'identifier' element value. A unique identifier for the node. This may initially be the same as the
                          baseURL, however this value should not change for future implementations of the same
                          node, whereas the baseURL may change in the future. 
                      
     * 
     * @return value
     */
    public NodeReference getIdentifier() {
        return identifier;
    }

    /** 
     * Set the 'identifier' element value. A unique identifier for the node. This may initially be the same as the
                          baseURL, however this value should not change for future implementations of the same
                          node, whereas the baseURL may change in the future. 
                      
     * 
     * @param identifier
     */
    public void setIdentifier(NodeReference identifier) {
        this.identifier = identifier;
    }

    /** 
     * Get the 'name' element value. A human readable name of the Node. 
                          The name of the node is being used in Mercury currently to assign a path,
                          so format should be consistent with dataone directory naming conventions
                      
     * 
     * @return value
     */
    public String getName() {
        return name;
    }

    /** 
     * Set the 'name' element value. A human readable name of the Node. 
                          The name of the node is being used in Mercury currently to assign a path,
                          so format should be consistent with dataone directory naming conventions
                      
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** 
     * Get the 'description' element value. Description of content maintained by this node and any other free style
                          notes. May be we should allow CDATA element with the purpose of using for display
                      
     * 
     * @return value
     */
    public String getDescription() {
        return description;
    }

    /** 
     * Set the 'description' element value. Description of content maintained by this node and any other free style
                          notes. May be we should allow CDATA element with the purpose of using for display
                      
     * 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
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
     * Get the 'health' element value. The name of the node is being used in Mercury currently to assign a
                          path, so format should be consistent with dataone directory naming conventions
                      
     * 
     * @return value
     */
    public NodeHealth getHealth() {
        return health;
    }

    /** 
     * Set the 'health' element value. The name of the node is being used in Mercury currently to assign a
                          path, so format should be consistent with dataone directory naming conventions
                      
     * 
     * @param health
     */
    public void setHealth(NodeHealth health) {
        this.health = health;
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
    public NodeType getType() {
        return type;
    }

    /** 
     * Set the 'type' attribute value.
     * 
     * @param type
     */
    public void setType(NodeType type) {
        this.type = type;
    }

    /** 
     * Get the 'environment' attribute value.
     * 
     * @return value
     */
    public Environment getEnvironment() {
        return environment;
    }

    /** 
     * Set the 'environment' attribute value.
     * 
     * @param environment
     */
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
