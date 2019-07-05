package com.ftn.uns.travelplaner.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Route implements Serializable {

    public Long id;
    public String name;
    public Date date;
    public List<Activity> activities = new ArrayList<>();
}
