package com.example.travelplanner;

import java.io.Serializable;

public class Activity implements Serializable {

    private Integer id;
    private String type;
    private Route route;

    public Activity() {}

    public Activity(Integer id, String type, Route route) {
        this.id = id;
        this.type = type;
        this.route = route;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return String.format("%s", type);
    }
}
