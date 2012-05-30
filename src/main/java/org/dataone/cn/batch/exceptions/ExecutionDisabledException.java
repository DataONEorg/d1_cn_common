/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dataone.cn.batch.exceptions;

import java.util.concurrent.ExecutionException;

/**
 * A DataONE Job or Task Exception. 
 * Allows Tasks and Jobs to throw specific error that is recoverable.
 * Any processing component should check a property that toggles execution of 
 * the component, and if component is listed as disabled, the job
 * or task will throw this exception and allow the calling mechanism to
 * re-schedule rather than fail.
 * 
 * @author waltz
 */
public class ExecutionDisabledException extends ExecutionException{
    private static final long serialVersionUID = 139024629580553752L;
    
    /**
     * Create new instance of a DataONE ExecutionDisabledException to wrap Throwable
     * cause.
     * @param cause 
     */
    public ExecutionDisabledException(Throwable cause) {
        super(cause);
    }
    /**
     * Create new instance of a DataONE ExecutionDisabledException with message
     * @param cause 
     */
    public ExecutionDisabledException(String message) {
        super(message);
    }
    /**
     * Create new instance of a DataONE ExecutionDisabledException
     * @param cause 
     */
    public ExecutionDisabledException() {
        super();
    }
}
