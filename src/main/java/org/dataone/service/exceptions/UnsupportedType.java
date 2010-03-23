package org.dataone.service.exceptions;

import java.util.TreeMap;

/**
 * The DataONE UnsupportedType exception, raised when the information 
 * presented appears to be unsupported. 
 *  
 * @author Matthew Jones
 */
public class UnsupportedType extends BaseException {

    /** Fix the errorCode in this exception. */
    private static final int errorCode=400;
    
    public UnsupportedType(int detailCode, String description) {
        super(errorCode, detailCode, description);
    }

    public UnsupportedType(int detailCode, String description, 
            TreeMap<String, String> trace_information) {
        super(errorCode, detailCode, description);
    }
}
