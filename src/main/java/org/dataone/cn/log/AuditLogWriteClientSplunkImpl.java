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

import org.apache.log4j.Logger;

public class AuditLogWriteClientSplunkImpl implements AuditLogWriteClient {

    private static Logger splunkLog = Logger.getLogger(AuditLogWriteClientSplunkImpl.class
            .getName());

    public boolean logAuditEvent(AuditLogEntry logEntry) {
        if (logEntry != null) {
            splunkLog.error(logEntry.toString());
        }
        return true;
    }

    public boolean removeReplicaAuditEvent(AuditLogEntry logEntry) {
        return true;
    }

}
