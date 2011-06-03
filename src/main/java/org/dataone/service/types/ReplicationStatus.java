
package org.dataone.service.types;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:simpleType xmlns:ns="http://ns.dataone.org/service/types/0.6.2" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="ReplicationStatus">
 *   &lt;xs:restriction base="xs:string">
 *     &lt;xs:enumeration value="queued"/>
 *     &lt;xs:enumeration value="requested"/>
 *     &lt;xs:enumeration value="completed"/>
 *     &lt;xs:enumeration value="invalidated"/>
 *   &lt;/xs:restriction>
 * &lt;/xs:simpleType>
 * </pre>
 */
public enum ReplicationStatus {
    QUEUED("queued"), REQUESTED("requested"), COMPLETED("completed"), INVALIDATED(
            "invalidated");
    private final String value;

    private ReplicationStatus(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    public static ReplicationStatus convert(String value) {
        for (ReplicationStatus inst : values()) {
            if (inst.toString().equals(value)) {
                return inst;
            }
        }
        return null;
    }
}
