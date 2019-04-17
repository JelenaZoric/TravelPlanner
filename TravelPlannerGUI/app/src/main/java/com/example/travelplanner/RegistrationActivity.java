package com.example.travelplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText email;
    private EditText country;
    private EditText city;
    private EditText street;
    private EditText houseNumber;
    private Button submit;
    private Location location = new Location();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    /*    Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);    */
        username = (EditText)findViewById(R.id.etUsername);
        password = (EditText)findViewById(R.id.etPassword);
        email = (EditText)findViewById(R.id.etEmail);
        country = (EditText)findViewById(R.id.etCountry);
        city = (EditText)findViewById(R.id.etCity);
        street = (EditText)findViewById(R.id.etStreet);
        houseNumber = (EditText)findViewById(R.id.etHouseNumber);
        submit = (Button) findViewById(R.id.btnSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrate();
            }
        });
    }

    private void registrate(){
        User newUser = new User();
        newUser.setUsername(username.getText().toString());
        newUser.setPassword(password.getText().toString());
        newUser.setEmail(email.getText().toString());

        location.setCountry(country.getText().toString());
        location.setCity(city.getText().toString());
        location.setStreet(street.getText().toString());
        location.setNumber(houseNumber.getText().toString());
        newUser.setLocation(location);

        Intent intent = new Intent(RegistrationActivity.this, RoutesActivity.class);
        startActivity(intent);
    }
}
