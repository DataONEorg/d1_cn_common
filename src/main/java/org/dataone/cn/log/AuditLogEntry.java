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

import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;

/**
 * AuditLogEntry model class.  Represents an audit log entry message.
 * Internal unique id property is not exposed through java class, just
 * provided for solr index - in order to have a unique field defined.
 * 
 * 
 * @author sroseboo
 *
 */
public class AuditLogEntry {

    private static final String seperatorChar = "|";

    @Field
    private String id;

    @Field
    private String pid;

    @Field
    private String nodeId;

    @Field
    private AuditEvent event;

    @Field
    private Date dateLogged;

    /**
     * Descriptive text for the log entry message.
     */
    @Field
    private String logText;

    public AuditLogEntry() {
    }

    public AuditLogEntry(String pid, String nodeId, AuditEvent event, Date dateLogged,
            String logText) {
        this.pid = pid;
        this.nodeId = nodeId;
        this.event = event;
        this.dateLogged = dateLogged;
        this.logText = logText;
        this.id = generateId();
    }

    public AuditLogEntry(String pid, String nodeId, AuditEvent event, String logText) {
        this(pid, nodeId, event, new Date(System.currentTimeMillis()), logText);
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public AuditEvent getEvent() {
        return event;
    }

    public void setEvent(AuditEvent event) {
        this.event = event;
    }

    public Date getDateLogged() {
        return dateLogged;
    }

    public void setDateLogged(Date dateLogged) {
        this.dateLogged = dateLogged;
    }

    public String getLogText() {
        return logText;
    }

    public void setLogText(String logText) {
        this.logText = logText;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    private String generateId() {
        StringBuffer id = new StringBuffer();
        if (this.pid != null) {
            id.append(this.pid + seperatorChar);
        }
        if (this.nodeId != null) {
            id.append(this.nodeId + seperatorChar);
        }
        if (this.event != null) {
            id.append(this.event + seperatorChar);
        }
        if (this.dateLogged != null) {
            id.append(this.dateLogged);
        }
        return id.toString();
    }
}
