
package org.dataone.service.types.v1;

import java.io.Serializable;

/** 
 * A controlled string value indicating the current
 state of a replica of an object.  When an object 
 identified that needs to be replicated, it is added to the replication
 task queue and is marked as 'queued'; a CN node will then pick up 
 that task and request that it be replicated to a MN and marks that it
 is 'requested'; when a MN finishes replicating the object, it informs
 the CN that it is finished and it is marked as 'completed'; periodically
 the CN checks each replica to be sure it is both available and valid 
 (matching checksum with original), and if it is either unavailable in 
 invalid then it marks it as 'invalidated', which indicates that the 
 replication policy needs to be checked again. 

 * 
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
