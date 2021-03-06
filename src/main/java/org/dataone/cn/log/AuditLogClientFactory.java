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
 * Factory pattern class to hide implementation of AuditLogClient.
 * 
 * Current factory creates a static instance of a solr 
 * implementation of the AuditLogClient.
 * @author sroseboo
 *
 */
public class AuditLogClientFactory {

    //private static AuditLogClient auditLogClient = new AuditLogClientSolrImpl();
    private static AuditLogClient auditLogClient = new AuditLogClientSplunkImpl();

    private AuditLogClientFactory() {
    }

    public static AuditLogClient getAuditLogClient() {
        return auditLogClient;
    }
}
