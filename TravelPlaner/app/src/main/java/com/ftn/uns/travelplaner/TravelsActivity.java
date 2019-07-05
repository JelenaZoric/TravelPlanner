package com.ftn.uns.travelplaner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ftn.uns.travelplaner.adapters.TravelsAdapter;
import com.ftn.uns.travelplaner.auth.AuthInterceptor;
import com.ftn.uns.travelplaner.mock.Mocker;
import com.ftn.uns.travelplaner.model.Travel;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TravelsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new AuthInterceptor(this))
            .build();

    private Moshi moshi = new Moshi.Builder()
            .add(Date.class, new Rfc3339DateJsonAdapter())
            .build();

    ProgressDialog progressDialog;  // https://stackoverflow.com/questions/35079083/android-loading-circle-spinner-between-two-activity

    ListView travelsView;

    static Long selected_travel_id;

    private List<Travel> travels = new ArrayList<Travel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travels);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        travelsView = findViewById(R.id.travels_list);

        FloatingActionButton fab = findViewById(R.id.new_travel);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TravelsActivity.this, NewTravelActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setState();
    }

    private void setState() {
        getTravels();
        travelsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Long travelId = travels.get(position).id;
                Intent intent = new Intent(TravelsActivity.this, TravelInfoActivity.class);
                intent.putExtra("current_travel_id", travelId);
                selected_travel_id = travelId;
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(TravelsActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(TravelsActivity.this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(TravelsActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void getTravels(){

        final String url = getString(R.string.BASE_URL) + "travels";

        Request request = new Request.Builder()
                .url(url)
                .build();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Loading travels");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        progressDialog.show();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("\nErrororororor\n");
                        progressDialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                progressDialog.dismiss();

                final String myResponse = response.body().string();
                System.out.println(myResponse);

                if (response.isSuccessful()) {
                    Type type = Types.newParameterizedType(List.class, Travel.class);
                    JsonAdapter<List<Travel>> adapter = moshi.adapter(type);
                    travels = adapter.fromJson(myResponse);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            travelsView.setAdapter(new TravelsAdapter(TravelsActivity.this, travels));
                        }
                    });
                }
                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("\nManji Errororor\n");
                        }
                    });
                }
            }
        });
    }
}
