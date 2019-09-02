package com.ftn.uns.travelplaner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.ftn.uns.travelplaner.adapters.TravelItemsAdapter;
import com.ftn.uns.travelplaner.adapters.TravelsAdapter;
import com.ftn.uns.travelplaner.auth.AuthInterceptor;
import com.ftn.uns.travelplaner.model.Item;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TravelItemsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new AuthInterceptor(this))
            .build();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private Moshi moshi = new Moshi.Builder()
            .add(Date.class, new Rfc3339DateJsonAdapter())
            .build();
    private List<Item> items = new ArrayList<>();
    ProgressDialog progressDialog;
    ListView itemsView;
    //Set<Item> items = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_items);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText newItemView = findViewById(R.id.new_travel_item);
        newItemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Item item = new Item();
                    item.name = ((EditText) v).getText().toString();

                    Toast.makeText(getApplicationContext(), String.format("New item: '%s'", item.name), Toast.LENGTH_SHORT);
                    ((EditText) v).setText("");
                }
            }
        });

        newItemView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                Item item = new Item();
                item.name = newItemView.getText().toString();
                setState();
                postItem(item);
                return true;
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        itemsView = findViewById(R.id.travel_items_list);
        setState();
    }

    private void setState() {
        getItems();

        itemsView.setAdapter(new TravelItemsAdapter(TravelItemsActivity.this, items));
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
            Intent intent = new Intent(TravelItemsActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(TravelItemsActivity.this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(TravelItemsActivity.this, LoginActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_travels) {
            Intent intent = new Intent(TravelItemsActivity.this, TravelsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_travel_info) {
            Intent intent = new Intent(TravelItemsActivity.this, TravelInfoActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_routes) {
            Intent intent = new Intent(TravelItemsActivity.this, RoutesActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    void postItem(Item item){

        JsonAdapter<Item> jsonAdapter = moshi.adapter(Item.class);

        String json = jsonAdapter.toJson(item);
        final String url = getString(R.string.BASE_URL) + "items/" + TravelInfoActivity.currentTravelId ;
        try {
            doPostRequest(url, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

                    Type type = Types.newParameterizedType(Item.class);
                    JsonAdapter<Item> adapter = moshi.adapter(type);
                    Item item = adapter.fromJson(myResponse);
                    items.add(item);
                    // Testiranja radi.
                    System.out.println("\nItem:");
                    System.out.println(item.name);
                    System.out.println(item.brought);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            itemsView.setAdapter(new TravelItemsAdapter(TravelItemsActivity.this, items));
                        }
                    });

              /*      runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            testTextView.append(myResponse);
                        }
                    }); */
                }
            }
        });
    }

    private void getItems(){

        //long currentTravelId = TravelInfoActivity.currentTravelId;
        final String url = getString(R.string.BASE_URL) + "travels/" + TravelInfoActivity.currentTravelId.toString() + "/items";

        Request request = new Request.Builder()
                .url(url)
                .build();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Loading items");
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
                    Type type = Types.newParameterizedType(List.class, Item.class);
                    JsonAdapter<List<Item>> adapter = moshi.adapter(type);
                    items = adapter.fromJson(myResponse);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            itemsView.setAdapter(new TravelItemsAdapter(TravelItemsActivity.this, items));
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
