package com.example.travelplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class RoutesActivity extends AppCompatActivity {

    private static List<Route> routes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ListView listView = findViewById(R.id.routes_listview);
        listView.setAdapter(new ArrayAdapter<>(RoutesActivity.this,
                android.R.layout.simple_list_item_1, routes));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(RoutesActivity.this, ActivitiesActivity.class);
                Route route = routes.get((int)id);
                intent.putExtra("route", route);

                startActivity(intent);
            }
        });
    }

    public void openNewRouteForm(View button) {
        Intent intent = new Intent(this, NewRouteActivity.class);
        startActivity(intent);
    }

    public static void addRoute(Route route){
        routes.add(route);
    }

    public void startSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void openObject(View view) {
        Intent intent = new Intent(this, ObjectActivity.class);
        startActivity(intent);
    }
}
