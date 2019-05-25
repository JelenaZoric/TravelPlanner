package com.ftn.uns.travelplaner;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    private RegisterActivity.UserRegisterTask mAuthTask = null;

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mFirstNameView;
    private EditText mLastNameView;
    private AutoCompleteTextView mLocationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);
        mFirstNameView = findViewById(R.id.first_name);
        mLastNameView = findViewById(R.id.last_name);
        mLocationView = findViewById(R.id.location);

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
