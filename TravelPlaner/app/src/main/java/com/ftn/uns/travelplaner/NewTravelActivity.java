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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.ftn.uns.travelplaner.auth.AuthInterceptor;
import com.ftn.uns.travelplaner.mock.Mocker;
import com.ftn.uns.travelplaner.model.ActivityType;
import com.ftn.uns.travelplaner.model.Location;
import com.ftn.uns.travelplaner.model.Object;
import com.ftn.uns.travelplaner.model.Transportation;
import com.ftn.uns.travelplaner.model.TransportationMode;
import com.ftn.uns.travelplaner.model.Travel;
import com.ftn.uns.travelplaner.util.DateTimeFormatter;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NewTravelActivity extends AppCompatActivity {

    EditText originView;
    Spinner dateFromView;
    Spinner timeFromView;
    EditText destinationView;
    Spinner dateToView;
    Spinner timeToView;

    Spinner travelMode;

    EditText hotelView;
    EditText addressView;
    EditText emailView;
    EditText phoneView;
    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new AuthInterceptor(this))
            .build();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private Moshi moshi = new Moshi.Builder()
            .add(Date.class, new Rfc3339DateJsonAdapter())
            .build();
    Travel travel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        initState();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_travel);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Spinner spinner = findViewById(R.id.mode);
        spinner.setAdapter(new ArrayAdapter<>(
                this, R.layout.support_simple_spinner_dropdown_item, TransportationMode.values()));

        originView = findViewById(R.id.origin);
        dateFromView = findViewById(R.id.origin_date);
        setListeners(dateFromView, false);
        timeFromView = findViewById(R.id.origin_time);
        setListeners(timeFromView, true);

        destinationView = findViewById(R.id.destination);
        dateToView = findViewById(R.id.destination_date);
        setListeners(dateToView, false);
        timeToView = findViewById(R.id.destination_time);
        setListeners(timeToView, true);

        travelMode = findViewById(R.id.mode);

        hotelView = findViewById(R.id.accommodation_name);
        addressView = findViewById(R.id.accommodation_address);
        emailView = findViewById(R.id.accommodation_email);
        phoneView = findViewById(R.id.accommodation_phone_number);
    }

    private void setListeners(Spinner view, final boolean isTime) {
        View.OnTouchListener spinnerOnTouch = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (isTime) {
                        showTimePickerDialog(v);
                    } else {
                        showDatePickerDialog(v);
                    }
                }
                return true;
            }
        };

        View.OnKeyListener spinnerOnKey = new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                    if (isTime) {
                        showTimePickerDialog(v);
                    } else {
                        showDatePickerDialog(v);
                    }
                    return true;
                } else {
                    return false;
                }
            }
        };

        view.setOnTouchListener(spinnerOnTouch);
        view.setOnKeyListener(spinnerOnKey);
    }

    private void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.context = NewTravelActivity.this;
        newFragment.view = (Spinner) v;
        newFragment.show(this.getSupportFragmentManager(), "Date");
    }

    private void showTimePickerDialog(View v) {
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.context = NewTravelActivity.this;
        newFragment.view = (Spinner) v;
        newFragment.show(this.getSupportFragmentManager(), "Time");
    }

    private void initState() {
        travel = new Travel();

        travel.accommodation = new Object();
        travel.accommodation.location = new Location();

        travel.destination = new Transportation();
        travel.destination.location = new Location();
        travel.origin = new Transportation();
        travel.origin.location = new Location();
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

            Intent intent = new Intent(NewTravelActivity.this, TravelsActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean save() {
        travel.origin.location = createLocation(originView);
        travel.origin.departure = createTimestamp(dateFromView, timeFromView);

        travel.destination.location = createLocation(destinationView);
        travel.destination.departure = createTimestamp(dateToView, timeToView);

        travel.mode = TransportationMode.valueOf(travelMode.getSelectedItem().toString().toUpperCase());

        travel.accommodation.name = hotelView.getText().toString();
        travel.accommodation.type = ActivityType.ACCOMMODATION;
        travel.accommodation.location = travel.destination.location;
        travel.accommodation.email = emailView.getText().toString();
        travel.accommodation.address = addressView.getText().toString();
        travel.accommodation.phoneNumber = phoneView.getText().toString();

//        Mocker.db.travels.add(travel);
        postTravel(travel);
        return true;
    }

    private Location createLocation(EditText view) {
        Location location = new Location();

        String[] content = view.getText().toString().split(",");
        location.city = content[0];
        location.country = content[1];

        List<Double> coords = Mocker.mockCoordinates(-1);
        location.latitude = coords.get(0);
        location.longitude = coords.get(1);

        return location;
    }

    private Date createTimestamp(Spinner dateView, Spinner timeView) {
        String contentDate = dateView.getSelectedItem().toString();
        String contentTime = timeView.getSelectedItem().toString();
        String[] dates = contentDate.split("/");
        String[] times = contentTime.split(":");

        int year = Integer.parseInt(dates[2]);
        int month = Integer.parseInt(dates[1]);
        int day = Integer.parseInt(dates[0]);
        int hour = Integer.parseInt(times[0]);
        int minute = Integer.parseInt(times[1]);
        Calendar c = Calendar.getInstance();
        c.set(year, month-1, day, hour, minute);

        Date date = c.getTime();
        String formattedDate = DateTimeFormatter.formatDate(date);
        String formattedTime = DateTimeFormatter.formatTime(date);

        return date;
        /*
        return LocalDateTime.of(
                LocalDate.parse(contentDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                LocalTime.parse(contentTime, DateTimeFormatter.ofPattern("HH:mm")));
        */
    }

    void doPostRequest(String url, String json) throws IOException {
        System.out.println("\nDoing doPostRequest");
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("\nDoing Failure");
                e.printStackTrace();
          /*      runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        testTextView.setText(R.string.network_failure);
                    }
                }); */
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("\nDoing onResponse");
                final String myResponse = response.body().string();

                if (response.isSuccessful()) {
                    System.out.println("\nDoing isSuccessful");
                    System.out.println(myResponse);

                    Type type = Types.newParameterizedType(Travel.class);
                    JsonAdapter<Travel> adapter = moshi.adapter(type);
                    Travel travel = adapter.fromJson(myResponse);

                    // Testiranja radi.
                    System.out.println("\nTravel:");
                    // System.out.println(travel.currency);
                    System.out.println(travel.destination.departure);
                    System.out.println(travel.accommodation.email);


                 /*   runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            testTextView.append(myResponse);
                        }
                    });   */
                }
                else { // npr. unauthorized 401
                    System.out.println("\nDoing isNotSuccessful");
                    System.out.println(myResponse);
                }
            }
        });
    }

    void postTravel(Travel travel){

        JsonAdapter<Travel> jsonAdapter = moshi.adapter(Travel.class);

        String json = jsonAdapter.toJson(travel);
        final String url = getString(R.string.BASE_URL) + "travels";
        try {
            doPostRequest(url, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
