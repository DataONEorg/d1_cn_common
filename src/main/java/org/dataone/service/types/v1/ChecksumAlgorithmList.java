
package org.dataone.service.types.v1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** 
 * Represents a list of :term:`checksum`
 algorithms.
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="ChecksumAlgorithmList">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="xs:string" name="algorithm" minOccurs="1" maxOccurs="unbounded"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class ChecksumAlgorithmList implements Serializable
{
    private static final long serialVersionUID = 10000000;
    private List<String> algorithmList = new ArrayList<String>();

    /** 
     * Get the list of 'algorithm' element items.
     * 
     * @return list
     */
    public List<String> getAlgorithmList() {
        return algorithmList;
    }

    /** 
     * Set the list of 'algorithm' element items.
     * 
     * @param list
     */
    public void setAlgorithmList(List<String> list) {
        algorithmList = list;
    }

    /** 
     * Get the number of 'algorithm' element items.
     * @return count
     */
    public int sizeAlgorithmList() {
        return algorithmList.size();
    }

    /** 
     * Add a 'algorithm' element item.
     * @param item
     */
    public void addAlgorithm(String item) {
        algorithmList.add(item);
    }

    /** 
     * Get 'algorithm' element item by position.
     * @return item
     * @param index
     */
    public String getAlgorithm(int index) {
        return algorithmList.get(index);
    }

    /** 
     * Remove all 'algorithm' element items.
     */
    public void clearAlgorithmList() {
        algorithmList.clear();
    }
}
