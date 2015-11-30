package com.tsystems.rts.utils;

/**
 * Custom exception, which is used to identify all business logic exceptions.
 * @author Anton
 * @version 0.0.1
 *
 */
public class BusinessLogicException extends Exception {
	
	private static final long serialVersionUID = 3645019667823241534L;

	public BusinessLogicException() {
	}
	        
	public BusinessLogicException(String message) {
        super(message);
    }
	        
	public BusinessLogicException(Throwable cause) {
        super(cause);
    }
	
	public BusinessLogicException(String message, Throwable cause) {
        super(message, cause);
    }
	
}
