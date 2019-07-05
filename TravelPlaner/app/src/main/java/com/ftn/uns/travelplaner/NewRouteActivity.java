package com.ftn.uns.travelplaner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;

import com.ftn.uns.travelplaner.adapters.TravelItemsAdapter;
import com.ftn.uns.travelplaner.adapters.TravelsAdapter;
import com.ftn.uns.travelplaner.auth.AuthInterceptor;
import com.ftn.uns.travelplaner.mock.Mocker;
import com.ftn.uns.travelplaner.model.Activity;
import com.ftn.uns.travelplaner.model.Route;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NewRouteActivity extends AppCompatActivity {

    ProgressDialog nDialog;

    EditText nameView;
    DatePicker dateView;

    Route route;

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
        nDialog = new ProgressDialog(this);
        nDialog.setMessage("Loading...");
        nDialog.setTitle("Creating Route");
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(true);
        nDialog.show();

        route.name = nameView.getText().toString();
        //route.date = LocalDate.of(dateView.getYear(), dateView.getMonth() + 1, dateView.getDayOfMonth());
        Calendar c = Calendar.getInstance();
        c.set(dateView.getYear(), dateView.getMonth(), dateView.getDayOfMonth());
        System.out.println(c.getTime());
        route.date = c.getTime();

        postRoute(route);
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("\nErrororororor\n");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                if (response.isSuccessful()) {
                    Type type = Types.newParameterizedType(Route.class);
                    JsonAdapter<Route> adapter = moshi.adapter(type);
                    Route route = adapter.fromJson(myResponse);

                    nDialog.dismiss();
                    Intent intent = new Intent(NewRouteActivity.this, RoutesActivity.class);
                    startActivity(intent);
                    // Testiranja radi.
                    System.out.println("\nRoute:");
                    System.out.println(route.name);
                } else {
                    System.out.println("\nDoing isNotSuccessful");
                    System.out.println(myResponse);
                    //nDialog.dismiss();
                }
            }
        });
    }

    void postRoute(Route route) {
        JsonAdapter<Route> jsonAdapter = moshi.adapter(Route.class);

        String json = jsonAdapter.toJson(route);
        final String url = getString(R.string.BASE_URL) + "routes/" + TravelsActivity.selected_travel_id;
        try {
            doPostRequest(url, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
