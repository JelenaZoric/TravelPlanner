package com.ftn.uns.travelplaner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SeekBar;

import com.ftn.uns.travelplaner.auth.AuthInterceptor;
import com.ftn.uns.travelplaner.mock.Mocker;
import com.ftn.uns.travelplaner.model.Comment;
import com.ftn.uns.travelplaner.model.Travel;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NewCommentActivity extends AppCompatActivity {

    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new AuthInterceptor(this))
            .build();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private Moshi moshi = new Moshi.Builder()
            .add(Date.class, new Rfc3339DateJsonAdapter())
            .build();

    ProgressDialog nDialog;

    EditText textView;
    SeekBar ratingView;
    private Long objectId;

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
        ratingView.setMax(5);
    }

    private void initState() {
        comment = new Comment();
        objectId = getIntent().getLongExtra("object_id", 0);
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
        nDialog = new ProgressDialog(this);
        nDialog.setMessage("Loading...");
        nDialog.setTitle("Creating Comment");
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(true);
        nDialog.show();

        comment.text = textView.getText().toString();
        comment.rating = ratingView.getProgress();

        postComment(comment);

        return true;
    }

    void postComment(Comment comment) {
        JsonAdapter<Comment> jsonAdapter = moshi.adapter(Comment.class);
        String json = jsonAdapter.toJson(comment);
        final String url = getString(R.string.BASE_URL) + "comments/" + objectId;
        doPostRequest(url, json);
    }

    void doPostRequest(String url, String json) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();

                if (response.isSuccessful()) {
                    Type type = Types.newParameterizedType(Comment.class);
                    JsonAdapter<Comment> adapter = moshi.adapter(type);
                    Comment comment = adapter.fromJson(myResponse);
                    nDialog.dismiss();
                    Intent intent = new Intent(NewCommentActivity.this, ObjectActivity.class);
                    startActivity(intent);
                }
                else { // npr. unauthorized 401
                    System.out.println("\nDoing isNotSuccessful");
                    System.out.println(myResponse);
                    nDialog.dismiss();
                }
            }
        });
    }
}
