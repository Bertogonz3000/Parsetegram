package com.example.bertogonz3000.parstegram;

import android.preference.EditTextPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    private EditText etNewUsername, etNewPassword, etNewEmail;
    private Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etNewPassword = (EditText) findViewById(R.id.etNewPassword);
        etNewUsername = (EditText) findViewById(R.id.etNewUsername);
        createAccountButton = (Button) findViewById(R.id.createAccountButton);
        etNewEmail = (EditText) findViewById(R.id.etNewEmail);

        createAccountButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Create new user
                ParseUser user = new ParseUser();

                //Set properties if EditTexts are full
                if (!etNewPassword.getText().toString().equals("") &&
                        !etNewUsername.getText().toString().equals("") &&
                        !etNewEmail.getText().toString().equals("")){

                    //apparently parse takes care to check that usernames/emails aren't duplicated
                    user.setUsername(etNewUsername.getText().toString());
                    user.setPassword(etNewPassword.getText().toString());
                    user.setEmail(etNewEmail.getText().toString());

                    //Do i need to put anything?

                    //invoke signUpInBackground
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            //If there's no error, show them back to the login page to login
                            if (e == null){
                                Toast.makeText(getApplicationContext(),
                                        "Hooray! You now have an account, please sign in",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Sign Up Failure, user may already exist",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    //if not, ask user to fill
                    Toast.makeText(getApplicationContext(),
                            "Please enter a username/password", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}
