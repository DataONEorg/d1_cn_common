
package org.dataone.service.types.v1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** 
 * A set of values that describe a member or coordinating node, its Internet location, and the services it
 supports. Several nodes may exist on a single physical device or hostname.

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Node">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:NodeReference" name="identifier" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="name" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="description" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="baseURL" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="ns:Services" name="services" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="ns:Synchronization" name="synchronization" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="ns:Ping" name="ping" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="ns:Subject" name="subject" minOccurs="0" maxOccurs="unbounded"/>
 *     &lt;xs:element type="ns:Subject" name="contactSubject" minOccurs="1" maxOccurs="unbounded"/>
 *   &lt;/xs:sequence>
 *   &lt;xs:attribute type="xs:boolean" use="required" name="replicate"/>
 *   &lt;xs:attribute type="xs:boolean" use="required" name="synchronize"/>
 *   &lt;xs:attribute type="ns:NodeType" use="required" name="type"/>
 *   &lt;xs:attribute type="ns:NodeState" use="required" name="state"/>
 * &lt;/xs:complexType>
 * </pre>
 */
public class Node implements Serializable
{
    private static final long serialVersionUID = 10000000;
    private NodeReference identifier;
    private String name;
    private String description;
    private String baseURL;
    private Services services;
    private Synchronization synchronization;
    private Ping ping;
    private List<Subject> subjectList = new ArrayList<Subject>();
    private List<Subject> contactSubjectList = new ArrayList<Subject>();
    private boolean replicate;
    private boolean synchronize;
    private NodeType type;
    private NodeState state;

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
     * Get the 'name' element value. A human readable name of the Node. This name can be used as a label in many systems
                      to represent the node, and thus should be short but understandable.
                      
     * 
     * @return value
     */
    public String getName() {
        return name;
    }

    /** 
     * Set the 'name' element value. A human readable name of the Node. This name can be used as a label in many systems
                      to represent the node, and thus should be short but understandable.
                      
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** 
     * Get the 'description' element value. Description of a Node, explaining the community it serves and other
                          relevant information about the node, such as what content is maintained by this node
                          and any other free style notes.
                      
     * 
     * @return value
     */
    public String getDescription() {
        return description;
    }

    /** 
     * Set the 'description' element value. Description of a Node, explaining the community it serves and other
                          relevant information about the node, such as what content is maintained by this node
                          and any other free style notes.
                      
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
     * Get the 'ping' element value.
     * 
     * @return value
     */
    public Ping getPing() {
        return ping;
    }

    /** 
     * Set the 'ping' element value.
     * 
     * @param ping
     */
    public void setPing(Ping ping) {
        this.ping = ping;
    }

    /** 
     * Get the list of 'subject' element items. The Subject of this node, which can be repeated as needed.  
                      The Node.subject represents the identifier of the node that would be found in X.509 
                      certificates that would be used to securely communicate with this node.  Thus, it is
                      an X.509 Distinguished Name that applies to the host on which the Node is operating. 
                      When (and if) this hostname changes the new subject for the node would be added to the
                      Node to track the subject that has been used in various access control rules over time.
                      
     * 
     * @return list
     */
    public List<Subject> getSubjectList() {
        return subjectList;
    }

    /** 
     * Set the list of 'subject' element items. The Subject of this node, which can be repeated as needed.  
                      The Node.subject represents the identifier of the node that would be found in X.509 
                      certificates that would be used to securely communicate with this node.  Thus, it is
                      an X.509 Distinguished Name that applies to the host on which the Node is operating. 
                      When (and if) this hostname changes the new subject for the node would be added to the
                      Node to track the subject that has been used in various access control rules over time.
                      
     * 
     * @param list
     */
    public void setSubjectList(List<Subject> list) {
        subjectList = list;
    }

    /** 
     * Get the number of 'subject' element items.
     * @return count
     */
    public int sizeSubjectList() {
        return subjectList.size();
    }

    /** 
     * Add a 'subject' element item.
     * @param item
     */
    public void addSubject(Subject item) {
        subjectList.add(item);
    }

    /** 
     * Get 'subject' element item by position.
     * @return item
     * @param index
     */
    public Subject getSubject(int index) {
        return subjectList.get(index);
    }

    /** 
     * Remove all 'subject' element items.
     */
    public void clearSubjectList() {
        subjectList.clear();
    }

    /** 
     * Get the list of 'contactSubject' element items. The appropriate person or group to contact regarding the disposition, 
                      management, and status of this Member Node. The Node.contactSubject is
                      an X.509 Distinguished Name for a person or group that can be used to look up current 
                      contact details (e.g., name, email address) for the contact in the DataONE Identity service.
                      DataONE uses the subjectContact to provide notices of interest to DataONE nodes, including 
                      information such as policy changes, maintenance updates, node outage notifications, among 
                      other information useful for administering a node. Each node that is registered with DataONE
                      must provide at least one subjectContact that has been verified with DataONE.
                      
     * 
     * @return list
     */
    public List<Subject> getContactSubjectList() {
        return contactSubjectList;
    }

    /** 
     * Set the list of 'contactSubject' element items. The appropriate person or group to contact regarding the disposition, 
                      management, and status of this Member Node. The Node.contactSubject is
                      an X.509 Distinguished Name for a person or group that can be used to look up current 
                      contact details (e.g., name, email address) for the contact in the DataONE Identity service.
                      DataONE uses the subjectContact to provide notices of interest to DataONE nodes, including 
                      information such as policy changes, maintenance updates, node outage notifications, among 
                      other information useful for administering a node. Each node that is registered with DataONE
                      must provide at least one subjectContact that has been verified with DataONE.
                      
     * 
     * @param list
     */
    public void setContactSubjectList(List<Subject> list) {
        contactSubjectList = list;
    }

    /** 
     * Get the number of 'contactSubject' element items.
     * @return count
     */
    public int sizeContactSubjectList() {
        return contactSubjectList.size();
    }

    /** 
     * Add a 'contactSubject' element item.
     * @param item
     */
    public void addContactSubject(Subject item) {
        contactSubjectList.add(item);
    }

    /** 
     * Get 'contactSubject' element item by position.
     * @return item
     * @param index
     */
    public Subject getContactSubject(int index) {
        return contactSubjectList.get(index);
    }

    /** 
     * Remove all 'contactSubject' element items.
     */
    public void clearContactSubjectList() {
        contactSubjectList.clear();
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
     * Get the 'state' attribute value.
     * 
     * @return value
     */
    public NodeState getState() {
        return state;
    }

    /** 
     * Set the 'state' attribute value.
     * 
     * @param state
     */
    public void setState(NodeState state) {
        this.state = state;
    }
}
