package org.dataone.service.exceptions;

import java.util.TreeMap;

/**
 * The DataONE ServiceFailure exception, raised when a requested 
 * service was called but failed to execute the service request due to an
 * internal error, such as misconfiguration, or a missing resource such as 
 * a missing data connection, etc.  
 * @author Matthew Jones
 */
public class ServiceFailure extends BaseException {

    /** Fix the errorCode in this exception. */
    private static final int errorCode=500;
    
    public ServiceFailure(int detailCode, String description) {
        super(errorCode, detailCode, description);
    }

    public ServiceFailure(int detailCode, String description, 
            TreeMap<String, String> trace_information) {
        super(errorCode, detailCode, description);
    }
}
