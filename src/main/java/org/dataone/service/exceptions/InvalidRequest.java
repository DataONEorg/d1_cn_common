package org.dataone.service.exceptions;

import java.util.TreeMap;

/**
 * The DataONE InvalidRequest exception, raised when a request 
 * is issued for which one or more parameters provided in the call were invalid.
 * 
 * @author Matthew Jones
 */
public class InvalidRequest extends BaseException {

    /** Fix the errorCode in this exception. */
    private static final int errorCode=400;
    
    public InvalidRequest(int detailCode, String description) {
        super(errorCode, detailCode, description);
    }

    public InvalidRequest(int detailCode, String description, 
            TreeMap<String, String> trace_information) {
        super(errorCode, detailCode, description);
    }
}
