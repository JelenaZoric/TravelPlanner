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

        Random random = new Random();
        Object object = Mocker.mockObjects(
                1, ActivityType.values()[random.nextInt(ActivityType.values().length - 1) + 1])
                .get(0);

        setTitle(object.name);
        ImageView imageView = findViewById(R.id.object_image);
        TextView addressView = findViewById(R.id.object_address);
        TextView ratingView = findViewById(R.id.object_rating);
        TextView descriptionView = findViewById(R.id.object_description);

        imageView.setImageURI(Uri.parse(object.imagePath));
        addressView.setText(object.address);
        ratingView.setText(String.valueOf(object.rating));
        descriptionView.setText(object.description);

        ListView commentsView = findViewById(R.id.comments);
        commentsView.setAdapter(new CommentsAdapter(ObjectActivity.this, Mocker.mockComments(random.nextInt(10) + 1)));
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
