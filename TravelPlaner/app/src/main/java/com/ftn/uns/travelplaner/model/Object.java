package com.ftn.uns.travelplaner.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Object implements Serializable {

    public Long id;
    public String name;
    public Location location;
    public String address;
    public String email;
    public String phoneNumber;
    public ActivityType type;
    public double rating;
    public String imagePath;
    public String description;
    public List<Comment> comments = new ArrayList<>();
}
