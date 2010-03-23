package org.dataone.service.exceptions;

import java.util.TreeMap;

/**
 * The DataONE NotFound exception, raised when an object is not present 
 * on the node where the exception was raised.
 *  
 * @author Matthew Jones
 */
public class NotFound extends BaseException {

    /** Fix the errorCode in this exception. */
    private static final int errorCode=404;
    
    public NotFound(int detailCode, String description) {
        super(errorCode, detailCode, description);
    }

    public NotFound(int detailCode, String description, 
            TreeMap<String, String> trace_information) {
        super(errorCode, detailCode, description);
    }
}
