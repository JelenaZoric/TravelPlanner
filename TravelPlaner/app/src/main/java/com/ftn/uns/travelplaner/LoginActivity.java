package com.ftn.uns.travelplaner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.auth0.android.jwt.JWT;
import com.ftn.uns.travelplaner.auth.AuthInterceptor;
import com.ftn.uns.travelplaner.model.User;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    Context context;
    private OkHttpClient client = new OkHttpClient();

    // JSON <--> Java converter
    private Moshi moshi = new Moshi.Builder()
            .add(Date.class, new Rfc3339DateJsonAdapter())
            .build();


    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        System.out.println("\nLogin onCreate R.string.PREFS_NAME");
        System.out.println(this.getString(R.string.PREFS_NAME));

        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.PREFS_NAME), MODE_PRIVATE).edit();
        editor.putString("token", null);
        editor.apply();

        SharedPreferences prefs = getSharedPreferences(getString(R.string.PREFS_NAME), MODE_PRIVATE);
        String token = prefs.getString("token", null);
        System.out.println("\n\nSystem.out.println(token);");
        System.out.println(token);
        if (token != null) {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.activity_login);
        mEmailView = findViewById(R.id.email);

        mPasswordView = findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        TextView registerView = findViewById(R.id.register);
        registerView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        Button mEmailSignInButton = findViewById(R.id.sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {

        mEmailView.setError(null);
        mPasswordView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            sendLoginRequest();
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private void sendLoginRequest() {
        String email = mEmailView.getText().toString();
        String password = mPasswordView .getText().toString();

        User user = new User(email, password);

        Type type = Types.newParameterizedType(User.class);
        JsonAdapter<User> adapter = moshi.adapter(type);
        String userString = adapter.toJson(user);

        final String url = getString(R.string.BASE_URL) + "login";

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), userString))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        testTextView.setText(R.string.network_failure);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                System.out.println(myResponse);

                if (response.isSuccessful()) {
                    // { "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzIiwiZXhwIjoxNTYyMDI2NzMwfQ.rdFDZiNnUltE3gFnscJSwIKC4DGgDtRE76F9cWRU198NV7I7MlUogtB6Irgp7rVs0E4WGXV_932D8CYsGhaVDQ"}
                    String untrimmedJwt = myResponse.split(":")[1];
                    String jwtString = myResponse.split(":")[1].substring(2, untrimmedJwt.length() - 2);
                    JWT jwt = new JWT(jwtString);

                    System.out.println(jwt.getSubject()); // id user-a

                    SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.PREFS_NAME), MODE_PRIVATE).edit();
                    editor.putString("token", jwtString);
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, TravelsActivity.class);
                    startActivity(intent);
                }
                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mPasswordView.setError(getString(R.string.error_incorrect_password));
                            mPasswordView.requestFocus();
                        }
                    });
                }
            }
        });
    }
}

