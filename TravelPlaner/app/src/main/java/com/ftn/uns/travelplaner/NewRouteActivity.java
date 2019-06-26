package com.ftn.uns.travelplaner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;

import com.ftn.uns.travelplaner.mock.Mocker;
import com.ftn.uns.travelplaner.model.Route;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class NewRouteActivity extends AppCompatActivity {

    EditText nameView;
    DatePicker dateView;

    Route route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        initState();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_route);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameView = findViewById(R.id.new_route);
        dateView = findViewById(R.id.new_route_date);
    }

    private void initState() {
        this.route = new Route();
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

            Intent intent = new Intent(NewRouteActivity.this, RoutesActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean save() {
        route.name = nameView.getText().toString();
        //route.date = LocalDate.of(dateView.getYear(), dateView.getMonth() + 1, dateView.getDayOfMonth());
        Calendar c = Calendar.getInstance();
        c.set(dateView.getYear(), dateView.getMonth(), dateView.getDayOfMonth());
        route.date = c.getTime();

        Mocker.dbTravel.routes.add(route);
        return true;
    }
}
