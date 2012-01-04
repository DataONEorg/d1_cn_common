
package org.dataone.service.types.v1;

import java.io.Serializable;

/** 
 * The type of this node, which is either *mn* for
 Member Nodes, or *cn* for Coordinating Nodes.
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:simpleType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="NodeType">
 *   &lt;xs:restriction base="xs:string">
 *     &lt;xs:enumeration value="mn"/>
 *     &lt;xs:enumeration value="cn"/>
 *     &lt;xs:enumeration value="Monitor"/>
 *   &lt;/xs:restriction>
 * &lt;/xs:simpleType>
 * </pre>
 */
public enum NodeType implements Serializable {
    MN("mn"), CN("cn"), MONITOR("Monitor");
    private static final long serialVersionUID = 10000000;
    private final String value;

    private NodeType(String value) {
        this.value = value;
    }

    public String xmlValue() {
        return value;
    }

    public static NodeType convert(String value) {
        for (NodeType inst : values()) {
            if (inst.xmlValue().equals(value)) {
                return inst;
            }
        }
        return null;
    }
}
