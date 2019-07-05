package com.ftn.uns.travelplanerbackend.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Route implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1313606689895225061L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	private Date date;
	@JsonIgnore
	@OneToMany(mappedBy="activityRoute")
	private Set<Activity> activities = new HashSet<>();
	@ManyToOne//(cascade=CascadeType.ALL)
	private Travel routeTravel;
	
	public Route() {}

	public Route(String name, Date date, Set<Activity> activities) {
		super();
		this.name = name;
		this.date = date;
		this.activities = activities;
	}

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
