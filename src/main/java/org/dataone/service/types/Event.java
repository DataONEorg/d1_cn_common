
package org.dataone.service.types;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:simpleType xmlns:ns="http://dataone.org/service/types/logging/0.5" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Event">
 *   &lt;xs:restriction base="xs:string">
 *     &lt;xs:enumeration value="create"/>
 *     &lt;xs:enumeration value="read"/>
 *     &lt;xs:enumeration value="update"/>
 *     &lt;xs:enumeration value="delete"/>
 *     &lt;xs:enumeration value="replicate"/>
 *   &lt;/xs:restriction>
 * &lt;/xs:simpleType>
 * </pre>
 */
public enum Event {
    CREATE("create"), READ("read"), UPDATE("update"), DELETE("delete"), REPLICATE(
            "replicate");
    private final String value;

    private Event(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    public static Event convert(String value) {
        for (Event inst : values()) {
            if (inst.toString().equals(value)) {
                return inst;
            }
        }
        return null;
    }
}
