package org.dataone.service.exceptions;

import java.util.TreeMap;

/**
 * The DataONE InvalidSystemMetadata exception, raised when a request 
 * is issued and the supplied system metadata is invalid. This could be 
 * because some required field is not set, the metadata document is malformed, 
 * or the value of some field is not valid.
 * 
 * @author Matthew Jones
 */
public class InvalidSystemMetadata extends BaseException {

    /** Fix the errorCode in this exception. */
    private static final int errorCode=400;
    
    public InvalidSystemMetadata(int detailCode, String description) {
        super(errorCode, detailCode, description);
    }

    public InvalidSystemMetadata(int detailCode, String description, 
            TreeMap<String, String> trace_information) {
        super(errorCode, detailCode, description);
    }
}
