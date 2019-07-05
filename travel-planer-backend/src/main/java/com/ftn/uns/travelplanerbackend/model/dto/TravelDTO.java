package com.ftn.uns.travelplanerbackend.model.dto;

import java.util.Set;

import com.ftn.uns.travelplanerbackend.model.*;
import com.ftn.uns.travelplanerbackend.model.Object;

public class TravelDTO {

	private Long id;
	private Transportation origin;
	private Transportation destination;
	private String currency;
	private TransportationMode mode;
	private Object accommodation;
	private Set<Route> routes;
	private Set<Item> items;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Transportation getOrigin() {
		return origin;
	}
	public void setOrigin(Transportation origin) {
		this.origin = origin;
	}
	public Transportation getDestination() {
		return destination;
	}
	public void setDestination(Transportation destination) {
		this.destination = destination;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public TransportationMode getMode() {
		return mode;
	}
	public void setMode(TransportationMode mode) {
		this.mode = mode;
	}
	public Object getAccommodation() {
		return accommodation;
	}
	public void setAccommodation(Object accommodation) {
		this.accommodation = accommodation;
	}
	public Set<Route> getRoutes() {
		return routes;
	}
	public void setRoutes(Set<Route> routes) {
		this.routes = routes;
	}
	public Set<Item> getItems() {
		return items;
	}
	public void setItems(Set<Item> items) {
		this.items = items;
	}
}
