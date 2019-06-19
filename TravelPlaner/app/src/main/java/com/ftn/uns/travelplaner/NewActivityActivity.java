package com.ftn.uns.travelplaner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.ftn.uns.travelplaner.adapters.ObjectsAdapter;
import com.ftn.uns.travelplaner.mock.Mocker;
import com.ftn.uns.travelplaner.model.Activity;
import com.ftn.uns.travelplaner.model.ActivityType;
import com.ftn.uns.travelplaner.model.Object;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class NewActivityActivity extends AppCompatActivity {

    Spinner typeView;
    Spinner timeView;
    Activity activity;
    private List<Object> mockerObjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        initState();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner spinner = findViewById(R.id.activity_type);
        spinner.setAdapter(new ArrayAdapter<>(
                this, R.layout.support_simple_spinner_dropdown_item, ActivityType.values()));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = parentView.getSelectedItem().toString();

                ListView objectsView = findViewById(R.id.objects);

                Random random = new Random();
                mockerObjects = Mocker.mockObjects(random.nextInt(2) + 1, ActivityType.valueOf(selectedItem.toUpperCase()));

                objectsView.setAdapter(new ObjectsAdapter(NewActivityActivity.this, mockerObjects));

                setOnClickListener(objectsView);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });


        timeView = findViewById(R.id.activity_time);
        typeView = findViewById(R.id.activity_type);

        setListeners(timeView);
    }

    private void setListeners(Spinner view) {
        View.OnTouchListener spinnerOnTouch = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    showTimePickerDialog(v);
                }
                return true;
            }
        };

        View.OnKeyListener spinnerOnKey = new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                    showTimePickerDialog(v);
                    return true;
                } else {
                    return false;
                }
            }
        };

        view.setOnTouchListener(spinnerOnTouch);
        view.setOnKeyListener(spinnerOnKey);
    }

    private void showTimePickerDialog(View v) {
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.context = NewActivityActivity.this;
        newFragment.view = (Spinner) v;
        newFragment.show(this.getSupportFragmentManager(), "Time");
    }

    private void setOnClickListener(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                activity.object = mockerObjects.get(position);
                ListView objectsView = findViewById(R.id.objects);
                objectsView.setAdapter(new ObjectsAdapter(NewActivityActivity.this, Arrays.asList(activity.object)));
            }
        });
    }

    private void initState() {
        this.activity = new Activity();
        this.activity.object = new Object();
        this.activity.type = ActivityType.ACCOMMODATION;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_new_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            save();

            Intent intent = new Intent(NewActivityActivity.this, RouteListActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean save() {
        //activity.time = LocalTime.parse(timeView.getSelectedItem().toString(), DateTimeFormatter.ofPattern("HH:mm"));
        Date time = new Date();
        String[] times = timeView.getSelectedItem().toString().split(":");
        time.setHours(Integer.parseInt(times[0]));
        time.setMinutes(Integer.parseInt(times[1]));
        activity.time = time;
        activity.type = ActivityType.valueOf(typeView.getSelectedItem().toString().toUpperCase());

        Mocker.dbRoute.activities.add(activity);
        return true;
    }
}
