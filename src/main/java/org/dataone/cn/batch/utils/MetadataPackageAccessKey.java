/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.dataone.cn.batch.utils;

/**
 *
 * @author waltz
 */
public enum MetadataPackageAccessKey {

    SKIP_IN_LOG_FIELD("skipInLogFile"),
    DATE_TIME_LAST_ACCESSED_FIELD("dateTimeLastAccessed"),
    SCIMETA("SCIMETA"),
    SYSMETA("SYSMETA");
        private final String value;

    private MetadataPackageAccessKey(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    public static MetadataPackageAccessKey convert(String value) {
        for (MetadataPackageAccessKey inst : values()) {
            if (inst.toString().equals(value)) {
                return inst;
            }
        }
        return null;
    }
}
