package com.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.Customer;
import com.app.model.Parcel;
import com.app.repository.CustomerRepository;
import com.app.repository.ParcelRepository;
import com.app.util.Utility;

import jakarta.transaction.Transactional;


@Service
public class ParcelService {

	@Autowired
	private CustomerRepository customerRepository; 

	@Autowired
	private ParcelRepository parcelRepository;   
	
	@Autowired
	private TrackingNumberService trackingNumberService;


	@Transactional
	public Parcel createParcel(Parcel parcel) {	
		
		Customer cust = customerRepository.findById(parcel.getCustomerId()).get();

		parcel.setCreatedAt(Utility.getCurrentTime());	
		parcel.setCount(1);
		parcel.setCustomer(cust);
		Parcel parc = parcelRepository.save(parcel);

		String trackingNumber = trackingNumberService.generateTrackingNumber(parc,1);		
		parc.setTrackingNumber(trackingNumber);

		trackingNumberService.save(trackingNumber, parcel);

		return parcelRepository.save(parc);	

	}

}
