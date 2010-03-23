package org.dataone.service.exceptions;

import java.util.TreeMap;

/**
 * The DataONE UnsupportedMetadataType exception, raised when the science 
 * metadata document submitted is not of a type that is recognized by 
 * the DataONE system.
 *  
 * @author Matthew Jones
 */
public class UnsupportedMetadataType extends BaseException {

    /** Fix the errorCode in this exception. */
    private static final int errorCode=400;
    
    public UnsupportedMetadataType(int detailCode, String description) {
        super(errorCode, detailCode, description);
    }

    public UnsupportedMetadataType(int detailCode, String description, 
            TreeMap<String, String> trace_information) {
        super(errorCode, detailCode, description);
    }
}
