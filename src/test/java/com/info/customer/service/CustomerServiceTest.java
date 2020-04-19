package com.info.customer.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.info.customer.dto.CustomerDto;
import com.info.customer.entity.Customer;
import com.info.customer.exception.CustomerNotFoundException;
import com.info.customer.repository.CustomerRepository;

/**
 * Unit test class for CustomerService class
 * @author Satya Kolipaka
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTest {

	@MockBean
	private CustomerRepository customerRepositoryMock;

	@Autowired
	private CustomerService customerService;

	@Test
	public void retrieveCustomerSuccess() {
		Optional<Customer> customer = Optional.of(buildCustomer());
		when(customerRepositoryMock.findById(1L)).thenReturn(customer);
		CustomerDto response = customerService.retrieveCustomer(1L);

		Assert.assertEquals("Satya", response.getFirstName());
		Assert.assertEquals("Kolipaka", response.getLastName());
		Assert.assertEquals("password", response.getPassword());
		Assert.assertEquals(LocalDate.parse("1992-06-06"), response.getDateOfBirth());
	}

	@Test(expected = CustomerNotFoundException.class)
	public void retrieveCustomerFailure() {
		CustomerDto response = customerService.retrieveCustomer(1L);

		Assert.assertEquals("Satya", response.getFirstName());
		Assert.assertEquals("Kolipaka", response.getLastName());
	}

	@Test
	public void createCustomerTest() {
		CustomerDto customerDto = buildCustomerDto();
		Customer customer = buildCustomer();
		when(customerRepositoryMock.save(any(Customer.class))).thenReturn(customer);
		CustomerDto response = customerService.createCustomer(customerDto);
		Assert.assertEquals("Satya", response.getFirstName());
		Assert.assertEquals("Kolipaka", response.getLastName());
		Assert.assertEquals("password", response.getPassword());
		Assert.assertEquals(LocalDate.parse("1992-06-06"), response.getDateOfBirth());
	}

	@Test
	public void modifyCustomerSuccess() {
		CustomerDto customerDto = buildCustomerDto();
		Optional<Customer> customer = Optional.of(buildCustomer());

		when(customerRepositoryMock.findById(1L)).thenReturn(customer);
		when(customerRepositoryMock.save(any(Customer.class))).thenReturn(customer.get());

		CustomerDto response = customerService.modifyCustomer(1L, customerDto);
		Assert.assertEquals("Satya", response.getFirstName());
		Assert.assertEquals("Kolipaka", response.getLastName());
		Assert.assertEquals("password", response.getPassword());
		Assert.assertEquals(LocalDate.parse("1992-06-06"), response.getDateOfBirth());
	}

	@Test(expected = CustomerNotFoundException.class)
	public void modifyCustomerFailure() {
		CustomerDto customerDto = buildCustomerDto();
		Optional<Customer> customer = Optional.of(buildCustomer());

		when(customerRepositoryMock.save(any(Customer.class))).thenReturn(customer.get());

		CustomerDto response = customerService.modifyCustomer(1L, customerDto);
		Assert.assertEquals("Satya", response.getFirstName());
		Assert.assertEquals("Kolipaka", response.getLastName());
	}

	private Customer buildCustomer() {
		Customer customer = new Customer("Satya", "Kolipaka", "password", LocalDate.parse("1992-06-06"));
		customer.setId(1L);
		return customer;
	}

	private CustomerDto buildCustomerDto() {
		CustomerDto customerDto = new CustomerDto("Satya", "Kolipaka", "password", LocalDate.parse("1992-06-06"));
		return customerDto;
	}
}
