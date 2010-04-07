/**
 * Copyright 2010 Regents of the University of California and the
 *                National Center for Ecological Analysis and Synthesis
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.dataone.service.types;

/**
 * The DataONE Type to represent an access rule.
 *
 * @author Matthew Jones
 */
public class AccessRule {
 
        private RuleType rule;
        private Service service;
        private PrincipalType principal;

        /** 
         * Get the 'RuleType' attribute value.
         * 
         * @return value
         */
        public RuleType getRule() {
            return rule;
        }

        /** 
         * Set the 'RuleType' attribute value.
         * 
         * @param ruleType
         */
        public void setRule(RuleType rule) {
            this.rule = rule;
        }

        /** 
         * Get the 'Service' attribute value.
         * 
         * @return value
         */
        public Service getService() {
            return service;
        }

        /** 
         * Set the 'Service' attribute value.
         * 
         * @param service
         */
        public void setService(Service service) {
            this.service = service;
        }

        /** 
         * Get the 'Principal' attribute value.
         * 
         * @return value
         */
        public PrincipalType getPrincipal() {
            return principal;
        }

        /** 
         * Set the 'Principal' attribute value.
         * 
         * @param principal
         */
        public void setPrincipal(PrincipalType principal) {
            this.principal = principal;
        }

        public static enum RuleType {
            ALLOW("allow"), DENY("deny");
            private final String value;

            private RuleType(String value) {
                this.value = value;
            }

            public String toString() {
                return value;
            }

            public static RuleType convert(String value) {
                for (RuleType inst : values()) {
                    if (inst.toString().equals(value)) {
                        return inst;
                    }
                }
                return null;
            }
        }

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
