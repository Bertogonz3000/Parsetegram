package com.example.bertogonz3000.parstegram;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private ArrayList<Post> posts, newPosts;
    private PostAdapter adapter;
    private SwipeRefreshLayout swipeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        RecyclerView rvPosts = (RecyclerView) findViewById(R.id.rvPosts);

        posts = new ArrayList<>();
        newPosts = new ArrayList<>();

        adapter = new PostAdapter(posts);
        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));

        getPosts();

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPosts();
            }
        });

        swipeContainer.setColorSchemeResources(R.color.ig_purple, R.color.ig_red, R.color.ig_yellow);

        createPostButton = (Button) findViewById(R.id.createPostButton);



    }

    public void viewCreatePost(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }


    public void getPosts(){
        final Post.Query query = new Post.Query();
        query.getTop().withUser();
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null){
                    newPosts.clear();
                    newPosts.addAll(objects);
                    adapter.clear();
                    adapter.addAll(newPosts);
                    swipeContainer.setRefreshing(false);
                } else {
                    Log.e("FeedActivity", "Failed to get posts");
                }
            }
        });
    }


}
