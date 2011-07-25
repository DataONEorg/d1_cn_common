
package org.dataone.service.types.v1;

import java.io.Serializable;

/** 
 * Initially an enumerated list of strings that specify different types of identifiers.

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:simpleType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="IdentifierFormat">
 *   &lt;xs:restriction base="xs:string">
 *     &lt;xs:enumeration value="OID"/>
 *     &lt;xs:enumeration value="LSID"/>
 *     &lt;xs:enumeration value="UUID"/>
 *     &lt;xs:enumeration value="LSRN"/>
 *     &lt;xs:enumeration value="DOI"/>
 *     &lt;xs:enumeration value="URI"/>
 *     &lt;xs:enumeration value="STRING"/>
 *   &lt;/xs:restriction>
 * &lt;/xs:simpleType>
 * </pre>
 */
public enum IdentifierFormat implements Serializable {
    OID, LSID, UUID, LSRN, DOI, URI, STRING
}
