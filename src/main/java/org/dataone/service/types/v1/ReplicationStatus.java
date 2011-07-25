
package org.dataone.service.types.v1;

import java.io.Serializable;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:simpleType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="ReplicationStatus">
 *   &lt;xs:restriction base="xs:string">
 *     &lt;xs:enumeration value="queued"/>
 *     &lt;xs:enumeration value="requested"/>
 *     &lt;xs:enumeration value="completed"/>
 *     &lt;xs:enumeration value="invalidated"/>
 *   &lt;/xs:restriction>
 * &lt;/xs:simpleType>
 * </pre>
 */
public enum ReplicationStatus implements Serializable {
    QUEUED("queued"), REQUESTED("requested"), COMPLETED("completed"), INVALIDATED(
            "invalidated");
    private final String value;

    private ReplicationStatus(String value) {
        this.value = value;
    }

    public String xmlValue() {
        return value;
    }

    public static ReplicationStatus convert(String value) {
        for (ReplicationStatus inst : values()) {
            if (inst.xmlValue().equals(value)) {
                return inst;
            }
        }
        return null;
    }
}
