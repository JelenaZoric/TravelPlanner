package com.ftn.uns.travelplaner.model;

import java.io.Serializable;

public class Location implements Serializable {

    public double longitude;
    public double latitude;
    public String city;
    public String country;

    @Override
    public String toString() {
        return String.format("%s, %s", city, country);
    }
}
