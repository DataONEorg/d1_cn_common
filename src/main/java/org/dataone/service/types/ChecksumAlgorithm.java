
package org.dataone.service.types;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:simpleType xmlns:ns="http://dataone.org/service/types/0.5.1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="ChecksumAlgorithm">
 *   &lt;xs:restriction base="xs:string">
 *     &lt;xs:enumeration value="SHA-1"/>
 *     &lt;xs:enumeration value="SHA-224"/>
 *     &lt;xs:enumeration value="SHA-256"/>
 *     &lt;xs:enumeration value="SHA-384"/>
 *     &lt;xs:enumeration value="SHA-512"/>
 *     &lt;xs:enumeration value="MD5"/>
 *   &lt;/xs:restriction>
 * &lt;/xs:simpleType>
 * </pre>
 */
public enum ChecksumAlgorithm {
    SHA_1("SHA-1"), SHA_224("SHA-224"), SHA_256("SHA-256"), SHA_384("SHA-384"), SHA_512(
            "SHA-512"), MD5("MD5");
    private final String value;

    private ChecksumAlgorithm(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    public static ChecksumAlgorithm convert(String value) {
        for (ChecksumAlgorithm inst : values()) {
            if (inst.toString().equals(value)) {
                return inst;
            }
        }
        return null;
    }
}
