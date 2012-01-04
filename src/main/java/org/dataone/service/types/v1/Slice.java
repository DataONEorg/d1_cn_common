
package org.dataone.service.types.v1;

import java.io.Serializable;

/** 
 * An abstract type used as a common base for other types
 that need to include *count*, *start*, and *total* attributes to
 indicate which slice of a list is represented by a set of
 records.The first element in a list is always index 0, i.e.
 list indexes are zero-based.
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Slice">
 *   &lt;xs:attribute type="xs:int" use="required" name="count"/>
 *   &lt;xs:attribute type="xs:int" use="required" name="start"/>
 *   &lt;xs:attribute type="xs:int" use="required" name="total"/>
 * &lt;/xs:complexType>
 * </pre>
 */
public class Slice implements Serializable
{
    private static final long serialVersionUID = 10000000;
    private int count;
    private int start;
    private int total;

    /** 
     * Get the 'count' attribute value. The number of entries in the
          slice.
     * 
     * @return value
     */
    public int getCount() {
        return count;
    }

    /** 
     * Set the 'count' attribute value. The number of entries in the
          slice.
     * 
     * @param count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /** 
     * Get the 'start' attribute value. The zero-based index of the first element in the
          slice.
     * 
     * @return value
     */
    public int getStart() {
        return start;
    }

    /** 
     * Set the 'start' attribute value. The zero-based index of the first element in the
          slice.
     * 
     * @param start
     */
    public void setStart(int start) {
        this.start = start;
    }

    /** 
     * Get the 'total' attribute value. The total number of entries in the source list from
          which the slice was extracted.
     * 
     * @return value
     */
    public int getTotal() {
        return total;
    }

    /** 
     * Set the 'total' attribute value. The total number of entries in the source list from
          which the slice was extracted.
     * 
     * @param total
     */
    public void setTotal(int total) {
        this.total = total;
    }
}
