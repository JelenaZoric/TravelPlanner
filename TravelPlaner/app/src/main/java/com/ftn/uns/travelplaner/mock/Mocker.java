package com.ftn.uns.travelplaner.mock;

import com.ftn.uns.travelplaner.model.Activity;
import com.ftn.uns.travelplaner.model.ActivityType;
import com.ftn.uns.travelplaner.model.Comment;
import com.ftn.uns.travelplaner.model.Item;
import com.ftn.uns.travelplaner.model.Location;
import com.ftn.uns.travelplaner.model.Object;
import com.ftn.uns.travelplaner.model.Route;
import com.ftn.uns.travelplaner.model.Transportation;
import com.ftn.uns.travelplaner.model.TransportationMode;
import com.ftn.uns.travelplaner.model.Travel;
import com.ftn.uns.travelplaner.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static java.lang.Math.abs;

public class Mocker {

    private static final String[] CITIES = {"Moscow", "Rome", "Paris", "London"};
    private static final String[] COUNTRIES = {"Russia", "Italy", "France", "United Kingdom"};
    private static final String[] CURRENCYS = {"RUB", "EUR", "EUR", "GBR"};
    private static final String ORIGIN_CITY = "Novi Sad";
    private static final String ORIGIN_COUNTRY = "Serbia";

    private static final int DAYS_BOUND = 1000;
    private static final int DURATION_RANGE_BOUND = 31;

    private static final String EMAIL = "foo@example.bar";
    private static final String[] FIRST_NAME = {"John", "Jane"};
    private static final String LAST_NAME = "Doe";

    private static final String[] HOTEL_NAMES = {"Radisson Blue", "Sheraton", "Four seasons", "The Ritz hotel"};
    private static final String[] SPORT_NAMES = {"Wembley stadium", "Madison Square Garden"};
    private static final String[] FOOD_NAMES = {"Nobu", "McDonalds"};
    private static final String[] OUTDOOR_NAMES = {"Central Park", "The Strand"};
    private static final String[] ANIMALS_NAMES = {"Aquarium", "Grand Zoo"};
    private static final String[] THEATER_NAMES = {"Garnier Opera", "Cineplexx"};
    private static final String[] MUSEUM_NAMES = {"The Louvre", "National Museum"};
    private static final String[] SHOPPING_NAMES = {"Main Plaza", "Flee Market"};

    private static final Double[][] COORDINATES = {
            {48.864997072, 2.322526932}, {48.864906733, 2.310407638},
            {48.857785, 2.300292}, {48.853228910, 2.317651747}};

    private static final String ADDRESS_PATTERN = "Main street %d";
    private static final String EMAIL_PATTERN = "%s@%s.com";
    private static final String PHONE_PATTERN = "+33 1%d 5%d7 %d9%d";

    private static final String[] ITEMS = {"Passport", "Money", "Hair Dryer", "Umbrella", "Phone", "Toothbrush", "Slippers", "Keys"};

    private static final String ROUTE_PATTERN = "Route %d";

    private static final String[] COMMENTS = {
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            "Praesent eu turpis in neque pulvinar pretium.",
            "In a placerat enim. In lectus odio, molestie vel ex ut, mollis facilisis diam.",
            "Nullam vulputate magna quam, quis congue dui scelerisque vel.",
            "Praesent eleifend turpis orci.",
            "Etiam mattis, enim vel rutrum viverra, nulla nisi accumsan tellus, in elementum ipsum dui nec est.",
            "Duis in lorem id enim sodales venenatis eu vel risus."
    };

    public static User db;
    public static Travel dbTravel;
    public static Route dbRoute;
    public static Activity dbActivity;

    public static User mockUser() {
        User user = new User();
        user.email = EMAIL;

        Random random = new Random();
        int nameIdx = random.nextInt(2);

        user.firstName = FIRST_NAME[nameIdx];
        user.lastName = LAST_NAME;

        user.location = mockLocation(-1);

        user.travels = mockTravels(1);
        return user;
    }

    private static List<Travel> mockTravels(int itemsCount) {
        List<Travel> travels = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < itemsCount; i++) {
            Travel travel = new Travel();
            Transportation originTransportation = new Transportation();

            Location originLocation = new Location();
            originLocation.city = ORIGIN_CITY;
            originLocation.country = ORIGIN_COUNTRY;
            originTransportation.location = originLocation;

            long randomDaysBefore = random.nextInt(DAYS_BOUND);
            //originTransportation.departure = LocalDateTime.now().minusDays(randomDaysBefore);
            originTransportation.departure = new Date();

            travel.origin = originTransportation;

            Transportation destinationTransportation = new Transportation();
            destinationTransportation.location = mockLocation(-1);

            long randomDurationRange = random.nextInt(DURATION_RANGE_BOUND);
            //destinationTransportation.departure = originTransportation.departure.plusDays(randomDurationRange);
            //destinationTransportation.departure.add(Calendar.DAY_OF_MONTH, +5);
            destinationTransportation.departure = new Date();
            destinationTransportation.departure.setDate(Calendar.getInstance().getTime().getDate()+5);

            travel.destination = destinationTransportation;

            int randomModeIdx = random.nextInt(TransportationMode.values().length);
            travel.mode = TransportationMode.values()[randomModeIdx];
            travel.currency = CURRENCYS[randomModeIdx % 4];

            travel.items = mockItems(random.nextInt(10) + 1);
            travel.routes = mockRoutes(random.nextInt(10) + 1);

            travel.accommodation = mockObjects(1, ActivityType.ACCOMMODATION, -1).get(0);

            travels.add(travel);
        }

