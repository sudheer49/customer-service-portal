package com.info.customer.exception;

/**
 * Custom Exception class for Invalid Password
 * @author Satya Kolipaka
 *
 */
public class InvalidPasswordException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidPasswordException(String message) {
		super(message);
	}
	
}
