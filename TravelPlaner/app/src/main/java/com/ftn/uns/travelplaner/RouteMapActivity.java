package com.ftn.uns.travelplaner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ftn.uns.travelplaner.mock.Mocker;
import com.ftn.uns.travelplaner.model.Activity;
import com.ftn.uns.travelplaner.model.Route;
import com.ftn.uns.travelplaner.util.ColorResolverUtil;
import com.ftn.uns.travelplaner.util.DateTimeFormatter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class RouteMapActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private DirectionsTask directionsTask = null;
    private GoogleMap map;
    private List<LatLng> activities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_map);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.hotel_map);
        mapFragment.getMapAsync(this);

        setState();
    }

    private void setState() {
        Route route = Mocker.dbRoute;
        setTitle(route.name);

        TextView dateView = findViewById(R.id.route_date);
        dateView.setText(DateTimeFormatter.formatDate(route.date));
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        this.activities = new ArrayList<>();

        LatLng accommodation = new LatLng(Mocker.dbTravel.accommodation.location.latitude, Mocker.dbTravel.accommodation.location.longitude);
        this.activities.add(accommodation);

        for (Activity activity : Mocker.dbRoute.activities) {
            this.activities.add(new LatLng(activity.object.location.latitude, activity.object.location.longitude));
        }

        String url = getMapsApiDirectionsUrl(this.activities);
        if (this.directionsTask == null) {
            this.directionsTask = new DirectionsTask(url);
            this.directionsTask.execute("");
        }
    }


    private String getMapsApiDirectionsUrl(List<LatLng> activities) {

        LatLng origin = activities.get(0);
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin="
                + origin.latitude + "," + origin.longitude + "&waypoints=";

        for (int i = 1; i < activities.size() - 2; i++) {
            LatLng activity = activities.get(i);

            url += activity.latitude + "," + activity.longitude + "|";
        }

        LatLng lastWaypoint = activities.get(activities.size() - 2);
        url += lastWaypoint.latitude + "," + lastWaypoint.longitude;

        LatLng destination = activities.get(activities.size() - 1);
        return url + "&destination=" + destination.latitude + "," + destination.longitude
                + "&key=AIzaSyB_I8oy65QQaD9c8gGkqPYBaG-QfkjzLl4";
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(RouteMapActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        if (id == R.id.action_edit) {
            return true;
        }

        if (id == R.id.action_delete) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(RouteMapActivity.this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(RouteMapActivity.this, LoginActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_travels) {
            Intent intent = new Intent(RouteMapActivity.this, TravelsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_travel_info) {
            Intent intent = new Intent(RouteMapActivity.this, TravelInfoActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_travel_items) {
            Intent intent = new Intent(RouteMapActivity.this, TravelItemsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_routes) {
            Intent intent = new Intent(RouteMapActivity.this, RoutesActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_route_list) {
            Intent intent = new Intent(RouteMapActivity.this, RouteListActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public class DirectionsTask extends AsyncTask<String, String, List<LatLng>> {

        public String url = "";

        public DirectionsTask(String url) {
            this.url = url;
        }

        @Override
        protected List<LatLng> doInBackground(String... uri) {
            if (url == null) {
                return null;
            }

            try {
                URL url = new URL(this.url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if (conn.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                    return readResponse(conn.getInputStream());
                }
            } catch (IOException | JSONException e) {
                //TODO Handle problems..
            }
            return null;
        }

        private List<LatLng> readResponse(InputStream stream) throws IOException, JSONException {
            BufferedReader r = new BufferedReader(new InputStreamReader(stream));
            StringBuilder total = new StringBuilder();
            for (String line; (line = r.readLine()) != null; ) {
                total.append(line).append('\n');
            }

            return parseResponse(total.toString());
        }

        private List<LatLng> parseResponse(String response) throws JSONException {
            JSONObject responseObject = new JSONObject(response);
            JSONArray routesArray = responseObject.getJSONArray("routes");
            List<LatLng> waypoints = new ArrayList<>();

            for(int k = 0; k < routesArray.length(); k++) {
                JSONObject routeObject = routesArray.getJSONObject(k);
                JSONArray legsArray = routeObject.getJSONArray("legs");

                for (int i = 0; i < legsArray.length(); i++) {
                    JSONObject legObject = legsArray.getJSONObject(i);
                    JSONArray stepsArray = legObject.getJSONArray("steps");

                    for (int j = 0; j < stepsArray.length(); j++) {
                        JSONObject stepObject = stepsArray.getJSONObject(j);
                        JSONObject startLocation = stepObject.getJSONObject("start_location");

                        double latitude = startLocation.getDouble("lat");
                        double longitude = startLocation.getDouble("lng");
                        waypoints.add(new LatLng(latitude, longitude));
                    }
                }
            }

            return waypoints;
        }

        @Override
        protected void onPostExecute(List<LatLng> result) {

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(RouteMapActivity.this);
            String routeColour = preferences.getString("routeColour", "Red");

            for (int i = 0; i < result.size() - 1; i++) {
                map.addPolyline(new PolylineOptions()
                        .add(result.get(i), result.get(i + 1))
                        .color(ColorResolverUtil.resolveColour(routeColour)));
            }

            for (LatLng activity : activities) {
                map.addMarker(new MarkerOptions()
                        .position(activity));
            }

            //draw lines and markers
            CameraPosition cameraPosition = new CameraPosition(result.get(0), 15, 0, 0);
            map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }
}
