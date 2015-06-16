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
 * 
 * $Id$
 */

package org.dataone.cn.synchronization.types;

import java.io.Serializable;

/**
 * A localization of a DataONE object using the Identifier and NodeId
 * @author waltz
 */
public class SyncObject implements Serializable {

    private final String nodeId;
    private final String pid;
    private final String taskLabel;
    private Integer attempt = 1;

    public SyncObject(String nodeId, String pid) {
        this.nodeId = nodeId;
        this.pid = pid;
        /* the task label is used extensively for logging for several Task classes
         * so it is advantageous to set the value it once.  It will also be used
         * for testing equality and hashCode, so be careful of changing the implementation.
         */
        this.taskLabel = String.format("Task-%s-%s", nodeId, pid);
    }
    
    
    public String getNodeId() {
        return nodeId;
    }

    public String getPid() {
        return pid;
    }
    
    public String taskLabel() {
        return taskLabel;
    }

   /** The number of times this object has been attempted to be synchronized
    *  it may be attempted multiple times due to a lock being present
    *  or another process changing the systemMetadata before
    *  the call to the CN for updating succeeds
    */
    public Integer getAttempt() {
        return attempt;
    }

    public void setAttempt(Integer attempt) {
        this.attempt = attempt;
    }


    /**
     * the taskLabel property is derived from the nodeID and pid, so can be the
     * sole property used for generating the hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((taskLabel == null) ? 0 : taskLabel.hashCode());
        return result;
    }


    /**
     * the taskLabel property is derived from the nodeID and pid, so can be the
     * sole property used for equals()
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof SyncObject)) {
            return false;
        }
        SyncObject other = (SyncObject) obj;
        if (taskLabel == null) {
            if (other.taskLabel != null) {
                return false;
            }
        } else if (!taskLabel.equals(other.taskLabel)) {
            return false;
        }
        return true;
    }
    
}
