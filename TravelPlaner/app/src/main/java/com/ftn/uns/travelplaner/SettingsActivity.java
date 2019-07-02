package com.ftn.uns.travelplaner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       if(findViewById(R.id.fragment_settings) != null) {

           if(savedInstanceState != null) {
               return;
           }

           getFragmentManager()
                   .beginTransaction()
                   .add(R.id.fragment_settings, new SettingsFragment())
                   .commit();

       }
    }
}
