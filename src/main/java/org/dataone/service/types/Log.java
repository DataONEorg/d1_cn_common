
package org.dataone.service.types;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:element xmlns:ns="http://dataone.org/service/types/logging/0.1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="log">
 *   &lt;xs:complexType>
 *     &lt;xs:sequence>
 *       &lt;xs:element type="ns:LogEntry" name="logEntry" minOccurs="0" maxOccurs="unbounded"/>
 *     &lt;/xs:sequence>
 *   &lt;/xs:complexType>
 * &lt;/xs:element>
 * </pre>
 */
public class Log
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