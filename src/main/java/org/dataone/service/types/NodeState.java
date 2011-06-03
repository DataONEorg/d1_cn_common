
package org.dataone.service.types;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:simpleType xmlns:ns="http://ns.dataone.org/service/types/0.6.2" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="NodeState">
 *   &lt;xs:restriction base="xs:string">
 *     &lt;xs:enumeration value="up"/>
 *     &lt;xs:enumeration value="down"/>
 *     &lt;xs:enumeration value="unknown"/>
 *   &lt;/xs:restriction>
 * &lt;/xs:simpleType>
 * </pre>
 */
public enum NodeState {
    UP("up"), DOWN("down"), UNKNOWN("unknown");
    private final String value;

    private NodeState(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    public static NodeState convert(String value) {
        for (NodeState inst : values()) {
            if (inst.toString().equals(value)) {
                return inst;
            }
        }
        return null;
    }
}
