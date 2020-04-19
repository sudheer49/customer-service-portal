package com.info.customer.exception;

/**
 * Custom exception class for UserName not found
 * @author Satya Kolipaka
 *
 */
public class UserNameNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public UserNameNotFoundException(String message) {
		super(message);
	}

}
