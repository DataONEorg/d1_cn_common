
package org.dataone.service.types.v1;

import java.io.Serializable;

/** 
 * A string value indicating the set of actions that
 can be performed on a resource as specified in an access policy. The
 set of permissions include the ability to read a resource (read), modify a
 resource (write), and to change the set of access control policies
 for a resource (changePermission). In addition, there is a permission
 that controls ability to execute a service (execute), and a 
 permission that controls the ability of a node to receive a replica of
 an object from another node (replicate).

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:simpleType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="Permission">
 *   &lt;xs:restriction base="xs:string">
 *     &lt;xs:enumeration value="read"/>
 *     &lt;xs:enumeration value="write"/>
 *     &lt;xs:enumeration value="changePermission"/>
 *     &lt;xs:enumeration value="execute"/>
 *     &lt;xs:enumeration value="replicate"/>
 *   &lt;/xs:restriction>
 * &lt;/xs:simpleType>
 * </pre>
 */
public enum Permission implements Serializable {
    READ("read"), WRITE("write"), CHANGE_PERMISSION("changePermission"), EXECUTE(
            "execute"), REPLICATE("replicate");
    private static final long serialVersionUID = 10000000;
    private final String value;

    private Permission(String value) {
        this.value = value;
    }

    public String xmlValue() {
        return value;
    }

    public static Permission convert(String value) {
        for (Permission inst : values()) {
            if (inst.xmlValue().equals(value)) {
                return inst;
            }
        }
        return null;
    }
}
