package com.ftn.uns.travelplaner.mock;

import com.ftn.uns.travelplaner.model.Location;
import com.ftn.uns.travelplaner.model.Transportation;
import com.ftn.uns.travelplaner.model.TransportationMode;
import com.ftn.uns.travelplaner.model.Travel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mocker {

    private static final String[] CITIES = { "Moscow", "Rome", "Paris", "London"};
    private static final String[] COUNTRIES = { "Russia", "Italy", "France", "United Kingdom" };
    private static final String[] CURRENCYS = { "RUB", "EUR", "EUR", "GBR" };
    private static final String ORIGIN_CITY = "Novi Sad";
    private static final String ORIGIN_COUNTRY = "Serbia";
    private static final int DAYS_BOUND = 1000;
    private static final int DURATION_RANGE_BOUND = 31;

    public static List<Travel> mockTravels(int itemsCount) {
        List<Travel> travels = new ArrayList<>();
        Random random = new Random();

        for(int i = 0; i < itemsCount; i++) {
            Travel travel = new Travel();
            Transportation originTransportation = new Transportation();

            Location originLocation = new Location();
            originLocation.city = ORIGIN_CITY;
            originLocation.country = ORIGIN_COUNTRY;
            originTransportation.location = originLocation;

            long randomDaysBefore = random.nextInt(DAYS_BOUND);
            originTransportation.departure = LocalDateTime.now().minusDays(randomDaysBefore);

            travel.origin = originTransportation;

            Transportation destinationTransportation = new Transportation();
            Location destinationLocation = new Location();

            int randomLocationIdx = random.nextInt(CITIES.length);
            destinationLocation.city = CITIES[randomLocationIdx];
            destinationLocation.country = COUNTRIES[randomLocationIdx];
            destinationTransportation.location = destinationLocation;

            long randomDurationRange = random.nextInt(DURATION_RANGE_BOUND);
            destinationTransportation.departure = originTransportation.departure.plusDays(randomDurationRange);

            travel.destination = destinationTransportation;

            int randomModeIdx = random.nextInt(TransportationMode.values().length);
            travel.mode = TransportationMode.values()[randomModeIdx];
            travel.currency = CURRENCYS[randomLocationIdx];

            travels.add(travel);
        }

        return travels;
    }
}
