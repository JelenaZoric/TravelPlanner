package com.ftn.uns.travelplaner;

import android.app.ProgressDialog;
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
import com.ftn.uns.travelplaner.auth.AuthInterceptor;
import com.ftn.uns.travelplaner.mock.Mocker;
import com.ftn.uns.travelplaner.model.Activity;
import com.ftn.uns.travelplaner.model.ActivityType;
import com.ftn.uns.travelplaner.model.Object;
import com.ftn.uns.travelplaner.model.Travel;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NewActivityActivity extends AppCompatActivity {

    ProgressDialog nDialog;

    Spinner typeView;
    Spinner timeView;
    Activity activity;
    ListView objectsView;
    private List<Object> objects = new ArrayList<>();
    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new AuthInterceptor(this))
            .build();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private Moshi moshi = new Moshi.Builder()
            .add(Date.class, new Rfc3339DateJsonAdapter())
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        initState();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        objectsView = findViewById(R.id.objects);

        Spinner spinner = findViewById(R.id.activity_type);
        spinner.setAdapter(new ArrayAdapter<>(
                this, R.layout.support_simple_spinner_dropdown_item, ActivityType.values()));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = parentView.getSelectedItem().toString();
                /*
                Random random = new Random();
                mockerObjects = Mocker.mockObjects(random.nextInt(2) + 1, ActivityType.valueOf(selectedItem.toUpperCase()), -1);
                */
                //String location =
                getObjectsByType(ActivityType.valueOf(selectedItem.toUpperCase()));
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
                activity.object = objects.get(position);

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
        String[] times = timeView.getSelectedItem().toString().split(":");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(times[0]));
        c.set(Calendar.MINUTE, Integer.parseInt(times[1]));
        Date time = c.getTime();
        activity.time = time;
        activity.type = ActivityType.valueOf(typeView.getSelectedItem().toString().toUpperCase());

        postActivity(activity);
        return true;
    }

    void doPostRequest(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
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
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    Type type = Types.newParameterizedType(Activity.class);
                    JsonAdapter<Activity> adapter = moshi.adapter(type);
                    Activity activity = adapter.fromJson(myResponse);

                    // Testiranja radi.
                    System.out.println("\nActivity:");
                    // System.out.println(travel.currency);
                    System.out.println(activity.type.toString());


                 /*   runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            testTextView.append(myResponse);
                        }
                    });   */
                }
            }
        });
    }

    void postActivity(Activity activity) {
        JsonAdapter<Activity> jsonAdapter = moshi.adapter(Activity.class);

        String json = jsonAdapter.toJson(activity);
        final String url = getString(R.string.BASE_URL) + "activities/" + RoutesActivity.currentRouteId
                + "/" + activity.object.id;
        try {
            doPostRequest(url, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void getObjectsByType(ActivityType type) {
        //get list of objects of certain type
        final String url = getString(R.string.BASE_URL) + "objects?location=" + TravelInfoActivity.currentTravelDestination
                + "&type=" + type.toString();
        Request request = new Request.Builder()
                .url(url)
                .build();
        nDialog = new ProgressDialog(this);
        nDialog.setTitle("Loading objects");
        nDialog.setMessage("Loading...");
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(true);
        nDialog.show();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("\nErrororororor\n");
                        nDialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                nDialog.dismiss();
                final String myResponse = response.body().string();

                if(response.isSuccessful()) {
                    Type type = Types.newParameterizedType(List.class, Object.class);
                    JsonAdapter<List<Object>> adapter = moshi.adapter(type);
                    objects = adapter.fromJson(myResponse);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            objectsView.setAdapter(new ObjectsAdapter(NewActivityActivity.this, objects));

                            setOnClickListener(objectsView);
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Manji eror");
                        }
                    });
                }
            }
        });
    }
}
