package com.ftn.uns.travelplanerbackend.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Travel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1329255545842981169L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@OneToOne(cascade = CascadeType.ALL)
	private Transportation origin;
	@OneToOne(cascade = CascadeType.ALL)
	private Transportation destination;
	private String currency;
	private TransportationMode mode;
	@Column(length = 1000)
	private Object accommodation;
	@JsonIgnore
	@OneToMany
	private List<Item> items = new ArrayList<>();
	@JsonIgnore
	@OneToMany
	private List<Route> routes = new ArrayList<>();

	public Travel() {}

	public Travel(Transportation origin, Transportation destination,
			String currency, TransportationMode mode, Object accommodation,
			List<Item> items, List<Route> routes) {
		super();
		this.origin = origin;
		this.destination = destination;
		this.currency = currency;
		this.mode = mode;
		this.accommodation = accommodation;
		this.items = items;
		this.routes = routes;
	}

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

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public List<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}
}
