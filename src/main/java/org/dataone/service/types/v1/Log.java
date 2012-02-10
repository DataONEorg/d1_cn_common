
package org.dataone.service.types.v1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** 
 * Represents a collection of :class:`Types.LogEntry`
 elements, used to transfer log information between DataONE
 components.
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Log">
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
public class Log extends Slice implements Serializable
{
    private static final long serialVersionUID = 10000000;
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
        if (logEntryList == null) {
            logEntryList = new ArrayList<LogEntry>();
        }
        return logEntryList.size();
    }

    /** 
     * Add a 'logEntry' element item.
     * @param item
     */
    public void addLogEntry(LogEntry item) {
        if (logEntryList == null) {
            logEntryList = new ArrayList<LogEntry>();
        }
        logEntryList.add(item);
    }

    /** 
     * Get 'logEntry' element item by position.
     * @return item
     * @param index
     */
    public LogEntry getLogEntry(int index) {
        if (logEntryList == null) {
            logEntryList = new ArrayList<LogEntry>();
        }
        return logEntryList.get(index);
    }

    /** 
     * Remove all 'logEntry' element items.
     */
    public void clearLogEntryList() {
        if (logEntryList == null) {
            logEntryList = new ArrayList<LogEntry>();
        }
        logEntryList.clear();
    }
}
