
package org.dataone.service.types;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/0.6.2" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Slice">
 *   &lt;xs:attribute type="xs:int" use="required" name="count"/>
 *   &lt;xs:attribute type="xs:int" use="required" name="start"/>
 *   &lt;xs:attribute type="xs:int" use="required" name="total"/>
 * &lt;/xs:complexType>
 * </pre>
 */
public class Slice
{
    private int count;
    private int start;
    private int total;

    /** 
     * Get the 'count' attribute value.
     * 
     * @return value
     */
    public int getCount() {
        return count;
    }

    /** 
     * Set the 'count' attribute value.
     * 
     * @param count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /** 
     * Get the 'start' attribute value.
     * 
     * @return value
     */
    public int getStart() {
        return start;
    }

    /** 
     * Set the 'start' attribute value.
     * 
     * @param start
     */
    public void setStart(int start) {
        this.start = start;
    }

    /** 
     * Get the 'total' attribute value.
     * 
     * @return value
     */
    public int getTotal() {
        return total;
    }

    /** 
     * Set the 'total' attribute value.
     * 
     * @param total
     */
    public void setTotal(int total) {
        this.total = total;
    }
}
