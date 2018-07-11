package com.example.bertogonz3000.parstegram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FeedActivity extends AppCompatActivity {

    private Button createPostButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        createPostButton = (Button) findViewById(R.id.createPostButton);
    }

    public void viewCreatePost(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }


}
