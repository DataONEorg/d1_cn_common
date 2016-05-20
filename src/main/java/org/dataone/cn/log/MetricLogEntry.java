/**
 * This work was created by participants in the DataONE project, and is jointly copyrighted by participating
 * institutions in DataONE. For more information on DataONE, see our web site at http://dataone.org.
 *
 * Copyright ${year}
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.dataone.cn.log;

import java.util.Date;

import org.dataone.service.types.v1.Identifier;
import org.dataone.service.types.v1.NodeReference;
import org.dataone.service.util.DateTimeMarshaller;

/**
 * Represents an metric log entry message. The entry will be serialized as a JSON message to a log file that is sent to
 * a log analysis engine
 *
 * Minimally, the JSON structure will return the event and the date and time the event occurred.
 *
 * Currently, the message is serialized in the toString method. However, this class may easily be annotated for Jackson.
 * There is also the Java API for JSON bindings in Java EE (but why do we want to install that?), and GSON Google's Json
 * library.
 *
 * @author waltz
 *
 */
public class MetricLogEntry {

    /**
     * The identifier of the object to which the log refers if the log message does not associate with any particular
     * object then it is left null and is not serialized
     */
    private Identifier pid;

    /**
     * The identifier of the node to which the log refers if the log message does not associate with any particular MN
     * or CN then it is left null and is not serialized
     *
     */
    private NodeReference nodeId;

    /**
     * Every message must be tied to an event. Default event is PING
     */
    private MetricEvent event;

    /**
     * The time to complete the event in Milliseconds If the event to track has multiple steps, such as the full
     * processing of a task to synchronize an object, or the annotation of a log entry, then use this field to measure
     * the milliseconds from start to finish.
     *
     */
    private Long timeToCompleteEventMS;
    
    /* 
     * thread name
     * 
    */
    private String threadName = Thread.currentThread().getName();
    
    /* 
     * thread id
     * 
    */    
    private long threadId = Thread.currentThread().getId();
    
    /**
     * The date that the event occurred
     *
     */
    private Date dateLogged = new Date(System.currentTimeMillis());

    /**
     * Descriptive message for the event Provides details that are not covered by the other fields
     *
     */
    private String message;

    public MetricLogEntry(MetricEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("MetricEvent may not be null");
        }
        this.event = event;
    }

    public MetricLogEntry(MetricEvent event, NodeReference nodeId) {
        this(event);
        this.nodeId = nodeId;
    }

    public MetricLogEntry(MetricEvent event, NodeReference nodeId, Identifier pid) {
        this(event, nodeId);
        this.pid = pid;
    }

    public MetricLogEntry(MetricEvent event, NodeReference nodeId, Identifier pid, String message) {
        this(event, nodeId, pid);
        this.message = message;
    }

    public MetricLogEntry(MetricEvent event, NodeReference nodeId, Identifier pid, String message, Long timeToCompleteEventMS) {
        this(event, nodeId, pid, message);
        this.timeToCompleteEventMS = timeToCompleteEventMS;
    }

    public MetricLogEntry(MetricEvent event, String message, NodeReference nodeId,
            Identifier pid, Long timeToCompleteEventMS, Date dateLogged) {
        this(event, nodeId, pid, message, timeToCompleteEventMS);
        if (dateLogged != null) {
            this.dateLogged = dateLogged;
        }
    }

    public Identifier getPid() {
        return pid;
    }

    public void setPid(Identifier pid) {
        this.pid = pid;
    }

    public NodeReference getNodeId() {
        return nodeId;
    }

    public void setNodeId(NodeReference nodeId) {
        this.nodeId = nodeId;
    }

    public MetricEvent getEvent() {
        return event;
    }

    public void setEvent(MetricEvent event) {
        this.event = event;
    }

    public Date getDateLogged() {
        return dateLogged;
    }

    public void setDateLogged(Date dateLogged) {
        this.dateLogged = dateLogged;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimeToCompleteEventMS() {
        return timeToCompleteEventMS;
    }

    public void setTimeToCompleteEventMS(Long timeToCompleteEventMS) {
        this.timeToCompleteEventMS = timeToCompleteEventMS;
    }

    /**
     * Transform the object into JSON
     *
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\"event\":\"");
        jsonBuilder.append(event.toString());
        jsonBuilder.append("\"");
        if (nodeId != null) {
            jsonBuilder.append(",\"nodeId\":\"");
            jsonBuilder.append(nodeId.getValue());
            jsonBuilder.append("\"");
        }
        if (pid != null) {
            jsonBuilder.append(",\"pid\":\"");
            jsonBuilder.append(pid.getValue());
            jsonBuilder.append("\"");
        }
        if (message != null) {
            jsonBuilder.append(",\"message\":\"");
            jsonBuilder.append(message);
            jsonBuilder.append("\"");
        }
        if (timeToCompleteEventMS != null) {
            jsonBuilder.append(",\"timeToCompleteEventMS\":");
            jsonBuilder.append(timeToCompleteEventMS);
        }
        jsonBuilder.append(",\"threadName\":\"");
        jsonBuilder.append(threadName);
        jsonBuilder.append("\"");   
        jsonBuilder.append(",\"threadId\":");
        jsonBuilder.append(threadId);  
        jsonBuilder.append(",\"dateLogged\":\"");
        jsonBuilder.append(DateTimeMarshaller.serializeDateToUTC(dateLogged));
        jsonBuilder.append("\"}");
        return jsonBuilder.toString();

    }
}
