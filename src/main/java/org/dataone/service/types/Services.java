
package org.dataone.service.types;

import java.util.Date;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://dataone.org/service/types/NodeRegistry/0.1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Services">
 *   &lt;xs:attribute type="xs:string" use="required" name="api"/>
 *   &lt;xs:attribute type="xs:boolean" use="required" name="available"/>
 *   &lt;xs:attribute type="xs:dateTime" use="required" name="datechecked"/>
 *   &lt;xs:attribute type="xs:string" use="required" name="method"/>
 *   &lt;xs:attribute type="xs:string" use="required" name="rest"/>
 * &lt;/xs:complexType>
 * </pre>
 */
public class Services
{
    private String api;
    private boolean available;
    private Date datechecked;
    private String method;
    private String rest;

    /** 
     * Get the 'api' attribute value.
     * 
     * @return value
     */
    public String getApi() {
        return api;
    }

    /** 
     * Set the 'api' attribute value.
     * 
     * @param api
     */
    public void setApi(String api) {
        this.api = api;
    }

    /** 
     * Get the 'available' attribute value.
     * 
     * @return value
     */
    public boolean isAvailable() {
        return available;
    }

    /** 
     * Set the 'available' attribute value.
     * 
     * @param available
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /** 
     * Get the 'datechecked' attribute value.
     * 
     * @return value
     */
    public Date getDatechecked() {
        return datechecked;
    }

    /** 
     * Set the 'datechecked' attribute value.
     * 
     * @param datechecked
     */
    public void setDatechecked(Date datechecked) {
        this.datechecked = datechecked;
    }

    /** 
     * Get the 'method' attribute value.
     * 
     * @return value
     */
    public String getMethod() {
        return method;
    }

    /** 
     * Set the 'method' attribute value.
     * 
     * @param method
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /** 
     * Get the 'rest' attribute value.
     * 
     * @return value
     */
    public String getRest() {
        return rest;
    }

    /** 
     * Set the 'rest' attribute value.
     * 
     * @param rest
     */
    public void setRest(String rest) {
        this.rest = rest;
    }
}
