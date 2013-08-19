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

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.dataone.configuration.Settings;

public class AuditLogClientSolrImpl implements AuditLogClient {

    private static Logger log = Logger.getLogger(AuditLogClientSolrImpl.class.getName());

    private static final String AUDIT_LOG_URL = Settings.getConfiguration().getString(
            "cn.audit.log.url", "http://localhost:8983/solr4/cn-audit/");

    private static CommonsHttpSolrServer server = null;

    static {
        try {
            server = new CommonsHttpSolrServer(AUDIT_LOG_URL);
        } catch (MalformedURLException e) {
            log.error("Exception attempting to create common solr server", e);
        }
    }

    public AuditLogClientSolrImpl() {
    }

    public boolean logAuditEvent(AuditLogEntry logEntry) {
        boolean success = false;
        try {
            //server.addBean(logEntry, 1000);
            server.addBean(logEntry);
            server.commit();
            success = true;
        } catch (SolrServerException e) {
            log.error("exception attempting to ADD audit event: " + logEntry.getEvent()
                    + " for pid: " + logEntry.getPid() + " and node: " + logEntry.getNodeId()
                    + " to audit log", e);
        } catch (IOException e) {
            log.error("exception attempting to ADD audit event: " + logEntry.getEvent()
                    + " for pid: " + logEntry.getPid() + " and node: " + logEntry.getNodeId()
                    + " to audit log", e);
        }
        return success;
    }

    public String queryLog(String query, Integer start, Integer rows) {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery(query);
        solrQuery.setStart(start);
        solrQuery.setRows(rows);

        String returnVal = "";
        try {
            QueryResponse response = server.query(solrQuery);
            returnVal = response.toString();
        } catch (SolrServerException e) {
            log.error("exception querying audit log", e);
        }
        return returnVal;
    }

    public String queryLog(AuditLogEntry logEntry, Integer start, Integer rows) {
        if (logEntry == null) {
            return "";
        }

        String queryString = createIntersectionQueryString(logEntry);
        if (queryString.isEmpty()) {
            return "";
        }

        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery(queryString);
        solrQuery.setStart(start);
        solrQuery.setRows(rows);

        String returnVal = "";
        try {
            QueryResponse response = server.query(solrQuery);
            returnVal = response.toString();
        } catch (SolrServerException e) {
            log.error("exception querying audit log", e);
        }
        return returnVal;
    }

    public boolean removeReplicaAuditEvent(AuditLogEntry logEntry) {
        if (logEntry == null) {
            return true;
        }
        String deleteQuery = createIntersectionQueryString(logEntry);

        if (deleteQuery.isEmpty()) {
            return true;
        }

        boolean success = false;
        try {
            // server.deleteByQuery(deleteQuery, 1000);
            server.deleteByQuery(deleteQuery);
            server.commit();
            success = true;
        } catch (SolrServerException e) {
            log.error("exception attempting to DELETE audit event: " + logEntry.getEvent()
                    + " for pid: " + logEntry.getPid() + " and node: " + logEntry.getNodeId()
                    + " to audit log", e);
        } catch (IOException e) {
            log.error("exception attempting to DELETE audit event: " + logEntry.getEvent()
                    + " for pid: " + logEntry.getPid() + " and node: " + logEntry.getNodeId()
                    + " to audit log", e);
        }
        return success;
    }

    private String createIntersectionQueryString(AuditLogEntry logEntry) {
        List<String> fields = new ArrayList<String>();
        if (logEntry.getPid() != null) {
            fields.add("pid:" + ClientUtils.escapeQueryChars(logEntry.getPid()));
        }

        if (logEntry.getNodeId() != null) {
            fields.add("nodeId:" + ClientUtils.escapeQueryChars(logEntry.getNodeId()));
        }

        if (logEntry.getEvent() != null) {
            fields.add("event:" + ClientUtils.escapeQueryChars(logEntry.getEvent().toString()));
        }

        StringBuffer queryString = new StringBuffer();
        for (Iterator<String> iterator = fields.iterator(); iterator.hasNext();) {
            String field = iterator.next();
            queryString.append(field);
            if (iterator.hasNext()) {
                queryString.append(" AND ");
            }
        }

        return queryString.toString();
    }

    public static void main(String[] args) throws InterruptedException {
        AuditLogClient alc = AuditLogClientFactory.getAuditLogClient();
        System.out.println(alc.queryLog("*:*", null, 0));
        AuditLogEntry rale = new AuditLogEntry("test-pid-1", "urn:node:the616",
                AuditEvent.REPLICA_AUDIT_FAILED, "audit failed!");
        alc.logAuditEvent(rale);
        Thread.sleep(2000);
        System.out.println(alc.queryLog("*:*", null, 0));
        //        alc.removeReplicaAuditEvent(new AuditLogEntry("test-pid-1", null, null, null, null));
        //        Thread.sleep(2000);
        //System.out.println(alc.queryLog(new AuditLogEntry("test-pid-1", null, null, null, null),
        //        null, null));
    }
}
