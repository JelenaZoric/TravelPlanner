package com.example.travelplanner;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

public class ObjectActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object);
    }

    public void startSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void openComments(View view) {
        Intent intent = new Intent(this, CommentsActivity.class);
        startActivity(intent);
    }
}
