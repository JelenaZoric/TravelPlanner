package com.ftn.uns.travelplanerbackend.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.uns.travelplanerbackend.model.Activity;
import com.ftn.uns.travelplanerbackend.model.Object;
import com.ftn.uns.travelplanerbackend.model.Route;
import com.ftn.uns.travelplanerbackend.service.ActivityService;
import com.ftn.uns.travelplanerbackend.service.ObjectService;
import com.ftn.uns.travelplanerbackend.service.RouteService;

@RestController
@RequestMapping(value="activities")
public class ActivityController {

	@Autowired
	private ActivityService activityService;
	@Autowired
	private RouteService routeService;
	@Autowired
	private ObjectService objectService;
	
	@RequestMapping(value="{route_id}")
	public ResponseEntity<Set<Activity>> getAllActivities(@PathVariable("route_id") Long route_id) {
		Route route = routeService.findOne(route_id);
		Set<Activity> activities = route.getActivities();
		return new ResponseEntity<Set<Activity>>(activities, HttpStatus.OK);
	}
	
	@RequestMapping(value="getActivity/{id}")
	public ResponseEntity<Activity> getActivity(@PathVariable("id") Long id) {
		Activity activity = activityService.findOne(id);
		return new ResponseEntity<Activity>(activity, HttpStatus.OK);
	}
	
	@RequestMapping(value="{route_id}/{object_id}",method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<Activity> addActivity(@RequestBody Activity activity,
			@PathVariable("route_id") Long route_id, @PathVariable("object_id") Long object_id) {
		Route route = routeService.findOne(route_id);
		activity.setActivityRoute(route);
		Object object = objectService.findOne(object_id);
		activity.setObject(object);
		Activity newActivity = activityService.save(activity);
		return new ResponseEntity<Activity>(newActivity, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Activity> deleteActivity(@PathVariable("id") Long id) {
		Activity deleted = activityService.delete(id);
		return new ResponseEntity<Activity>(deleted, HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value="{id}", method=RequestMethod.PUT, consumes="application/json", produces="application/json")
	public ResponseEntity<Activity> editActivity(@PathVariable("id") Long id, @RequestBody Activity activity) {
		Activity edited = activityService.findOne(id);
		edited.setObject(activity.getObject());
		edited.setTime(activity.getTime());
		edited.setType(activity.getType());
		activityService.save(edited);
		return new ResponseEntity<Activity>(edited, HttpStatus.OK);
	}
}
