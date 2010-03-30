package org.dataone.service.exceptions;

import java.util.TreeMap;

/**
 * The DataONE NotImplemented exception, raised when a request 
 * is issued for a service that is not implemented by this node.
 * 
 * @author Matthew Jones
 */
public class NotImplemented extends BaseException {

    /** Fix the errorCode in this exception. */
    private static final int errorCode=400;
    
    public NotImplemented(int detailCode, String description) {
        super(errorCode, detailCode, description);
    }

    public NotImplemented(int detailCode, String description, 
            TreeMap<String, String> trace_information) {
        super(errorCode, detailCode, description);
    }
}
