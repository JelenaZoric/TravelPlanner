package com.ftn.uns.travelplaner.model;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

public class Activity implements Serializable {

    public Long id;
    public ActivityType type;
    public Object object;
    public Date time;
}
