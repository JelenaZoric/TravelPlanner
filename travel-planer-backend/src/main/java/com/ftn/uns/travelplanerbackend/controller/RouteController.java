package com.ftn.uns.travelplanerbackend.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.uns.travelplanerbackend.model.Route;
import com.ftn.uns.travelplanerbackend.model.Travel;
import com.ftn.uns.travelplanerbackend.service.RouteService;
import com.ftn.uns.travelplanerbackend.service.TravelService;

@RestController
@RequestMapping(value="routes")
public class RouteController {

	@Autowired
	private RouteService routeService;
	@Autowired
	private TravelService travelService;
	
	@RequestMapping
	public ResponseEntity<List<Route>> getAllRoutes() {
		List<Route> routes = routeService.findAll();
		return new ResponseEntity<List<Route>>(routes, HttpStatus.OK);
	}
	
	@RequestMapping(value="{travel_id}")
	public ResponseEntity<Set<Route>> getTravelRoutes(@PathVariable Long travel_id) {
		Travel travel = travelService.findOne(travel_id);
		Set<Route> routes = travel.getRoutes();
		return new ResponseEntity<Set<Route>>(routes, HttpStatus.OK);
	}
	
	@RequestMapping(value="{travel_id}/{id}")
	public ResponseEntity<Route> getRoute(@PathVariable("id") Long id, @PathVariable("travel_id") Long travel_id) {
		Route route = routeService.findOne(id);
		return new ResponseEntity<Route>(route, HttpStatus.OK);
	}
	
	@RequestMapping(value="{travel_id}",method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<Route> addRoute(@RequestBody Route route, @PathVariable("travel_id") Long travel_id) {
		System.out.println("dosao do post metode");
		Travel travel = travelService.findOne(travel_id);
		route.setRouteTravel(travel);
		Route newRoute = routeService.save(route);
		return new ResponseEntity<Route>(newRoute, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Route> deleteRoute(@PathVariable("id")Long id) {
		Route deleted = routeService.delete(id);
		return new ResponseEntity<Route>(deleted, HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value="{id}",method=RequestMethod.PUT,consumes="application/json",produces="application/json")
	public ResponseEntity<Route> editRoute(@PathVariable("id") Long id, @RequestBody Route route) {
		Route edited = routeService.findOne(id);
		edited.setActivities(route.getActivities());
		edited.setDate(route.getDate());
		edited.setName(route.getName());
		edited = routeService.save(edited);
		return new ResponseEntity<Route>(edited, HttpStatus.OK);
	}
}
