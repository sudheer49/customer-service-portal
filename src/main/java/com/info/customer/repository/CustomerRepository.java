package com.info.customer.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.info.customer.entity.Customer;

/**
 * Repository interface for customer entity
 * @author Satya Kolipaka
 *
 */
@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long>{
	
	Optional<Customer> findByUserName(String userName);
}
