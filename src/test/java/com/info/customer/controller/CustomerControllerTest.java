package com.info.customer.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.info.customer.dto.CustomerDto;
import com.info.customer.dto.UserLoginDto;
import com.info.customer.exception.CustomerNotFoundException;
import com.info.customer.service.CustomerService;

/**
 * Api test class for CustomerController class
 * @author Satya Kolipaka
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerControllerTest {

	private MockMvc mockMvc;

	@Mock
	private CustomerService customerServiceMock;

	@Before
	public void setup() {
		CustomerController customerController = new CustomerController();
		ReflectionTestUtils.setField(customerController, "customerService", customerServiceMock);
		this.mockMvc = MockMvcBuilders.standaloneSetup(customerController)
				.setControllerAdvice(new CustomerControllerAdvice()).build();
	}

	@Test
	public void authenticate_customer() throws Exception {
		UserLoginDto userLoginDto = new UserLoginDto();
		userLoginDto.setUserName("satya123");
		userLoginDto.setPassword("pass123");

		String json = "{ \"userName\" : \"satya123\", \"password\" : \"pass123\" }";
		when(customerServiceMock.customerAuthenticate(userLoginDto)).thenReturn("Login Success");

		this.mockMvc.perform(post("/info/authenticate").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk());
	}
	
	@Test
	public void retrieveCustomer_found() throws Exception {
		CustomerDto customerDto = buildCustomerDto();
		when(customerServiceMock.retrieveCustomer(1L)).thenReturn(customerDto);
		this.mockMvc.perform(get("/info/customer/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("Satya"));
	}

	@Test
	public void retrieveCustomer_Notfound() throws Exception {
		when(customerServiceMock.retrieveCustomer(1L))
				.thenThrow(new CustomerNotFoundException("No Customer Found for id:1"));
		this.mockMvc.perform(get("/info/customer/1")).andExpect(status().isNotFound());
	}

	@Test
	public void delete_customer() throws Exception {
		when(customerServiceMock.removeCustomer(1L)).thenReturn(true);
		this.mockMvc.perform(delete("/info/customer/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$").value("true"));
	}

	@Test
	public void delete_customer_Notfound() throws Exception {
		when(customerServiceMock.removeCustomer(1L))
				.thenThrow(new CustomerNotFoundException("No Customer Found for id:1"));
		this.mockMvc.perform(delete("/info/customer/1")).andExpect(status().isNotFound())
				.andExpect(jsonPath("$").value("No Customer Found for id:1"));
	}

	private CustomerDto buildCustomerDto() {
		CustomerDto customerDto = new CustomerDto("Satya", "Kolipaka", "password", LocalDate.parse("1992-06-06"));
		return customerDto;
	}

}
