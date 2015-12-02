package com.tsystems.rts.utils;

/**
 * Custom exception, which is used to identify all business logic exceptions.
 * @author Anton
 * @version 0.0.1
 *
 */
public class ServiceException extends Exception {
	
	private static final long serialVersionUID = 3645019667823241534L;

	public ServiceException() {
	}
	        
	public ServiceException(String message) {
        super(message);
    }
	        
	public ServiceException(Throwable cause) {
        super(cause);
    }
	
	public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
	
}
