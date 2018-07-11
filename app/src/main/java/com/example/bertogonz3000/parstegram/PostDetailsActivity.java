package com.example.bertogonz3000.parstegram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PostDetailsActivity extends AppCompatActivity {

    private ImageView postDetailsImage;
    private TextView postDetailsUsername, postDetailsDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        postDetailsDescription = (TextView) findViewById(R.id.postDetailsDescription);
        postDetailsImage = (ImageView) findViewById(R.id.postDetailsImage);
        postDetailsUsername = (TextView) findViewById(R.id.postDetailsUsername);

        populatePost();
    }

    public void populatePost(){

    }

}
