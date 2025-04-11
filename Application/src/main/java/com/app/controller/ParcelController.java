package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Parcel;
import com.app.service.ParcelService;

@RestController
@RequestMapping("/application/parcel")
public class ParcelController {

	@Autowired
	private ParcelService service;  

	
	@PostMapping("/createParcel")
	public ResponseEntity<Parcel> createParcel(@RequestBody Parcel parcel) { 
		Parcel parc = service.createParcel(parcel);    
		return parc != null ? ResponseEntity.ok(parc) : ResponseEntity.notFound().build();
	}

}
