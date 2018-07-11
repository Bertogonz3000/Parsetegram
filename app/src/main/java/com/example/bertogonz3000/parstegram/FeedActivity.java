package com.example.bertogonz3000.parstegram;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bertogonz3000.parstegram.Model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    private Button createPostButton, testButton;
    private ArrayList<Post> posts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        createPostButton = (Button) findViewById(R.id.createPostButton);
        testButton = (Button) findViewById(R.id.testButton);

        posts = new ArrayList<>();

        getPosts();

    }

    public void viewCreatePost(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }


    public void getPosts(){
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null){
                    posts.addAll(objects);
                } else {
                    Log.e("FeedActivity", "Failed to get posts");
                }
            }
        });
    }


}
