package org.dataone.service.exceptions;

import java.util.TreeMap;

/**
 * The DataONE InvalidToken exception, raised when supplied authentication 
 * token could not be verified as being valid.
 * 
 * @author Matthew Jones
 */
public class InvalidToken extends BaseException {

    /** Fix the errorCode in this exception. */
    private static final int errorCode=401;
    
    public InvalidToken(int detailCode, String description) {
        super(errorCode, detailCode, description);
    }

    public InvalidToken(int detailCode, String description, 
            TreeMap<String, String> trace_information) {
        super(errorCode, detailCode, description);
    }
}
