/**
 * Copyright 2010 Regents of the University of California and the
 *                National Center for Ecological Analysis and Synthesis
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.dataone.service.exceptions;

import java.util.Set;
import java.util.TreeMap;

/**
 * A BaseException is the root of all DataONE service class exception messages.
 * Each exception contains a code, which typically corresponds to a high-level
 * HTTP error code, a detail_code, which classifies the exception to a more
 * constrained subtype of error, and a description of the error.  It can also
 * contain additional trace information used to debug the problem, which is
 * stored as a set of name-value pairs.
 *  
 * @author Matthew Jones
 */
public class BaseException extends Exception {
    
    /** The major error code associated with this exception. */
    private int code;
    
    /** The detailed error subcode associated with this exception. */
    private int detail_code;
    
    /** Additional trace-level debugging information, as name-value pairs. */
    private TreeMap<String, String> trace_information;

    public final static int FMT_XML = 0;
    public final static int FMT_JSON = 1;
    public final static int FMT_HTML = 2;
    
    /**
     * Construct a BaseException with the given code, detail code, and description.
     * 
     * @param code the code used to classify the exception
     * @param detail_code the detailed code for this exception
     * @param description the description of this exception
     */
    protected BaseException(int code, int detail_code, String description) {
        super(description);
        this.trace_information = new TreeMap<String, String>();
        this.code = code;
        this.detail_code = detail_code;
    }
    
    /**
     * Construct a BaseException with the given code, detail code, description,
     * and trace_information.
     * 
     * @param code the code used to classify the exception
     * @param detail_code the detailed code for this exception
     * @param description the description of this exception
     * @param trace_information containing a Map of key/value pairs
     */
    protected BaseException(int code, int detail_code, String description, 
            TreeMap<String, String> trace_information) {
        this(code, detail_code, description);
        this.trace_information = trace_information;
    }
    
    /**
     * @param code the code to set
     */
    protected void setCode(int code) {
        this.code = code;
    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param detail_code the detail_code to set
     */
    public void setDetail_code(int detail_code) {
        this.detail_code = detail_code;
    }

    /**
     * @return the detail_code
     */
    public int getDetail_code() {
        return detail_code;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return getMessage();
    }
    
    /**
     * Add new detailed trace information, storing it the value under a known key.
     * @param key the key to index the trace value
     * @param value that trace value to be stored
     */
    public void addTraceDetail(String key, String value) {
        trace_information.put(key, value);
    }
    
    /**
     * Get the value of one of the trace detail parametersm retrieved by its 
     * key value.
     * @param key the key of the trace information to be retrieved
     * @return the String key value associated with the key
     */
    public String getTraceDetail(String key) {
        return trace_information.get(key);
    }
    
    /**
     * Return a Set representation of all of the keys for the trace values in 
     * this exception.
     * @return Set representation of all of the keys for the trace values
     */
    public Set<String> getTraceKeySet() {
        return trace_information.keySet();
    }
    
    /**
     * Serialize the format in XML, JSON, or HTML format. If format is unknown,
     * then return in XML format.
     * @param format an integer code indicating the format
     */
    public String serialize(int format) {
        switch(format) {
        case FMT_XML:
            return serializeXML();
        case FMT_JSON:
            return serializeJSON();
        case FMT_HTML:
            return serializeHTML();
        default:
            return serializeXML();
        }
    }
    
    /** Serialize the exception in XML format. */
    private String serializeXML() {
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\"?>\n"); 
        sb.append("<error errorCode='").append(getCode()).append("' ");
        sb.append("detailCode='").append(getDetail_code()).append("'>\n");
        sb.append("  <description>").append(getDescription()).append("</description>\n");
        sb.append("  <traceInformation>\n");
        for (String key : this.getTraceKeySet()) {
            sb.append("    <value key='").append(key).append("'>");
            sb.append(trace_information.get(key)).append("</value>\n");
        }
        sb.append("  </traceInformation>\n");
        sb.append("</error>\n");
      
        return sb.toString();
    }
    
    /** Serialize the exception in JSON format.
     * TODO: Implement JSON serialization 
     */
    private String serializeJSON() {
        StringBuffer sb = new StringBuffer();
        sb.append("{'errorCode': ").append(getCode()).append(",\n");
        sb.append(" 'detailCode': ").append(getDetail_code()).append(",\n");
        sb.append(" 'description': '").append(getDescription()).append("',\n");
        sb.append(" 'traceInformation': {\n");
        for (String key : this.getTraceKeySet()) {
            sb.append("    '").append(key).append("': '");
            sb.append(trace_information.get(key)).append("',\n");
        }
        sb.append("  }\n");
        sb.append("}\n");
        return sb.toString();
    }
    
    /** Serialize the exception in HTML format.
     * TODO: Implement HTML serialization
     */
    private String serializeHTML() {
        StringBuffer sb = new StringBuffer();
        sb.append("<html>\n<body>\n"); 
        sb.append("  <p>\n");
        sb.append("    <dl>\n");
        sb.append("      <dt>Code</dt><dd class='errorCode'>").append(getCode()).append("</dd>\n");
        sb.append("      <dt>Detail Code</dt><dd class='detailCode'>").append(getDetail_code()).append("</dd>\n");
        sb.append("    </dl>\n");
        sb.append("  </p>\n");
        sb.append("  <p class='description'>").append(getDescription()).append("</p>\n");
        sb.append("  <div class='traceInformation'>\n");
        for (String key : this.getTraceKeySet()) {
            sb.append("    <dt>").append(key).append("</dt>\n");
            sb.append("    <dd>").append(trace_information.get(key)).append("</dd>\n");
        }
        sb.append("  </div>\n");
        sb.append("</body>\n</html>\n");
        return sb.toString();
    }
}
