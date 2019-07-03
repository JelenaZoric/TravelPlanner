package com.ftn.uns.travelplanerbackend.utils;

import com.ftn.uns.travelplanerbackend.model.Location;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class GoogleCoordinatesService {

    private static String GOOGLE_API_URL = "https://maps.googleapis.com/maps/api/geocode/json";
    private static String GOOGLE_API_KEY = "AIzaSyChqtYEEgRO0gPZJRwkKCnT8Ew-BRMgXPQ";
    private static RestTemplate template = new RestTemplate();

    public Location getCoordinatesFromAddress(String city, String country) {
        try {
            String uri = GOOGLE_API_URL + "?address=" +
                    URLEncoder.encode(city, "UTF-8") + "," + URLEncoder.encode(country, "UTF-8") +
                    "&key=" + GOOGLE_API_KEY;

            ResponseEntity<String> jsonResponse = template.getForEntity(new URI(uri), String.class);
            Map<String, Double> coordinates = extractCoordinatesFromJson(jsonResponse.getBody());
            return new Location(coordinates.get("lng"), coordinates.get("lat"), city, country);

        } catch (UnsupportedEncodingException | URISyntaxException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Map<String, Double> extractCoordinatesFromJson(String json) {
        Gson gson = new Gson();

        JsonObject location = gson.fromJson(json, JsonObject.class)
                .get("results").getAsJsonArray()
                .get(0).getAsJsonObject()
                .get("geometry").getAsJsonObject()
                .get("location").getAsJsonObject();

        double latitude = location.get("lat").getAsDouble();
        double longitude = location.get("lng").getAsDouble();

        Map<String, Double> coordinates = new HashMap<>();
        coordinates.put("lat", latitude);
        coordinates.put("lng", longitude);
        return coordinates;
    }
}