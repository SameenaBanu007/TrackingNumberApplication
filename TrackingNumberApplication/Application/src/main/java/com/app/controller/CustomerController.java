package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.model.Customer;
import com.app.service.CustomerService;


@RestController
@RequestMapping("/application/customer")
public class CustomerController {

	@Autowired
	private CustomerService service;   

	@PostMapping("/createCustomer")
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) { 
		Customer cust = service.save(customer);
		return cust != null ? ResponseEntity.ok(cust) : ResponseEntity.notFound().build();
	}

}
