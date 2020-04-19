package com.info.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.info.customer.dto.CustomerDto;
import com.info.customer.dto.UserLoginDto;
import com.info.customer.service.CustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller class which exposes APIs to perform customer operations
 * 
 * @author Satya Kolipaka
 * 
 */
@RestController
@RequestMapping("/info")
@Api(value = "Customer Controller")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	/**
	 * This API is validate given username and passoword of customer
	 * 
	 * @param userLoginDto
	 * @return
	 */
	@ApiOperation(value = "This API is validate given username and passoword of customer", response = String.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Logged in customer"),
			@ApiResponse(code = 401, message = "Wrong password"),
			@ApiResponse(code = 404, message = "Username is not found") })
	@PostMapping("/authenticate")
	public ResponseEntity<String> customerAuthenticate(
			@ApiParam(value = "UserLogin object to verify customer login information", required = true) @RequestBody final UserLoginDto userLoginDto) {
		return new ResponseEntity<String>(customerService.customerAuthenticate(userLoginDto), HttpStatus.OK);
	}

	/**
	 * Retrieve the customer information base on Id
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "Get Customer details by Id", response = CustomerDto.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Customer details"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 404, message = "Customer is not found for Id") })
	@GetMapping("/customer/{id}")
	public ResponseEntity<CustomerDto> retrieveCustomer(
			@ApiParam(value = "Customer Id for which customer to retrieve", required = true) @PathVariable final Long id) {
		return new ResponseEntity<CustomerDto>(customerService.retrieveCustomer(id), HttpStatus.OK);
	}

	/**
	 * Create new customer details
	 * 
	 * @param customerDto
	 * @return
	 */
	@ApiOperation(value = "Add Customer details", response = CustomerDto.class)
	@PostMapping("/customer")
	public ResponseEntity<CustomerDto> createCustomer(
			@ApiParam(value = "Customer object to store in Database", required = true) @RequestBody final CustomerDto customerDto) {
		return new ResponseEntity<CustomerDto>(customerService.createCustomer(customerDto), HttpStatus.CREATED);
	}

	/**
	 * Update the customer details based in Id
	 * 
	 * @param id
	 * @param customerDto
	 * @return
	 */
	@ApiOperation(value = "Update Customer details by Id", response = CustomerDto.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated Customer details"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 404, message = "Customer is not found for Id") })
	@PutMapping("/customer/{id}")
	public ResponseEntity<CustomerDto> modifyCustomer(
			@ApiParam(value = "Customer Id for which customer to retrieve", required = true) @PathVariable final Long id,
			@ApiParam(value = "Customer object to update in Database", required = true) @RequestBody final CustomerDto customerDto) {
		return new ResponseEntity<CustomerDto>(customerService.modifyCustomer(id, customerDto), HttpStatus.OK);
	}

	/**
	 * Delete the customer data by Id
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "Delete Customer data by Id", response = Boolean.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully detleted Customer details"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 404, message = "Customer is not found for Id") })
	@DeleteMapping("/customer/{id}")
	public ResponseEntity<Boolean> removeCustomer(
			@ApiParam(value = "Customer Id for which customer to retrieve", required = true) @PathVariable final Long id) {
		return new ResponseEntity<Boolean>(customerService.removeCustomer(id), HttpStatus.OK);
	}

	/**
	 * Retrieve top 3 customers based on date of birth descending
	 * 
	 * @return
	 */
	@ApiOperation(value = "Get top 3 Customer details by descending order of date of birth", response = List.class)
	@GetMapping("/customers")
	public ResponseEntity<List<CustomerDto>> retrieveTopThreeCustomers() {
		return new ResponseEntity<List<CustomerDto>>(customerService.retrieveTopThreeCustomers(), HttpStatus.OK);
	}
}
