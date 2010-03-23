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
    
    /** The description of this exception. */
    private String description;
    
    /** Additional trace-level debugging information, as name-value pairs. */
    private TreeMap<String, String> trace_information;

    /** Construct a BaseException, initializing the superclass. */
    private BaseException() {
        super();
        this.trace_information = new TreeMap<String, String>();
    }
    
    /**
     * Construct a BaseException with the given code, detail code, and description.
     * 
     * @param code the code used to classify the exception
     * @param detail_code the detailed code for this exception
     * @param description the description of this exception
     */
    protected BaseException(int code, int detail_code, String description) {
        this();
        this.code = code;
        this.detail_code = detail_code;
        this.description = description;
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
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
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
}
