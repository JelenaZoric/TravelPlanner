package com.ftn.uns.travelplaner.model;

import java.io.Serializable;

public class User implements Serializable {

    public String email;
    public String password;
    public String firstName;
    public String lastName;
    public Location location;
}
