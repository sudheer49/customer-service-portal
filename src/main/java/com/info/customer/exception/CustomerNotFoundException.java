package com.info.customer.exception;

/**
 * Exception class for customer not found 
 * @author Satya Kolipaka
 *
 */
public class CustomerNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public CustomerNotFoundException(String message) {
		super(message);
	}

}