        return travels;
    }

    private static Location mockLocation(int idx) {
        Location location = new Location();

        Random random = new Random();
        int randomLocationIdx = random.nextInt(CITIES.length);

        location.city = CITIES[randomLocationIdx];
        location.country = COUNTRIES[randomLocationIdx];

        List<Double> coordinates = mockCoordinates(idx);
        location.latitude = coordinates.get(0);
        location.longitude = coordinates.get(1);

        return location;
    }

    public static List<Object> mockObjects(int itemsCount, ActivityType type, int idx) {
        List<Object> objects = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < itemsCount; i++) {
            Object object = new Object();
            object.type = type;

            int randomNameIdx = random.nextInt(2);

            switch (type) {
                case FOOD:
                    object.name = FOOD_NAMES[randomNameIdx];
                    break;
                case SPORT:
                    object.name = SPORT_NAMES[randomNameIdx];
                    break;
                case SHOPPING:
                    object.name = SHOPPING_NAMES[randomNameIdx];
                    break;
                case THEATER:
                    object.name = THEATER_NAMES[randomNameIdx];
                    break;
                case MUSEUM:
                    object.name = MUSEUM_NAMES[randomNameIdx];
                    break;
                case ANIMALS:
                    object.name = ANIMALS_NAMES[randomNameIdx];
                    break;
                case OUTDOOR:
                    object.name = OUTDOOR_NAMES[randomNameIdx];
                    break;
                default:
                    object.name = HOTEL_NAMES[randomNameIdx];
                    break;
            }

            int randomAddressNumber = random.nextInt(300);
            object.address = String.format(Locale.getDefault(), ADDRESS_PATTERN, randomAddressNumber);

            object.email = String.format(
                    EMAIL_PATTERN,
                    object.name.toLowerCase().replace(' ', '_'),
                    type.name().toLowerCase());

            object.phoneNumber = String.format(
                    Locale.getDefault(),
                    PHONE_PATTERN,
                    randomNameIdx,
                    random.nextInt(10),
                    randomAddressNumber,
                    random.nextInt(10));

            object.location = mockLocation(idx);
            object.rating = abs(random.nextDouble() * 5);

            object.description = mockText();
            //object.comments = mockComments(random.nextInt(10) + 1);

            objects.add(object);
        }

        return objects;
    }

    public static List<Double> mockCoordinates(int idx) {
        if (idx == -1) {
            return Arrays.asList(COORDINATES[0]);
        }

        return Arrays.asList(COORDINATES[idx + 1]);
    }

    private static List<Item> mockItems(int itemsCount) {
        List<Item> items = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < itemsCount; i++) {
            Item item = new Item();

            int randomIdx = random.nextInt(ITEMS.length);
            item.name = ITEMS[randomIdx];
            item.brought = randomIdx % 2 == 0;

            items.add(item);
        }

        return items;
    }

    private static List<Route> mockRoutes(int itemsCount) {
        List<Route> routes = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < itemsCount; i++) {
            Route route = new Route();

            int randomIdx = random.nextInt(ITEMS.length);
            route.name = String.format(Locale.getDefault(), ROUTE_PATTERN, randomIdx);

            long randomDaysBefore = random.nextInt(DAYS_BOUND);
            //route.date = LocalDate.now().minusDays(randomDaysBefore);
            route.date = new Date();

            route.activities = mockActivities(3);

            routes.add(route);
        }

        return routes;
    }

    private static List<Activity> mockActivities(int itemsCount) {
        List<Activity> activities = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < itemsCount; i++) {
            Activity activity = new Activity();

            ActivityType type = ActivityType.values()[random.nextInt(ActivityType.values().length)];
            activity.type = type;
            activity.object = mockObjects(1, type,i).get(0);
            //activity.time = LocalTime.of(random.nextInt(24), random.nextInt(60), random.nextInt(60), 0);
            activity.time = Calendar.getInstance().getTime();

            activities.add(activity);
        }

        return activities;
    }

    private static List<Comment> mockComments(int itemsCount) {
        List<Comment> comments = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < itemsCount; i++) {
            Comment comment = new Comment();
            comment.user = mockUser();

            comment.rating = random.nextDouble() * 5;
            comment.text = mockText();

            comments.add(comment);
        }

        return comments;
    }

    private static String mockText() {
        Random random = new Random();

        int sentenceCount = random.nextInt(COMMENTS.length);
        StringBuilder content = new StringBuilder();

        for (int j = 0; j < sentenceCount; j++) {
            content.append(COMMENTS[random.nextInt(COMMENTS.length - 1)]);
        }
        return content.toString();
    }
}
