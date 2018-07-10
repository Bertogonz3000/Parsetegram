package com.example.bertogonz3000.parstegram;

import android.content.Context;
import android.content.Intent;
import android.preference.EditTextPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText etPassword, etUsername;
    private Button loginButton, signUpButton;
    //should I be grabbing the context like this?
    Context context;

    private Boolean loggingIn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //init context
        context = this.getBaseContext();

        //if the current user exists, run with them
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null){
            final Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            //put finish here so user can't logout just by hitting the back button
            finish();
            loggingIn = false;
        }

        //initializing widets
        etPassword = (EditText) findViewById(R.id.etPassword);
        etUsername = (EditText) findViewById(R.id.etUsername);
        loginButton = (Button) findViewById(R.id.loginButton);
        signUpButton = (Button) findViewById(R.id.signUpButton);

        loginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                login(username, password);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, SignUpActivity.class);
                startActivity(i);
            }
        });
    }

    private void login(String username, String password){
        if (!loggingIn) {
            loggingIn = true;
            ParseUser.logInInBackground(username, password, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (e == null) {
                        Log.d("LoginActivity", "Login Successful");
                        Toast.makeText(context, "Logging in", Toast.LENGTH_SHORT).show();
                        final Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        //put finish here so user can't logout just by hitting the back button
                        finish();
                        loggingIn = false;
                    } else {
                        Log.e("LoginActivity", "Login Failure");
                        Toast.makeText(context, "Login Fail", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                        loggingIn = false;
                    }
                }
            });
        } else {
            Toast.makeText(context, "Already Logging in, please wait", Toast.LENGTH_SHORT).show();
        }
    }


}
