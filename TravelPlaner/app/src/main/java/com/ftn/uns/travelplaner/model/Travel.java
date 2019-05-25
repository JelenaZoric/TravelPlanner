package com.ftn.uns.travelplaner.model;

import java.io.Serializable;

public class Travel implements Serializable {

    public User user;
    public Transportation origin;
    public Transportation destination;
    public String currency;
    public TransportationMode mode;

}
