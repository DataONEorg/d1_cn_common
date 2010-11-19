
package org.dataone.service.types;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:simpleType xmlns:ns="http://dataone.org/service/types/0.5.1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="IdentifierFormat">
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
public enum IdentifierFormat {
    OID, LSID, UUID, LSRN, DOI, URI, STRING
}
