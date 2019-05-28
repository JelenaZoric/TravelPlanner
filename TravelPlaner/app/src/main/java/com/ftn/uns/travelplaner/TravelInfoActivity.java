package com.ftn.uns.travelplaner;

import android.content.Intent;
import android.os.Bundle;
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
import com.ftn.uns.travelplaner.model.ActivityType;
import com.ftn.uns.travelplaner.model.Travel;
import com.ftn.uns.travelplaner.util.DateTimeFormatter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class TravelInfoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MapView mapView = findViewById(R.id.accommodation_map);
        mapView.getMapAsync(this);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setState();
    }

    private void setState() {
        Travel travel = Mocker.mockTravels(1).get(0);

        TextView titleView = findViewById(R.id.travel_destination);
        titleView.setText(travel.destination.location.toString());

        TextView durationView = findViewById(R.id.travel_duration);
        durationView.setText(DateTimeFormatter.formatDurationView(travel.origin, travel.destination));

        TextView startPointView = findViewById(R.id.travel_start_point);
        startPointView.setText(travel.origin.location.toString());

        TextView startTimeView = findViewById(R.id.travel_start_time);
        startTimeView.setText(DateTimeFormatter.formatDateTime(travel.origin.departure));

        TextView endPointView = findViewById(R.id.travel_end_point);
        endPointView.setText(travel.destination.location.toString());

        TextView endTimeView = findViewById(R.id.travel_end_time);
        endTimeView.setText(DateTimeFormatter.formatDateTime(travel.destination.departure));

        travel.accommodation = Mocker.mockObjects(1, ActivityType.ACCOMMODATION).get(0);
        TextView accommodationNameView = findViewById(R.id.accommodation_name);
        accommodationNameView.setText(travel.accommodation.name);

        TextView accommodationAddressView = findViewById(R.id.accommodation_address);
        accommodationAddressView.setText(travel.accommodation.address);

        TextView accommodationEmailView = findViewById(R.id.accommodation_email);
        accommodationEmailView.setText(travel.accommodation.email);

        TextView accommodationPhoneView = findViewById(R.id.accommodation_phone_number);
        accommodationPhoneView.setText(travel.accommodation.phoneNumber);

    }

    @Override
    public void onMapReady(GoogleMap map) {
        List<Double> coordinates = Mocker.mockCoordinates();
        LatLng destination = new LatLng(coordinates.get(0), coordinates.get(1));
        CameraPosition position = new CameraPosition(destination, 15, 0, 0);
        map.moveCamera(CameraUpdateFactory.newCameraPosition(position));
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(TravelInfoActivity.this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            //do logout
        } else if (id == R.id.nav_travels) {
            Intent intent = new Intent(TravelInfoActivity.this, TravelsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_travel_items) {
            Intent intent = new Intent(TravelInfoActivity.this, TravelItemsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_routes) {
            Intent intent = new Intent(TravelInfoActivity.this, RoutesActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}