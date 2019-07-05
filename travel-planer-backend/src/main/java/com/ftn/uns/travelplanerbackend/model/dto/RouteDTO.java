package com.ftn.uns.travelplanerbackend.model.dto;

import java.util.Date;
import java.util.Set;

import com.ftn.uns.travelplanerbackend.model.Activity;
import com.ftn.uns.travelplanerbackend.model.Travel;

public class RouteDTO {

	private Long id;
	private String name;
	private Date date;
	private Set<Activity> activities;
	private Travel routeTravel;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Set<Activity> getActivities() {
		return activities;
	}
	public void setActivities(Set<Activity> activities) {
		this.activities = activities;
	}
	public Travel getRouteTravel() {
		return routeTravel;
	}
	public void setRouteTravel(Travel routeTravel) {
		this.routeTravel = routeTravel;
	}
}
