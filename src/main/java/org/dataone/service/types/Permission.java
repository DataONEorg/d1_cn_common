
package org.dataone.service.types;

/** 
 * A string value indicating the set of actions that can be performed on a
 resource as specified in an access policy.  The set of permissions include
 the ability to read a resource, modify a resource (write), and to change
 the set of access control policies for a resource (changePermission).  In
 addition, there is a permission that controls ability to execute a service
 (execute). Permissions are cumulative, in that higher level permissions
 include all of the priveledges of lower levels (e.g., given write access, one
 also implicitly has read access).
 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:simpleType xmlns:ns="http://ns.dataone.org/service/types/0.6.2" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Permission">
 *   &lt;xs:restriction base="xs:string">
 *     &lt;xs:enumeration value="read"/>
 *     &lt;xs:enumeration value="write"/>
 *     &lt;xs:enumeration value="changePermission"/>
 *     &lt;xs:enumeration value="execute"/>
 *   &lt;/xs:restriction>
 * &lt;/xs:simpleType>
 * </pre>
 */
public enum Permission {
    READ("read"), WRITE("write"), CHANGE_PERMISSION("changePermission"), EXECUTE(
            "execute");
    private final String value;

    private Permission(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    public static Permission convert(String value) {
        for (Permission inst : values()) {
            if (inst.toString().equals(value)) {
                return inst;
            }
        }
        return null;
    }
}
