
package org.dataone.service.types;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://dataone.org/service/types/NodeRegistry/0.1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="nodeRegistry">
 *   &lt;xs:complexType>
 *     &lt;xs:sequence>
 *       &lt;xs:element type="ns:Node" name="node" minOccurs="1" maxOccurs="unbounded"/>
 *     &lt;/xs:sequence>
 *   &lt;/xs:complexType>
 * &lt;/xs:element>
 * </pre>
 */
public class NodeRegistry
{
    private List<Node> nodeList = new ArrayList<Node>();

    /** 
     * Get the list of 'node' element items.
     * 
     * @return list
     */
    public List<Node> getNodes() {
        return nodeList;
    }

    /** 
     * Set the list of 'node' element items.
     * 
     * @param list
     */
    public void setNodes(List<Node> list) {
        nodeList = list;
    }

    /** 
     * Get the number of 'node' element items.
     * @return count
     */
    public int sizeNodes() {
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
    public void clearNodes() {
        nodeList.clear();
    }
}
