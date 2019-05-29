package com.ftn.uns.travelplaner.model;

import java.io.Serializable;
import java.time.LocalTime;

public class Activity implements Serializable {

    public ActivityType type;
    public Object object;
    public LocalTime time;
}
