package com.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.model.Customer;
import com.app.repository.CustomerRepository;


@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository; 

	public Customer save(Customer customer) {    	
		return customerRepository.save(customer);  
	}

	
}
