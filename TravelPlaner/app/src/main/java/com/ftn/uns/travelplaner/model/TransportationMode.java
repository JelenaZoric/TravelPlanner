package com.ftn.uns.travelplaner.model;

public enum TransportationMode {
    CAR, PLANE, TRAIN, BUS, SHIP, BICYCLE, OTHER;

    @Override
    public String toString() {
        String lowercase = this.name().toLowerCase();
        return lowercase.substring(0, 1).toUpperCase() + lowercase.substring(1);
    }
}
