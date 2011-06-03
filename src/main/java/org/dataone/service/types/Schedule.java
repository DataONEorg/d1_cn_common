
package org.dataone.service.types;

/** 
 * The schedule on which MnSynchronization will run for a particular run

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/0.6.2" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Schedule">
 *   &lt;xs:attribute type="xs:string" use="required" name="hour"/>
 *   &lt;xs:attribute type="xs:string" use="required" name="mday"/>
 *   &lt;xs:attribute type="xs:string" use="required" name="min"/>
 *   &lt;xs:attribute type="xs:string" use="required" name="mon"/>
 *   &lt;xs:attribute type="xs:string" use="required" name="sec"/>
 *   &lt;xs:attribute type="xs:string" use="required" name="wday"/>
 *   &lt;xs:attribute type="xs:string" use="required" name="year"/>
 * &lt;/xs:complexType>
 * </pre>
 */
public class Schedule
{
    private String hour;
    private String mday;
    private String min;
    private String mon;
    private String sec;
    private String wday;
    private String year;

    /** 
     * Get the 'hour' attribute value.
     * 
     * @return value
     */
    public String getHour() {
        return hour;
    }

    /** 
     * Set the 'hour' attribute value.
     * 
     * @param hour
     */
    public void setHour(String hour) {
        this.hour = hour;
    }

    /** 
     * Get the 'mday' attribute value.
     * 
     * @return value
     */
    public String getMday() {
        return mday;
    }

    /** 
     * Set the 'mday' attribute value.
     * 
     * @param mday
     */
    public void setMday(String mday) {
        this.mday = mday;
    }

    /** 
     * Get the 'min' attribute value.
     * 
     * @return value
     */
    public String getMin() {
        return min;
    }

    /** 
     * Set the 'min' attribute value.
     * 
     * @param min
     */
    public void setMin(String min) {
        this.min = min;
    }

    /** 
     * Get the 'mon' attribute value.
     * 
     * @return value
     */
    public String getMon() {
        return mon;
    }

    /** 
     * Set the 'mon' attribute value.
     * 
     * @param mon
     */
    public void setMon(String mon) {
        this.mon = mon;
    }

    /** 
     * Get the 'sec' attribute value.
     * 
     * @return value
     */
    public String getSec() {
        return sec;
    }

    /** 
     * Set the 'sec' attribute value.
     * 
     * @param sec
     */
    public void setSec(String sec) {
        this.sec = sec;
    }

    /** 
     * Get the 'wday' attribute value.
     * 
     * @return value
     */
    public String getWday() {
        return wday;
    }

    /** 
     * Set the 'wday' attribute value.
     * 
     * @param wday
     */
    public void setWday(String wday) {
        this.wday = wday;
    }

    /** 
     * Get the 'year' attribute value.
     * 
     * @return value
     */
    public String getYear() {
        return year;
    }

    /** 
     * Set the 'year' attribute value.
     * 
     * @param year
     */
    public void setYear(String year) {
        this.year = year;
    }
}
