
package org.dataone.service.types.v1;

import java.io.Serializable;

/** 
 * A string value indicating the type of a query from
 a controlled list. The types of queries will expand with subsequent
 release versions, but the CN will only support certain query types
 for search during any particular release. Each release will document
 which QueryTypes are supported.

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="QueryType">
 *   &lt;xs:simpleContent>
 *     &lt;xs:extension base="xs:string"/>
 *   &lt;/xs:simpleContent>
 * &lt;/xs:complexType>
 * </pre>
 */
public class QueryType implements Serializable
{
    private String value;

    /** 
     * Get the 'QueryType' complexType value.
     * 
     * @return value
     */
    public String getValue() {
        return value;
    }

    /** 
     * Set the 'QueryType' complexType value.
     * 
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }
}
