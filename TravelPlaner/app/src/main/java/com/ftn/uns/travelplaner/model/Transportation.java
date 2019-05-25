package com.ftn.uns.travelplaner.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Transportation implements Serializable {

    public LocalDateTime departure;
    public Location location;
}
