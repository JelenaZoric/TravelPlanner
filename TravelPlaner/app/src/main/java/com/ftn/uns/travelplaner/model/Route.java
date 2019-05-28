package com.ftn.uns.travelplaner.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Route {

    public String name;
    public LocalDate date;
    public List<Activity> activities = new ArrayList<>();
}
