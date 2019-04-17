package com.example.travelplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ActivitiesActivity extends AppCompatActivity {

    private static List<Activity> activities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        //Toolbar toolbar = findViewById(R.id.activities_toolbar);
        //setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ListView listView = findViewById(R.id.activities_listview);
        listView.setAdapter(new ArrayAdapter<>(ActivitiesActivity.this,
                android.R.layout.simple_list_item_1, activities));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(ActivitiesActivity.this, ActivityActivity.class);
                Activity activity = activities.get((int)id);
                intent.putExtra("activity", activity);

                startActivity(intent);
            }
        });
    }

    public void openNewActivityForm(View button) {
        Intent intent = new Intent(this, NewActivityActivity.class);
        startActivity(intent);
    }

    public static void addActivity(Activity activity) { activities.add(activity); }

    public void startSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
