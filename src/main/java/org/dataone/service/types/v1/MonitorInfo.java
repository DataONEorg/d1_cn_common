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

package org.dataone.service.types.v1;

import java.io.Serializable;
import java.sql.Date;

/** 
 * A single log entry as reported by a 
 Member Node or Coordinating Node through the getMonitorInfos methods.

 * 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://ns.dataone.org/service/types/v1" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="MonitorInfo">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="xs:date" name="date" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:int" name="count" minOccurs="1" maxOccurs="1"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class MonitorInfo implements Serializable
{
    private static final long serialVersionUID = 10000000;
    private Date date;
    private int count;

    /** 
     * Get the 'date' element value.
     * 
     * @return value
     */
    public Date getDate() {
        return date;
    }

    /** 
     * Set the 'date' element value.
     * 
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /** 
     * Get the 'count' element value.
     * 
     * @return value
     */
    public int getCount() {
        return count;
    }

    /** 
     * Set the 'count' element value.
     * 
     * @param count
     */
    public void setCount(int count) {
        this.count = count;
    }
}
