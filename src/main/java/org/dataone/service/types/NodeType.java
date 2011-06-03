
package org.dataone.service.types;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:simpleType xmlns:ns="http://ns.dataone.org/service/types/0.6.2" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="NodeType">
 *   &lt;xs:restriction base="xs:string">
 *     &lt;xs:enumeration value="mn"/>
 *     &lt;xs:enumeration value="cn"/>
 *     &lt;xs:enumeration value="Monitor"/>
 *   &lt;/xs:restriction>
 * &lt;/xs:simpleType>
 * </pre>
 */
public enum NodeType {
    MN("mn"), CN("cn"), MONITOR("Monitor");
    private final String value;

    private NodeType(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    public static NodeType convert(String value) {
        for (NodeType inst : values()) {
            if (inst.toString().equals(value)) {
                return inst;
            }
        }
        return null;
    }
}
