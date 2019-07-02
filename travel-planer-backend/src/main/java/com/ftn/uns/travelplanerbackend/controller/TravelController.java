package com.ftn.uns.travelplanerbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.uns.travelplanerbackend.model.Location;
import com.ftn.uns.travelplanerbackend.model.Object;
import com.ftn.uns.travelplanerbackend.model.Transportation;
import com.ftn.uns.travelplanerbackend.model.Travel;
import com.ftn.uns.travelplanerbackend.service.LocationService;
import com.ftn.uns.travelplanerbackend.service.ObjectService;
import com.ftn.uns.travelplanerbackend.service.TransportationService;
import com.ftn.uns.travelplanerbackend.service.TravelService;

@RestController
@RequestMapping(value="travels")
public class TravelController {

	@Autowired
	private TravelService travelService;
	@Autowired
	private LocationService locationService;
	@Autowired
	private TransportationService transportationService;
	@Autowired
	private ObjectService objectService;

	@PreAuthorize("isAuthenticated()")
	@RequestMapping
	public ResponseEntity<List<Travel>> getAllTravels() {
		List<Travel> travels = travelService.findAll();
		return new ResponseEntity<List<Travel>>(travels, HttpStatus.OK);
	}
	
	@RequestMapping(value="{id}")
	public ResponseEntity<Travel> getTravel(@PathVariable("id") Long id) {
		Travel travel = travelService.findOne(id);
		return new ResponseEntity<Travel>(travel, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<Travel> addTravel(@RequestBody Travel travel) {
		/*
		Location startingLoc = locationService.save(travel.getOrigin().getLocation());
		Location destinationLoc = locationService.save(travel.getDestination().getLocation());
		Transportation transportationOrigin = travel.getOrigin();
		transportationOrigin.setLocation(startingLoc);
		transportationOrigin = transportationService.save(transportationOrigin);
		Transportation transportationDestination = travel.getDestination();
		transportationDestination.setLocation(destinationLoc);
		transportationDestination = transportationService.save(transportationDestination);
		
		Location accommodationLoc = locationService.save(travel.getAccommodation().getLocation());
		Object accommodation = travel.getAccommodation();
		accommodation.setLocation(accommodationLoc);
		com.ftn.uns.travelplanerbackend.model.Object object = objectService.save(accommodation);
		travel.setAccommodation(object);
		travel.setDestination(transportationDestination);
		travel.setOrigin(transportationOrigin);
		*/
		Travel newTravel = travelService.save(travel);
		return new ResponseEntity<Travel>(newTravel, HttpStatus.CREATED);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	public ResponseEntity<Travel> deleteTravel(@PathVariable("id") Long id) {
		Travel deletedTravel = travelService.delete(id);
		return new ResponseEntity<Travel>(deletedTravel, HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/{id}", consumes="application/json", produces="application/json")
	public ResponseEntity<Travel> editTravel(@PathVariable("id") Long id, @RequestBody Travel travel) {
		Travel editedTravel = travelService.findOne(id);
		editedTravel.setCurrency(travel.getCurrency());
		travelService.save(editedTravel);
		return new ResponseEntity<Travel>(editedTravel, HttpStatus.OK);
	}
}
