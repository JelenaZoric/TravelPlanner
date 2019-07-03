package com.ftn.uns.travelplanerbackend.controller;

import java.util.List;

import com.ftn.uns.travelplanerbackend.model.User;
import com.ftn.uns.travelplanerbackend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.uns.travelplanerbackend.model.Travel;

@RestController
@RequestMapping(value="travels")
public class TravelController {

	@Autowired
	private TravelService travelService;

	@Autowired
	private UserService userService;

	@PreAuthorize("isAuthenticated()")
	@RequestMapping
	public ResponseEntity<List<Travel>> getAllTravels(Authentication authentication) {
		User user = userService.findOne(Long.valueOf(authentication.getName()));
		List<Travel> travels = user.getTravels();
		return new ResponseEntity<List<Travel>>(travels, HttpStatus.OK);
	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="{id}")
	public ResponseEntity<Travel> getTravel(@PathVariable("id") Long id) {
		Travel travel = travelService.findOne(id);
		System.out.println("\nSystem.out.println(travel.getId());");
		System.out.println(travel.getId());
		System.out.println(travel.getCurrency());
		return new ResponseEntity<Travel>(travel, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<Travel> addTravel(@RequestBody Travel travel, Authentication authentication) {
		Travel newTravel = travelService.save(travel);
		User user = userService.findOne(Long.valueOf(authentication.getName()));
		user.addTravel(newTravel);
		userService.save(user);
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
