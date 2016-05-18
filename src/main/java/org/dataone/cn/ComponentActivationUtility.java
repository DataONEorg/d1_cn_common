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

import java.util.concurrent.atomic.AtomicBoolean;
import org.dataone.configuration.Settings;

/**
 * Class to capture common component activation logic.
 * 
 * @author sroseboo
 * 
 */
public class ComponentActivationUtility {

    private static AtomicBoolean runSynchronization = new AtomicBoolean(true);
    
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

    public static boolean replicationMNAuditorIsActive() {
        return replicationMNAuditorComponenentActive();
    }

    public static boolean replicationCNAuditorIsActive() {
        return replicationCNAuditorComponenentActive();
    }
    
    /* 
     *  disable running of synchronization in
     *  case of catastrophic failure of a subcomponent
     *
     *  see https://redmine.dataone.org/issues/7706
    */
    public static boolean disableSynchronization() {
        return runSynchronization.compareAndSet(true, false);
    }
    
    private static boolean replicationComponentActive() {
        return Settings.getConfiguration().getBoolean("Replication.active");
    }

    private static boolean sychronizationComponentActive() {
        return Settings.getConfiguration().getBoolean("Synchronization.active") && runSynchronization.get();
    }

    private static boolean replicationMNAuditorComponenentActive() {
        return Settings.getConfiguration().getBoolean("Replication.audit.mn.active");
    }

    private static boolean replicationCNAuditorComponenentActive() {
        return Settings.getConfiguration().getBoolean("Replication.audit.cn.active");
    }
}
