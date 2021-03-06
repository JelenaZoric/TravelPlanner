package com.ftn.uns.travelplaner.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Travel implements Serializable {

    public Long id;
    public Transportation origin;
    public Transportation destination;
    public String currency;
    public TransportationMode mode;
    public Object accommodation;
    public List<Item> items = new ArrayList<>();
    public List<Route> routes = new ArrayList<>();

    @Override
    public String toString() {
        return "\nTravel:\n " + "ID: " + id + "; accommodation name: " + accommodation.name;
    }
}
