package com.app.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.dto.TrackData;
import com.app.dto.TrackingDetails;
import com.app.model.Customer;
import com.app.model.Parcel;
import com.app.model.Tracking;
import com.app.repository.CustomerRepository;
import com.app.repository.ParcelRepository;
import com.app.repository.TrackingRepository;
import com.app.util.Utility;
import jakarta.transaction.Transactional;

@Service
public class TrackingNumberService {
	
	static String pattern = "^[A-Z0-9]{1,16}$";

	@Autowired
	private CustomerRepository customerRepository; 

	@Autowired
	private ParcelRepository parcelRepository;   

	@Autowired
	private TrackingRepository  trackingRepository;
	
	
	@Transactional
	public Tracking save(String trackingNumber, Parcel parcel) {   
		
		Tracking tracking = new Tracking(trackingNumber, parcel);
		return trackingRepository.save(tracking);
		
	}

	
	@Transactional
	public TrackingDetails processNextTrackingNumber(TrackData data) {

		TrackingDetails trackingDetails = null;

		Customer cust = customerRepository.findById(data.getCustomerId()).get();

		List<Parcel> parcels = 
				parcelRepository.findByCustomerAndOriginCountryIdAndDestinationCountryIdAndWeight
				(cust,data.getOriginCountryId(),data.getDestinationCountryId(),data.getWeight());

		Optional<Parcel> parcelCheck =
				parcels.stream().filter(parc -> parc.getCustomer().getCustomerId().equals(cust.getCustomerId()))
				.findAny();
		
		if(parcelCheck.isPresent()) {
			Parcel parcel = parcelCheck.get();
			int count = parcel.getCount()+1;
			parcel.setCount(count);	

			String trackingNumber = generateTrackingNumber(parcel,count);	
			parcel.setTrackingNumber(trackingNumber);	

			OffsetDateTime trackingDate  = Utility.getCurrentTime();
			parcel.setCreatedAt(trackingDate);
			parcelRepository.save(parcel);

			Tracking tracking = new Tracking(trackingNumber, parcel);
			trackingRepository.save(tracking);

			trackingDetails = new  TrackingDetails( trackingDate.toString(),  trackingNumber);
				
		}
		
		return trackingDetails;
	}


	public String generateTrackingNumber(Parcel parcel,Integer count) {		

		Customer customer = parcel.getCustomer();
		
		String input = 
				  customer.getCustomerName().substring(0,3).replace(" ", "")
				 +customer.getCustomerId().toString().replaceAll("-", "").substring(0,5)
				 +parcel.getParcelId().toString()+
				 count.toString();  
		
		String trackingNumber = input.toUpperCase().replace(" ", "").replaceAll("[^a-zA-Z0-9]", "");  
		System.out.println(trackingNumber);  
		
        if (Pattern.matches(pattern, input)) {
            System.out.println("Valid Number");	           
        } else {
           if( trackingNumber.length() > 16) {
        	   int removals = trackingNumber.length()-16;
        	  return trackingNumber.substring(removals+1);
           }
        }
        return trackingNumber;
	        

	}


}
