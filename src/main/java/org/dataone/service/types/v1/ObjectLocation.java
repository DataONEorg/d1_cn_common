
package org.dataone.service.types.v1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** 
 * Portion of an :class:`Types.ObjectLocationList`
 indicating the node from which the object can be retrieved. The
 principal information on each location is found in the *nodeIdentifier*,
 all other fields are provided for convenience, but could also be looked
 up from the Node list information.
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="ObjectLocation">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:NodeReference" name="nodeIdentifier" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="baseURL" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="version" minOccurs="1" maxOccurs="unbounded"/>
 *     &lt;xs:element type="xs:string" name="url" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:int" name="preference" minOccurs="0" maxOccurs="1"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class ObjectLocation implements Serializable
{
    private static final long serialVersionUID = 10000000;
    private NodeReference nodeIdentifier;
    private String baseURL;
    private List<String> versionList = new ArrayList<String>();
    private String url;
    private Integer preference;

    /** 
     * Get the 'nodeIdentifier' element value. Identifier of the :class:`Types.Node` (the same
            identifier used in the node registry for identifying the node).
            
     * 
     * @return value
     */
    public NodeReference getNodeIdentifier() {
        return nodeIdentifier;
    }

    /** 
     * Set the 'nodeIdentifier' element value. Identifier of the :class:`Types.Node` (the same
            identifier used in the node registry for identifying the node).
            
     * 
     * @param nodeIdentifier
     */
    public void setNodeIdentifier(NodeReference nodeIdentifier) {
        this.nodeIdentifier = nodeIdentifier;
    }

    /** 
     * Get the 'baseURL' element value. The current base URL (the *baseURL* element from
            the :class:`Types.Node` record) for services implemented on the
            target node. Used with service version to construct a URL for
            service calls to this node. Note that complete information on
            services available on a Node is available from the
            :func:`CNCore.listNodes` service. 
     * 
     * @return value
     */
    public String getBaseURL() {
        return baseURL;
    }

    /** 
     * Set the 'baseURL' element value. The current base URL (the *baseURL* element from
            the :class:`Types.Node` record) for services implemented on the
            target node. Used with service version to construct a URL for
            service calls to this node. Note that complete information on
            services available on a Node is available from the
            :func:`CNCore.listNodes` service. 
     * 
     * @param baseURL
     */
    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    /** 
     * Get the list of 'version' element items. The version of services implemented on the node.
            Used with base url to construct a URL for service calls to this
            node. Note that complete information on services available on a Node
            is available from the :func:`CNCore.listNodes` service.
            
     * 
     * @return list
     */
    public List<String> getVersionList() {
        return versionList;
    }

    /** 
     * Set the list of 'version' element items. The version of services implemented on the node.
            Used with base url to construct a URL for service calls to this
            node. Note that complete information on services available on a Node
            is available from the :func:`CNCore.listNodes` service.
            
     * 
     * @param list
     */
    public void setVersionList(List<String> list) {
        versionList = list;
    }

    /** 
     * Get the number of 'version' element items.
     * @return count
     */
    public int sizeVersionList() {
        return versionList.size();
    }

    /** 
     * Add a 'version' element item.
     * @param item
     */
    public void addVersion(String item) {
        versionList.add(item);
    }

    /** 
     * Get 'version' element item by position.
     * @return item
     * @param index
     */
    public String getVersion(int index) {
        return versionList.get(index);
    }

    /** 
     * Remove all 'version' element items.
     */
    public void clearVersionList() {
        versionList.clear();
    }

    /** 
     * Get the 'url' element value. The full (absolute) URL that can be used to
            retrieve the object using the get() method of the rest
            interface.For example, if identifier was "ABX154", and the
            node had a base URL of ``http://mn1.dataone.org/mn`` then the value
            would be ``http://mn1.dataone.org/mn/object/ABX154``
     * 
     * @return value
     */
    public String getUrl() {
        return url;
    }

    /** 
     * Set the 'url' element value. The full (absolute) URL that can be used to
            retrieve the object using the get() method of the rest
            interface.For example, if identifier was "ABX154", and the
            node had a base URL of ``http://mn1.dataone.org/mn`` then the value
            would be ``http://mn1.dataone.org/mn/object/ABX154``
     * 
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /** 
     * Get the 'preference' element value. A weighting parameter that provides a hint to the
            caller for the relative preference for nodes from which the content
            should be retrieved. 
     * 
     * @return value
     */
    public Integer getPreference() {
        return preference;
    }

    /** 
     * Set the 'preference' element value. A weighting parameter that provides a hint to the
            caller for the relative preference for nodes from which the content
            should be retrieved. 
     * 
     * @param preference
     */
    public void setPreference(Integer preference) {
        this.preference = preference;
    }
}
