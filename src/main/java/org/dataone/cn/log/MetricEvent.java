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
 */
package org.dataone.cn.log;

/**
 * Metric Event enumeration.  An enumeration class for defining metric log event
 * messages.  Ensures consistent encoding of log events.
 * 
 * @author sroseboo, waltz
 *
 */
public enum MetricEvent {

    SYNCHRONIZATION_HARVEST_RETRIEVED("synchronization harvest retrieved"),
    SYNCHRONIZATION_HARVEST_SUBMITTED("synchronization harvest submitted"),
    SYNCHRONIZATION_QUEUED("synchronization queued"),
    SYNCHRONIZATION_TASK_EXECUTION("synchronization task execution"),
    LOG_AGGREGATION_HARVEST_RETRIEVED("log aggregation harvest retrieved"),
    LOG_AGGREGATION_HARVEST_SUBMITTED("log aggregation harvest submitted"),
    REPLICATION_TASKS("replication tasks"),
    REPLICA_STATUS("replication status"),
    ;

    private final String value;

    private MetricEvent(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
