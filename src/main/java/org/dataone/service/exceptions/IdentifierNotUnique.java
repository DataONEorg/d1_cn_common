package org.dataone.service.exceptions;

import java.util.TreeMap;

/**
 * The DataONE IdentifierNotUnique exception, raised when a requested 
 * identifier has already been used or reserved.  Clients need to choose a
 * different identifier for the operation to complete successfully.
 * 
 * @author Matthew Jones
 */
public class IdentifierNotUnique extends BaseException {

    /** Fix the errorCode in this exception. */
    private static final int errorCode=409;
    
    public IdentifierNotUnique(int detailCode, String description) {
        super(errorCode, detailCode, description);
    }

    public IdentifierNotUnique(int detailCode, String description, 
            TreeMap<String, String> trace_information) {
        super(errorCode, detailCode, description);
    }
}
