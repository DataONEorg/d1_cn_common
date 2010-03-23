package org.dataone.service.exceptions;

import java.util.TreeMap;

/**
 * The DataONE AuthenticationTimeout exception, raised when authentication 
 * operations exceed pre-established limits.
 * 
 * @author Matthew Jones
 */
public class AuthenticationTimeout extends BaseException {

    /** Fix the errorCode in this exception. */
    private static final int errorCode=408;
    
    public AuthenticationTimeout(int detailCode, String description) {
        super(errorCode, detailCode, description);
    }

    public AuthenticationTimeout(int detailCode, String description, 
            TreeMap<String, String> trace_information) {
        super(errorCode, detailCode, description);
    }
}
