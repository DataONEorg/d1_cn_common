
package org.dataone.service.types.v1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** 
 *  A list of Node entries that is returned by :func:`CNCore.listNodes()`.NodeList is described in :mod:`NodeList`.
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="NodeList">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="ns:Node" name="node" minOccurs="1" maxOccurs="unbounded"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class NodeList implements Serializable
{
    private List<Node> nodeList = new ArrayList<Node>();

    /** 
     * Get the list of 'node' element items.
     * 
     * @return list
     */
    public List<Node> getNodeList() {
        return nodeList;
    }

    /** 
     * Set the list of 'node' element items.
     * 
     * @param list
     */
    public void setNodeList(List<Node> list) {
        nodeList = list;
    }

    /** 
     * Get the number of 'node' element items.
     * @return count
     */
    public int sizeNodeList() {
        return nodeList.size();
    }

    /** 
     * Add a 'node' element item.
     * @param item
     */
    public void addNode(Node item) {
        nodeList.add(item);
    }

    /** 
     * Get 'node' element item by position.
     * @return item
     * @param index
     */
    public Node getNode(int index) {
        return nodeList.get(index);
    }

    /** 
     * Remove all 'node' element items.
     */
    public void clearNodeList() {
        nodeList.clear();
    }
}
