
package org.dataone.service.types;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/0.6.2" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Log">
 *   &lt;xs:complexContent>
 *     &lt;xs:extension base="ns:Slice">
 *       &lt;xs:sequence>
 *         &lt;xs:element type="ns:LogEntry" name="logEntry" minOccurs="0" maxOccurs="unbounded"/>
 *       &lt;/xs:sequence>
 *     &lt;/xs:extension>
 *   &lt;/xs:complexContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class Log extends Slice
{
    private List<LogEntry> logEntryList = new ArrayList<LogEntry>();

    /** 
     * Get the list of 'logEntry' element items.
     * 
     * @return list
     */
    public List<LogEntry> getLogEntryList() {
        return logEntryList;
    }

    /** 
     * Set the list of 'logEntry' element items.
     * 
     * @param list
     */
    public void setLogEntryList(List<LogEntry> list) {
        logEntryList = list;
    }

    /** 
     * Get the number of 'logEntry' element items.
     * @return count
     */
    public int sizeLogEntryList() {
        return logEntryList.size();
    }

    /** 
     * Add a 'logEntry' element item.
     * @param item
     */
    public void addLogEntry(LogEntry item) {
        logEntryList.add(item);
    }

    /** 
     * Get 'logEntry' element item by position.
     * @return item
     * @param index
     */
    public LogEntry getLogEntry(int index) {
        return logEntryList.get(index);
    }

    /** 
     * Remove all 'logEntry' element items.
     */
    public void clearLogEntryList() {
        logEntryList.clear();
    }
}
