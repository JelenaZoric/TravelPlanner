package com.example.travelplanner;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setFontSpinner();
        setLanguageSpinner();
        setLandmarksSortedBy();
    }

    private void setFontSpinner() {
        Spinner fontSpinner = findViewById(R.id.font_settings);

        String[] fontOptions={"Large", "Medium", "Small"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, fontOptions);
        spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );

        fontSpinner.setAdapter(spinnerArrayAdapter);
    }

    private void setLanguageSpinner() {
        Spinner fontSpinner = findViewById(R.id.language_settings);

        String[] fontOptions={"English", "Serbian"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, fontOptions);
        spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );

        fontSpinner.setAdapter(spinnerArrayAdapter);
    }

    private void setLandmarksSortedBy() {
        Spinner fontSpinner = findViewById(R.id.sort_settings);

        String[] fontOptions={"Sort by distance", "Sort by rating"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, fontOptions);
        spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );

        fontSpinner.setAdapter(spinnerArrayAdapter);
    }
}
