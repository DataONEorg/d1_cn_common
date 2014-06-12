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
 * Audit log writing client interface.  Defines the usage inteface to the audit log.
 * Hides the implementation choice of the audit log data store.
 * 
 * @author sroseboo
 *
 */
public interface AuditLogWriteClient {

    /**
     * Add a new log entry to the audit log.
     * 
     * @param logEntry
     * @return success/failure of call.
     */
    public boolean logAuditEvent(AuditLogEntry logEntry);

    /**
     * Removes audit log entries based on the pid, nodeId, event type contained in
     * AuditLogEntry parameter logEntry.
     * 
     * @param logEntry
     * @return success/fail of operation.
     */
    public boolean removeReplicaAuditEvent(AuditLogEntry logEntry);
}