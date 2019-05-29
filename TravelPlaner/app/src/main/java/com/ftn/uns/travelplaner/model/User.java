package com.ftn.uns.travelplaner.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    public String email;
    public String password;
    public String firstName;
    public String lastName;
    public Location location;
    public List<Travel> travels = new ArrayList<>();
}
