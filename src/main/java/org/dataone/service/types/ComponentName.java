
package org.dataone.service.types;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:simpleType xmlns:ns="http://ns.dataone.org/service/types/0.6.2" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="ComponentName">
 *   &lt;xs:restriction base="xs:string">
 *     &lt;xs:enumeration value="Apache"/>
 *     &lt;xs:enumeration value="CoordinatingNode"/>
 *     &lt;xs:enumeration value="Django"/>
 *     &lt;xs:enumeration value="LinuxUbuntu"/>
 *     &lt;xs:enumeration value="LinuxDebian"/>
 *     &lt;xs:enumeration value="MemberNode"/>
 *     &lt;xs:enumeration value="Mercury"/>
 *     &lt;xs:enumeration value="Metacat"/>
 *   &lt;/xs:restriction>
 * &lt;/xs:simpleType>
 * </pre>
 */
public enum ComponentName {
    APACHE("Apache"), COORDINATING_NODE("CoordinatingNode"), DJANGO("Django"), LINUX_UBUNTU(
            "LinuxUbuntu"), LINUX_DEBIAN("LinuxDebian"), MEMBER_NODE(
            "MemberNode"), MERCURY("Mercury"), METACAT("Metacat");
    private final String value;

    private ComponentName(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    public static ComponentName convert(String value) {
        for (ComponentName inst : values()) {
            if (inst.toString().equals(value)) {
                return inst;
            }
        }
        return null;
    }
}
