/**
 * This work was created by participants in the DataONE project, and is
 * jointly copyrighted by participating institutions in DataONE. For 
 * more information on DataONE, see our web site at http://dataone.org.
 *
 *   Copyright ${year}
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 * 
 * $Id$
 */
package org.dataone.cn.hazelcast;

import org.apache.commons.lang.StringUtils;
import org.dataone.configuration.Settings;

public class HazelcastConfigLocationFactory {

    private static final String DEFAULT_STORAGE_CLUSTER_CONFIG = "/etc/dataone/storage/hazelcast.xml";
    private static final String DEFAULT_PROCESS_CLUSTER_CONFIG = "/etc/dataone/process/hazelcast.xml";
    private static final String DEFAULT_SESSION_CLUSTER_CONFIG = "/etc/dataone/portal/hazelcast.xml";

    private static final String STORAGE_CLUSTER_OVERRIDE_PROPERTY = "dataone.hazelcast.location.clientconfig";
    private static final String PROCESS_CLUSTER_OVERRIDE_PROPERTY = "dataone.hazelcast.location.processing.clientconfig";

    public static String getStorageConfigLocation() {
        return getConfigLocation(DEFAULT_STORAGE_CLUSTER_CONFIG, STORAGE_CLUSTER_OVERRIDE_PROPERTY);
    }

    public static String getProcessingConfigLocation() {
        return getConfigLocation(DEFAULT_PROCESS_CLUSTER_CONFIG, PROCESS_CLUSTER_OVERRIDE_PROPERTY);
    }

    public static String getSessionConfigLocation() {
        return getConfigLocation(DEFAULT_SESSION_CLUSTER_CONFIG, null);
    }

    private static String getConfigLocation(String defaultLocation, String overrideProperty) {
        String configLocation = null;
        if (overrideProperty != null) {
            configLocation = Settings.getConfiguration().getString(overrideProperty);
        }
        if (StringUtils.isBlank(configLocation)) {
            configLocation = defaultLocation;
        }
        return configLocation;
    }
}
