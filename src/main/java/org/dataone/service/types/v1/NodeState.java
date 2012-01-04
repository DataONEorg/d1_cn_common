
package org.dataone.service.types.v1;

import java.io.Serializable;

/** 
 * An indicator of the current node accessibility. Nodes
 that are marked *down* are inaccessible for service operations, those
 that are *up* are in the normal accessible state, and *unknown*
 indicates that the state has not been determined yet.
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:simpleType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="NodeState">
 *   &lt;xs:restriction base="xs:string">
 *     &lt;xs:enumeration value="up"/>
 *     &lt;xs:enumeration value="down"/>
 *     &lt;xs:enumeration value="unknown"/>
 *   &lt;/xs:restriction>
 * &lt;/xs:simpleType>
 * </pre>
 */
public enum NodeState implements Serializable {
    UP("up"), DOWN("down"), UNKNOWN("unknown");
    private static final long serialVersionUID = 10000000;
    private final String value;

    private NodeState(String value) {
        this.value = value;
    }

    public String xmlValue() {
        return value;
    }

    public static NodeState convert(String value) {
        for (NodeState inst : values()) {
            if (inst.xmlValue().equals(value)) {
                return inst;
            }
        }
        return null;
    }
}
