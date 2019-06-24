package com.ftn.uns.travelplanerbackend.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Location implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8352287457506759352L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private double longitude;
    private double latitude;
    private String city;
    private String country;

    public Location() {}
    
    public Location(double longitude, double latitude, String city,
			String country) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		this.city = city;
		this.country = country;
	}
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
    public String toString() {
        return String.format("%s, %s", city, country);
    }
}
