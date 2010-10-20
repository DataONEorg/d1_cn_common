
package org.dataone.service.types;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://dataone.org/service/types/common/0.5" xmlns:ns1="http://dataone.org/service/types/SystemMetadata/0.5" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="AccessRule">
 *   &lt;xs:attribute name="rule">
 *     &lt;xs:simpleType>
 *       &lt;!-- Reference to inner class Rule -->
 *     &lt;/xs:simpleType>
 *   &lt;/xs:attribute>
 *   &lt;xs:attribute name="service">
 *     &lt;xs:simpleType>
 *       &lt;!-- Reference to inner class Service -->
 *     &lt;/xs:simpleType>
 *   &lt;/xs:attribute>
 *   &lt;xs:attribute type="ns:Principal" name="principal"/>
 * &lt;/xs:complexType>
 * </pre>
 */
public class AccessRule
{
    private Rule rule;
    private Service service;
    private Principal principal;

    /** 
     * Get the 'rule' attribute value.
     * 
     * @return value
     */
    public Rule getRule() {
        return rule;
    }

    /** 
     * Set the 'rule' attribute value.
     * 
     * @param rule
     */
    public void setRule(Rule rule) {
        this.rule = rule;
    }

    /** 
     * Get the 'service' attribute value.
     * 
     * @return value
     */
    public Service getService() {
        return service;
    }

    /** 
     * Set the 'service' attribute value.
     * 
     * @param service
     */
    public void setService(Service service) {
        this.service = service;
    }

    /** 
     * Get the 'principal' attribute value.
     * 
     * @return value
     */
    public Principal getPrincipal() {
        return principal;
    }

    /** 
     * Set the 'principal' attribute value.
     * 
     * @param principal
     */
    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }
    /** 
     * Schema fragment(s) for this class:
     * <pre>
     * &lt;xs:simpleType xmlns:xs="http://www.w3.org/2001/XMLSchema">
     *   &lt;xs:restriction base="xs:string">
     *     &lt;xs:enumeration value="allow"/>
     *     &lt;xs:enumeration value="deny"/>
     *   &lt;/xs:restriction>
     * &lt;/xs:simpleType>
     * </pre>
     */
    public static enum Rule {
        ALLOW("allow"), DENY("deny");
        private final String value;

        private Rule(String value) {
            this.value = value;
        }

        public String toString() {
            return value;
        }

        public static Rule convert(String value) {
            for (Rule inst : values()) {
                if (inst.toString().equals(value)) {
                    return inst;
                }
            }
            return null;
        }
    }
    /** 
     * Schema fragment(s) for this class:
     * <pre>
     * &lt;xs:simpleType xmlns:xs="http://www.w3.org/2001/XMLSchema">
     *   &lt;xs:restriction base="xs:string">
     *     &lt;xs:enumeration value="read"/>
     *     &lt;xs:enumeration value="write"/>
     *     &lt;xs:enumeration value="changePermission"/>
     *   &lt;/xs:restriction>
     * &lt;/xs:simpleType>
     * </pre>
     */
    public static enum Service {
        READ("read"), WRITE("write"), CHANGE_PERMISSION("changePermission");
        private final String value;

        private Service(String value) {
            this.value = value;
        }

        public String toString() {
            return value;
        }

        public static Service convert(String value) {
            for (Service inst : values()) {
                if (inst.toString().equals(value)) {
                    return inst;
                }
            }
            return null;
        }
    }
}
