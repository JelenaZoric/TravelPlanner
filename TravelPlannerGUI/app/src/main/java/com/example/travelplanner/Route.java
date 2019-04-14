package com.example.travelplanner;

import java.io.Serializable;
import java.util.Date;

public class Route implements Serializable {

    private Integer id;
    private Date date;
    private String title;
    private Double lat;
    private Double lon;

    public Route(Integer id, Date date, String title, Double lat, Double lon) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.lat = lat;
        this.lon = lon;
    }

    public Route() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return String.format("%s @ %s", title, date);
    }
}
