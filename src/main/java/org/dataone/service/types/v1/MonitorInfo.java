
package org.dataone.service.types.v1;

import java.io.Serializable;
import java.sql.Date;

/** 
 * A single log entry as reported by a Member Node or Coordinating Node through
 the getMonitorInfos methods.
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="MonitorInfo">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="xs:date" name="date" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:int" name="count" minOccurs="1" maxOccurs="1"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class MonitorInfo implements Serializable
{
    private Date date;
    private int count;

    /** 
     * Get the 'date' element value.
     * 
     * @return value
     */
    public Date getDate() {
        return date;
    }

    /** 
     * Set the 'date' element value.
     * 
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /** 
     * Get the 'count' element value.
     * 
     * @return value
     */
    public int getCount() {
        return count;
    }

    /** 
     * Set the 'count' element value.
     * 
     * @param count
     */
    public void setCount(int count) {
        this.count = count;
    }
}
