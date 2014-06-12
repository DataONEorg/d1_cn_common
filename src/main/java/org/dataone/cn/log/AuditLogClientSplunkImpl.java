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

public class AuditLogClientSplunkImpl implements AuditLogClient {

    private AuditLogWriteClient writeClient = new AuditLogWriteClientSplunkImpl();
    private AuditLogQueryClient queryClient = new AuditLogQueryClientNullObjectImpl();

    public boolean logAuditEvent(AuditLogEntry logEntry) {
        return writeClient.logAuditEvent(logEntry);
    }

    public boolean removeReplicaAuditEvent(AuditLogEntry logEntry) {
        return writeClient.removeReplicaAuditEvent(logEntry);
    }

    public String queryLog(String query, Integer start, Integer rows) {
        return queryClient.queryLog(query, start, rows);
    }

    public String queryLog(AuditLogEntry logEntry, Integer start, Integer rows) {
        return queryClient.queryLog(logEntry, start, rows);
    }
}
