
package org.dataone.service.types.v1;

import java.io.Serializable;

/** 
 * A string value indicating the type of a query from a controlled list.
 The types of queries will expand with subsequent release versions, but
 the CN will only support certain query types for search during any particular
 release.

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:simpleType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="QueryType">
 *   &lt;xs:restriction base="xs:string">
 *     &lt;xs:enumeration value="SOLR"/>
 *     &lt;xs:enumeration value="ECOGRID"/>
 *   &lt;/xs:restriction>
 * &lt;/xs:simpleType>
 * </pre>
 */
public enum QueryType implements Serializable {
    SOLR, ECOGRID
}
