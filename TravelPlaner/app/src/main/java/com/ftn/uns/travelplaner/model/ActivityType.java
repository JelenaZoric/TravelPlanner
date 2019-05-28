package com.ftn.uns.travelplaner.model;

public enum ActivityType {
    ACCOMMODATION, SPORT, FOOD, OUTDOOR, ANIMALS, THEATER, MUSEUM, SHOPPING;

    @Override
    public String toString() {
        String lowercase = this.name().toLowerCase();
        return lowercase.substring(0, 1).toUpperCase() + lowercase.substring(1);
    }
}
