package org.dataone.service.exceptions;

import java.util.TreeMap;

/**
 * The DataONE InsufficientResources exception, raised when insufficient space,
 * bandwidth, or other resources are available to complete a request.
 * 
 * @author Matthew Jones
 */
public class InsufficientResources extends BaseException {

    /** Fix the errorCode in this exception. */
    private static final int errorCode=413;
    
    public InsufficientResources(int detailCode, String description) {
        super(errorCode, detailCode, description);
    }

    public InsufficientResources(int detailCode, String description, 
            TreeMap<String, String> trace_information) {
        super(errorCode, detailCode, description);
    }
}
