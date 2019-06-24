package com.ftn.uns.travelplanerbackend.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Object implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4984143278300229446L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	private Location location;
	private String address;
	private String email;
	private String phoneNumber;
	private ActivityType type;
	private double rating;
	private String imagePath;
	private String description;
	@OneToMany
	@JsonIgnore
	private List<Comment> comments = new ArrayList<>();
	
	public Object() {}

	public Object(String name, Location location, String address, String email,
			String phoneNumber, ActivityType type, double rating,
			String imagePath, String description, List<Comment> comments) {
		super();
		this.name = name;
		this.location = location;
		this.address = address;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.type = type;
		this.rating = rating;
		this.imagePath = imagePath;
		this.description = description;
		this.comments = comments;
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

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public ActivityType getType() {
		return type;
	}

	public void setType(ActivityType type) {
		this.type = type;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}
