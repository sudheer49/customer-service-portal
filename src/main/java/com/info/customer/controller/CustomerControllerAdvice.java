package com.info.customer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.info.customer.exception.CustomerNotFoundException;
import com.info.customer.exception.InvalidPasswordException;
import com.info.customer.exception.UserNameNotFoundException;

/**
 * This class in a controller advise that is executed when the exception is thrown 
 * in controller classes. It also helps to convert the exception into valid Errors 
 * to be returned with appropriate HTTP code in response
 * @author Satya Kolipaka
 *
 */
@ControllerAdvice
public class CustomerControllerAdvice {

	/**
	 * This method helps to build exception when no customer found
	 * @param exception
	 * @return
	 */
	@ExceptionHandler({CustomerNotFoundException.class,UserNameNotFoundException.class})
	public ResponseEntity<String> customerNotFoundException(final Exception exception) {
		return new ResponseEntity<String>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	/**
	 * This method helps to build exception when invalid or missing json request
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<String> httpMessageNotReadableException(final HttpMessageNotReadableException exception) {
		return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<String> httpMessageNotReadableException(final InvalidPasswordException exception) {
		return new ResponseEntity<String>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
	}
}
