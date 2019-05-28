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
import com.ftn.uns.travelplaner.model.Route;
import com.ftn.uns.travelplaner.util.DateTimeFormatter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Random;

public class RouteMapActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

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

        setState();
    }

    private void setState() {
        Route route = Mocker.mockRoutes(1).get(0);

        TextView nameView = findViewById(R.id.route_name);
        TextView dateView = findViewById(R.id.route_date);

        nameView.setText(route.name);
        dateView.setText(DateTimeFormatter.formatDate(route.date));
    }

    @Override
    public void onMapReady(GoogleMap map) {
        Random random = new Random();

        int maxIdx = random.nextInt(10 + 1);
        List<Double> centerCoordinates = Mocker.mockCoordinates();

        for (int i = 0; i < maxIdx; i++) {
            List<Double> coordinates = Mocker.mockCoordinates(centerCoordinates.get(0), centerCoordinates.get(1));
            LatLng destination = new LatLng(coordinates.get(0), coordinates.get(1));

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(destination);
        }

        CameraPosition cameraPosition = new CameraPosition(new LatLng(centerCoordinates.get(0), centerCoordinates.get(1)), 15, 0, 0);
        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
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
            Intent intent = new Intent(RouteMapActivity.this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            //do logout
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
}