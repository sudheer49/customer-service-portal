package com.info.customer.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.info.customer.dto.CustomerDto;
import com.info.customer.dto.UserLoginDto;
import com.info.customer.entity.Customer;
import com.info.customer.exception.CustomerNotFoundException;
import com.info.customer.exception.InvalidPasswordException;
import com.info.customer.exception.UserNameNotFoundException;
import com.info.customer.repository.CustomerRepository;

/**
 * Service class for customer service portal
 * 
 * @author Satya Kolipaka
 *
 */
@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	
	/**
	 * This method validate login information of customer
	 * @param userLoginDto
	 * @return
	 * @throws InvalidPasswordException
	 * @throws UserNameNotFoundException
	 */
	public String customerAuthenticate(UserLoginDto userLoginDto) throws InvalidPasswordException,UserNameNotFoundException {
		Optional<Customer> customer = customerRepository.findByUserName(userLoginDto.getUserName());
		if(customer.isPresent()) {
			if(customer.get().getPassword().equals(userLoginDto.getPassword())) {
				return "Login Success";
			}
			else {
				throw new InvalidPasswordException("Wrong Password");
			}
		}
		else {
			throw new UserNameNotFoundException("There is no customer on given User Name");
		}
		
	}
	
	/**
	 * Retrieve the customer information base on Id
	 * @param id
	 * @return
	 * @throws CustomerNotFoundException
	 */
	public CustomerDto retrieveCustomer(Long id) throws CustomerNotFoundException {
		Optional<Customer> customer = customerRepository.findById(id);
		CustomerDto customerDto = null;
		if (customer.isPresent()) {
			customerDto = buildCustomerDto(customer.get());
		} else {
			throw new CustomerNotFoundException("No Customer Found for id:" + id);
		}
		return customerDto;
	}

	/**
	 * Create new customer details
	 * @param customerDto
	 * @return
	 */
	@Transactional
	public CustomerDto createCustomer(CustomerDto customerDto) {
		Customer customer = new Customer(customerDto.getFirstName(), customerDto.getLastName(),
				customerDto.getPassword(), customerDto.getDateOfBirth());
		customer.setUserName(customerDto.getFirstName()+"123");
		final Customer savedCustomer = customerRepository.save(customer);
		if (savedCustomer != null) {
			return buildCustomerDto(savedCustomer);
		}
		return null;
	}

	/**
	 * Update the customer details based in Id
	 * @param id
	 * @param customerDto
	 * @return
	 */
	@Transactional
	public CustomerDto modifyCustomer(Long id, CustomerDto customerDto) {
		Optional<Customer> customer = customerRepository.findById(id);
		if (customer.isPresent()) {
			Customer customerEntity = customer.get();
			customerEntity.setFirstName(customerDto.getFirstName());
			customerEntity.setLastName(customerDto.getLastName());
			customerEntity.setPassword(customerDto.getPassword());
			customerEntity.setDateOfBirth(customerDto.getDateOfBirth());
			final Customer savedCustomer = customerRepository.save(customerEntity);
			return buildCustomerDto(savedCustomer);
		} else {
			throw new CustomerNotFoundException("No Customer Found for id:" + id);
		}
	}

	/**
	 * Delete the customer data by Id
	 * @param id
	 * @return
	 */
	@Transactional
	public Boolean removeCustomer(Long id) {
		Optional<Customer> customer = customerRepository.findById(id);
		if (customer.isPresent()) {
			customerRepository.delete(customer.get());
			return true;
		} else {
			throw new CustomerNotFoundException("No Customer Found for id:" + id);
		}

	}

	/**
	 * Retrieve top 3 customers based on date of birth descending
	 * @return
	 */
	public List<CustomerDto> retrieveTopThreeCustomers() {
		Pageable sortedByDateOfBirthDesc = PageRequest.of(0, 3, Sort.by("dateOfBirth").descending());
		Page<Customer> pageCustomer = customerRepository.findAll(sortedByDateOfBirthDesc);
		List<Customer> customers = pageCustomer.getContent();
		List<CustomerDto> customerDtoList = customers.stream().map(customer -> buildCustomerDto(customer))
				.collect(Collectors.toList());
		if (customerDtoList.isEmpty()) {
			throw new CustomerNotFoundException("No Customers Found");
		}
		return customerDtoList;
	}
	
	/**
	 * Build CustomerDto Object from Customer Object
	 * @param customer
	 * @return
	 */
	private CustomerDto buildCustomerDto(Customer customer) {
		return new CustomerDto(customer.getFirstName(), customer.getLastName(), customer.getPassword(),
				customer.getDateOfBirth());
	}

	
}
