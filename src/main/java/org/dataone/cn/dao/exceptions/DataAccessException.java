package org.dataone.cn.dao.exceptions;

/**
 * A DataONE Data Access Exception. Used to wrap implementation specific
 * exceptions that occur in Data Access Objects (DAO) implementation classes.
 * Allows DAO classes to catch implementation specific errors and re-throw them
 * as a checked DataONE exception.
 * 
 * @author sroseboo
 * 
 */
public class DataAccessException extends Exception {

    private static final long serialVersionUID = 208924174580554803L;

    /**
     * Create new instance of a DataONE DataAccessException to wrap Throwable
     * cause.
     */
    public DataAccessException(Throwable cause) {
        super(cause);
    }
}
