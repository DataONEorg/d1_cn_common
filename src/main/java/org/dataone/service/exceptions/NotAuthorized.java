package org.dataone.service.exceptions;

import java.util.TreeMap;

/**
 * The DataONE NotAuthorized exception, raised when the supplied identity 
 * information is not authorized for the requested operation.
 * 
 * @author Matthew Jones
 */
public class NotAuthorized extends BaseException {

    /** Fix the errorCode in this exception. */
    private static final int errorCode=401;
    
    public NotAuthorized(int detailCode, String description) {
        super(errorCode, detailCode, description);
    }

    public NotAuthorized(int detailCode, String description, 
            TreeMap<String, String> trace_information) {
        super(errorCode, detailCode, description);
    }
}
