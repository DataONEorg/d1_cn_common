/**
 * This work was created by participants in the DataONE project, and is
 * jointly copyrighted by participating institutions in DataONE. For
 * more information on DataONE, see our web site at http://dataone.org.
 *
 *   Copyright 2012. All rights reserved.
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
 */

package org.dataone.cn;

import org.dataone.configuration.Settings;

/**
 * Class to capture common replication utility logic.
 * 
 * @author sroseboo
 * 
 */
public class ComponentActivationUtility {

    private ComponentActivationUtility() {
    }

    public static boolean replicationIsActive() {
        return replicationComponentActive();
        // && !ClusterPartitionMonitor.getProcessingPartition() &&
        // !ClusterPartitionMonitor.getStoragePartion();
    }

    public static boolean synchronizationIsActive() {
        return sychronizationComponentActive();
        // && !ClusterPartitionMonitor.getProcessingPartition()&&
        // !ClusterPartitionMonitor.getStoragePartion();
    }

    private static boolean replicationComponentActive() {
        return Settings.getConfiguration().getBoolean("Replication.active");
    }

    private static boolean sychronizationComponentActive() {
        return Settings.getConfiguration().getBoolean("Synchronization.active");
    }
}
