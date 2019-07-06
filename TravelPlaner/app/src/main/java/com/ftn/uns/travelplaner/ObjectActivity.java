package com.ftn.uns.travelplaner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ftn.uns.travelplaner.adapters.CommentsAdapter;
import com.ftn.uns.travelplaner.auth.AuthInterceptor;
import com.ftn.uns.travelplaner.mock.Mocker;
import com.ftn.uns.travelplaner.model.ActivityType;
import com.ftn.uns.travelplaner.model.Object;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ObjectActivity extends AppCompatActivity {

    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new AuthInterceptor(this))
            .build();

    private Moshi moshi = new Moshi.Builder()
            .add(Date.class, new Rfc3339DateJsonAdapter())
            .build();

    ProgressDialog progressDialog;
    private Object object;

    ImageView imageView;
    TextView addressView;
    TextView emailView;
    TextView phoneView;
    TextView ratingView;
    TextView descriptionView;
    ListView commentsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = findViewById(R.id.object_image);
        addressView = findViewById(R.id.object_address);
        emailView = findViewById(R.id.object_email);
        phoneView = findViewById(R.id.object_phone);
        ratingView = findViewById(R.id.object_rating);
        descriptionView = findViewById(R.id.object_description);
        commentsView = findViewById(R.id.comments);

        FloatingActionButton fab = findViewById(R.id.new_comment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ObjectActivity.this, NewCommentActivity.class);
                intent.putExtra("object_id", object.id);
                startActivity(intent);
            }
        });

        setState();
    }

    private void setState() {
        getObject();
    }

    void updateView() {
        setTitle(object.name);
        if (object.imagePath != null) {
            imageView.setImageURI(Uri.parse(object.imagePath));
        }

        addressView.setText(object.address);
        ratingView.setText(String.format(Locale.getDefault(), "%.1f", object.rating));
        descriptionView.setText(object.description);
        emailView.setText(object.email);
        phoneView.setText(object.phoneNumber);

        commentsView.setAdapter(new CommentsAdapter(ObjectActivity.this, object.comments));
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
            Intent intent = new Intent(ObjectActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    void getObject() {
        final String url = getString(R.string.BASE_URL) + "objects/" + RouteListActivity.current_activity_id;
        Request request = new Request.Builder()
                .url(url)
                .build();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Loading object");
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
                if(response.isSuccessful()) {
                    Type type = Types.newParameterizedType(Object.class);
                    JsonAdapter<Object> adapter = moshi.adapter(type);
                    object = adapter.fromJson(myResponse);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateView();
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
