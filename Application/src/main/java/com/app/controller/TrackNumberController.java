package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.dto.TrackData;
import com.app.dto.TrackingDetails;
import com.app.service.TrackingNumberService;


@RestController
@RequestMapping("/application/tracknumber")
public class TrackNumberController {

	@Autowired
	private TrackingNumberService service;   

	@GetMapping("/next-tracking-number")
	public ResponseEntity<TrackingDetails> processNextTrackingNumber(@RequestBody  TrackData data) {
		TrackingDetails tractingNumber =  service.processNextTrackingNumber(data);
		return tractingNumber != null ? ResponseEntity.ok(tractingNumber) : ResponseEntity.notFound().build();
	}
}
