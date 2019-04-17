package com.example.travelplanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NewCommentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_comment);
    }

    public void startSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void goBack(View view) {
        Intent intent = new Intent(this, CommentsActivity.class);
        startActivity(intent);
    }

    public void createComment(View view) {
        Comment comment = new Comment();
        comment.setUser("User");

        EditText gradeView = findViewById(R.id.new_comment_grade);
        comment.setGrade(Double.valueOf(gradeView.getText().toString()));

        EditText commentView = findViewById(R.id.new_comment_comment);
        comment.setComment(commentView.getText().toString());

        CommentsActivity.addComment(comment);
        Intent intent = new Intent(this, CommentsActivity.class);
        startActivity(intent);
    }
}
