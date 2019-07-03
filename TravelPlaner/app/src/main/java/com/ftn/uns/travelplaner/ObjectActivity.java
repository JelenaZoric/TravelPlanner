package com.ftn.uns.travelplaner;

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
import com.ftn.uns.travelplaner.mock.Mocker;
import com.ftn.uns.travelplaner.model.ActivityType;
import com.ftn.uns.travelplaner.model.Object;

import java.util.Locale;
import java.util.Random;

public class ObjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = findViewById(R.id.new_comment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ObjectActivity.this, NewCommentActivity.class);
                startActivity(intent);
            }
        });

        setState();
    }

    private void setState() {

        Object object = Mocker.dbActivity.object;

        setTitle(object.name);
        ImageView imageView = findViewById(R.id.object_image);
        TextView addressView = findViewById(R.id.object_address);
        TextView emailView = findViewById(R.id.object_email);
        TextView phoneView = findViewById(R.id.object_phone);
        TextView ratingView = findViewById(R.id.object_rating);
        TextView descriptionView = findViewById(R.id.object_description);

        if (object.imagePath != null) {
            imageView.setImageURI(Uri.parse(object.imagePath));
        }

        addressView.setText(object.address);
        ratingView.setText(String.format(Locale.getDefault(), "%.1f", object.rating));
        descriptionView.setText(object.description);
        emailView.setText(object.email);
        phoneView.setText(object.phoneNumber);

        ListView commentsView = findViewById(R.id.comments);
        commentsView.setAdapter(new CommentsAdapter(ObjectActivity.this, Mocker.dbActivity.object.comments));
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
}
