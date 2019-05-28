package com.ftn.uns.travelplaner.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Travel implements Serializable {

    public User user;
    public Transportation origin;
    public Transportation destination;
    public String currency;
    public TransportationMode mode;
    public Object accommodation;
    public List<Item> items = new ArrayList<>();
    public List<Route> routes = new ArrayList<>();
}
