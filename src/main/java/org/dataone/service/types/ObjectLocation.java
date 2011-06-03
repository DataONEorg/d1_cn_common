
package org.dataone.service.types;

import java.math.BigInteger;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/0.6.2" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="ObjectLocation">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:Identifier" name="nodeIdentifier" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="baseURL" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="url" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:integer" name="preference" minOccurs="0" maxOccurs="1"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class ObjectLocation
{
    private Identifier nodeIdentifier;
    private String baseURL;
    private String url;
    private BigInteger preference;

    /** 
     * Get the 'nodeIdentifier' element value. Identifier of the node (the same identifier used
                          in the node registry for identifying the node.
                      
     * 
     * @return value
     */
    public Identifier getNodeIdentifier() {
        return nodeIdentifier;
    }

    /** 
     * Set the 'nodeIdentifier' element value. Identifier of the node (the same identifier used
                          in the node registry for identifying the node.
                      
     * 
     * @param nodeIdentifier
     */
    public void setNodeIdentifier(Identifier nodeIdentifier) {
        this.nodeIdentifier = nodeIdentifier;
    }

    /** 
     * Get the 'baseURL' element value. The current base URL for services implemented on the target node.
                      
     * 
     * @return value
     */
    public String getBaseURL() {
        return baseURL;
    }

    /** 
     * Set the 'baseURL' element value. The current base URL for services implemented on the target node.
                      
     * 
     * @param baseURL
     */
    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    /** 
     * Get the 'url' element value. The full (absolute) URL that can be used to
                          retrieve the object using the get() method of the rest interface.
                      
     * 
     * @return value
     */
    public String getUrl() {
        return url;
    }

    /** 
     * Set the 'url' element value. The full (absolute) URL that can be used to
                          retrieve the object using the get() method of the rest interface.
                      
     * 
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /** 
     * Get the 'preference' element value. A weighting parameter that provides a hint to the caller 
                          for the relative preference for nodes from which the content should be retrieved.
                      
     * 
     * @return value
     */
    public BigInteger getPreference() {
        return preference;
    }

    /** 
     * Set the 'preference' element value. A weighting parameter that provides a hint to the caller 
                          for the relative preference for nodes from which the content should be retrieved.
                      
     * 
     * @param preference
     */
    public void setPreference(BigInteger preference) {
        this.preference = preference;
    }
}
