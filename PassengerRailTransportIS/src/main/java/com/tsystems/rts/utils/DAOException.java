package com.tsystems.rts.utils;

public class DAOException extends Exception {
	
	private static final long serialVersionUID = 3645019667823241534L;

	public DAOException() {
	}
	        
	public DAOException(String message) {
        super(message);
    }
	        
	public DAOException(Throwable cause) {
        super(cause);
    }
	
	public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
