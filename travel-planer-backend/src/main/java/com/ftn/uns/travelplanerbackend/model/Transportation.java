package com.ftn.uns.travelplanerbackend.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Transportation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3512565838144950754L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Date departure;
	private Location location;
	
	public Transportation() {}

	public Transportation(Date departure, Location location) {
		super();
		this.departure = departure;
		this.location = location;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDeparture() {
		return departure;
	}

	public void setDeparture(Date departure) {
		this.departure = departure;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
