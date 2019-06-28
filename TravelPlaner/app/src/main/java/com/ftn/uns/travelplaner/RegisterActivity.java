package com.ftn.uns.travelplaner;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ftn.uns.travelplaner.mock.Mocker;
import com.ftn.uns.travelplaner.model.Location;
import com.ftn.uns.travelplaner.model.Travel;
import com.ftn.uns.travelplaner.model.User;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    private OkHttpClient client = new OkHttpClient();

    // JSON <--> Java converter
    private Moshi moshi = new Moshi.Builder()
            .add(Date.class, new Rfc3339DateJsonAdapter())
            .build();

    private RegisterActivity.UserRegisterTask mAuthTask = null;

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mFirstNameView;
    private EditText mLastNameView;
    private AutoCompleteTextView mLocationView;

    private TextView testTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);
        mFirstNameView = findViewById(R.id.first_name);
        mLastNameView = findViewById(R.id.last_name);
        mLocationView = findViewById(R.id.location);

        testTextView = findViewById(R.id.testTextView);

        TextView loginView = findViewById(R.id.login);
        loginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        Button mSignUpButton = findViewById(R.id.sign_up_button);
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

        run(); // Radi fetching sa bekenda

        Button testButton = findViewById(R.id.testButton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("\nTestButton Click!");
                run();
            }
        });
    }

    void run() {
        final String url = getString(R.string.BASE_URL) + "travels";
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        testTextView.setText(R.string.network_failure);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    Type type = Types.newParameterizedType(List.class, Travel.class);
                    JsonAdapter<List<Travel>> adapter = moshi.adapter(type);
                    List<Travel> travels = adapter.fromJson(myResponse);

                    for (Travel travel : travels) {  // Testiranja radi.
                        System.out.println("\nTravel:");
                        System.out.println(travel.currency);
                        System.out.println(travel.destination.departure);
                        System.out.println(travel.accommodation.email);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            testTextView.append(myResponse);
                        }
                    });
                }
            }
        });
    }

    private void attemptRegister() {
        if (mAuthTask != null) {
            return;
        }

        mEmailView.setError(null);
        mPasswordView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String firstName = mFirstNameView.getText().toString();
        String lastName = mLastNameView.getText().toString();
        String location = mLocationView.getText().toString();

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
            mAuthTask = new RegisterActivity.
                    UserRegisterTask(email, password, firstName, lastName, location);

            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 6 && !password.toLowerCase().equals(password);
    }

    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final String mFirstName;
        private final String mLastName;
        private final String mLocation;

        public UserRegisterTask(
                String mEmail,
                String mPassword,
                String mFirstName,
                String mLastName,
                String mLocation) {

            this.mEmail = mEmail;
            this.mPassword = mPassword;
            this.mFirstName = mFirstName;
            this.mLastName = mLastName;
            this.mLocation = mLocation;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                Mocker.db = new User();
                Mocker.db.email = mEmail;
                Mocker.db.firstName = mFirstName;
                Mocker.db.lastName = mLastName;

                String[] parts = mLocation.split(",");
                Mocker.db.location = new Location();
                Mocker.db.location.city = parts[0];
                Mocker.db.location.country = parts[1];

                Intent intent = new Intent(RegisterActivity.this, TravelsActivity.class);
                startActivity(intent);
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}
