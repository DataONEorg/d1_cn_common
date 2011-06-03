
package org.dataone.service.types;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:simpleType xmlns:ns="http://ns.dataone.org/service/types/0.6.2" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Environment">
 *   &lt;xs:restriction base="xs:string">
 *     &lt;xs:enumeration value="dev"/>
 *     &lt;xs:enumeration value="test"/>
 *     &lt;xs:enumeration value="staging"/>
 *     &lt;xs:enumeration value="prod"/>
 *   &lt;/xs:restriction>
 * &lt;/xs:simpleType>
 * </pre>
 */
public enum Environment {
    DEV("dev"), TEST("test"), STAGING("staging"), PROD("prod");
    private final String value;

    private Environment(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    public static Environment convert(String value) {
        for (Environment inst : values()) {
            if (inst.toString().equals(value)) {
                return inst;
            }
        }
        return null;
    }
}
