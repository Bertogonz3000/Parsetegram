package com.example.bertogonz3000.parstegram;

import android.preference.EditTextPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseUser;

public class SignUpActivity extends AppCompatActivity {

    private EditText etNewUsername, etNewPassword;
    private Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etNewPassword = (EditText) findViewById(R.id.etNewPassword);
        etNewUsername = (EditText) findViewById(R.id.etNewUsername);
        createAccountButton = (Button) findViewById(R.id.createAccountButton);

        createAccountButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Create new user
                ParseUser user = new ParseUser();

                //Set properties if EditTexts are full
                if (!etNewPassword.getText().toString().equals("") &&
                        !etNewUsername.getText().toString().equals("")){

                

                } else {
                    Toast.makeText(getApplicationContext(), "Please enter a username/password", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}
