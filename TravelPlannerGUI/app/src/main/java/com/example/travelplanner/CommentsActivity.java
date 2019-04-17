package com.example.travelplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CommentsActivity extends Activity {

    private static List<Comment> comments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ListView scrollView = findViewById(R.id.comments_view);
        scrollView.setAdapter(new ArrayAdapter<>(CommentsActivity.this,
                android.R.layout.simple_list_item_1, comments));
    }

    public void openNewComment(View view) {
        Intent intent = new Intent(this, NewCommentActivity.class);
        startActivity(intent);
    }

    public static void addComment(Comment comment) {
        comments.add(comment);
    }

    public void startSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void goBack() {

    }
}

