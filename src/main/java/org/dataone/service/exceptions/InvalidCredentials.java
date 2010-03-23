package org.dataone.service.exceptions;

import java.util.TreeMap;

/**
 * The DataONE InvalidCredentials exception, raised when a request 
 * is issued for which the credentials were not validated.
 * 
 * @author Matthew Jones
 */
public class InvalidCredentials extends BaseException {

    /** Fix the errorCode in this exception. */
    private static final int errorCode=401;
    
    public InvalidCredentials(int detailCode, String description) {
        super(errorCode, detailCode, description);
    }

    public InvalidCredentials(int detailCode, String description, 
            TreeMap<String, String> trace_information) {
        super(errorCode, detailCode, description);
    }
}
