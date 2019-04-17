package com.example.travelplanner;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

public class LoginActivity extends AppCompatActivity {

    private EditText name;
    private EditText password;
    private TextView info;
    private Button login;
    private Button signup;
    private int counter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      name = (EditText)findViewById(R.id.etName);
      password = (EditText)findViewById(R.id.etPassword);
      login = (Button)findViewById(R.id.btnLogin);
      signup = (Button)findViewById(R.id.btnSignUp);
      login.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              validate(name.getText().toString(), password.getText().toString());
          }
      });
      signup.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
              startActivity(intent);
          }
      });
    }

    private void validate(String username, String password){
        if((username.equals("admin")) && (password.equals("111"))){
            Intent intent = new Intent(LoginActivity.this, RoutesActivity.class);
            startActivity(intent);
        }
        else{
            counter--;
            if(counter==0){
                login.setEnabled(false);
            }
        }
    }

}
