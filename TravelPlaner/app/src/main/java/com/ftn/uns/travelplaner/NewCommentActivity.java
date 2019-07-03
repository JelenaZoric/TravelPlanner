package com.ftn.uns.travelplaner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SeekBar;

import com.ftn.uns.travelplaner.mock.Mocker;
import com.ftn.uns.travelplaner.model.Comment;

public class NewCommentActivity extends AppCompatActivity {

    EditText textView;
    SeekBar ratingView;

    private Comment comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initState();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_comment);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView = findViewById(R.id.new_comment);
        ratingView = findViewById(R.id.rating);
    }

    private void initState() {
        comment = new Comment();
        comment.user = Mocker.db;
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

            Intent intent = new Intent(NewCommentActivity.this, ObjectActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean save() {
        comment.text = textView.getText().toString();
        comment.rating = ratingView.getProgress() % 5 + 1;

        Mocker.dbActivity.object.comments.add(comment);

        return true;
    }
}
