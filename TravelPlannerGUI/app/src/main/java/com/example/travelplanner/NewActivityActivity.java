package com.example.travelplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import java.util.Random;

public class NewActivityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_activity);
    }

    public void createActivity(View button) {
        Activity activity = new Activity();

        Spinner typeSpinner = findViewById(R.id.spinner);
        String type = typeSpinner.getSelectedItem().toString();
        activity.setType(type);

        Random random = new Random();
        activity.setId(random.nextInt());

        ActivitiesActivity.addActivity(activity);

        Intent intent = new Intent(this, ActivitiesActivity.class);
        startActivity(intent);
    }
}
