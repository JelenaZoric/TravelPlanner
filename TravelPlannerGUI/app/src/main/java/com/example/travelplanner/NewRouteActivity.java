package com.example.travelplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Random;

public class NewRouteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_route);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void createRoute(View button) {
        Route route = new Route();
        route.setLat(45.2522166);
        route.setLon(19.8469194);

        EditText titleView = findViewById(R.id.route_title);
        route.setTitle(titleView.getText().toString());

        DatePicker dateView = findViewById(R.id.route_date);
        int day = dateView.getDayOfMonth();
        int month = dateView.getMonth();
        int year =  dateView.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        route.setDate(calendar.getTime());

        Random random = new Random();
        route.setId(random.nextInt());

        RoutesActivity.addRoute(route);

        Intent intent = new Intent(this, RoutesActivity.class);
        startActivity(intent);
    }

    public void startSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

}
