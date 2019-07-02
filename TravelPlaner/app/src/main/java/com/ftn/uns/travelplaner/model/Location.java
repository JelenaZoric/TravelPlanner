package com.ftn.uns.travelplaner.model;

import java.io.Serializable;

public class Location implements Serializable {

    public double longitude;
    public double latitude;
    public String city;
    public String country;

    public Location() {}

    public Location(String city, String country) {
        this.city = city;
        this.country = country;
    }

    @Override
    public String toString() {
        return String.format("%s, %s", city, country);
    }
}
