package com.example.bertogonz3000.parstegram.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bertogonz3000.parstegram.Model.Post;
import com.example.bertogonz3000.parstegram.PostAdapter;
import com.example.bertogonz3000.parstegram.R;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;


public class FeedFragment extends Fragment {

    private Button createPostButton, testButton;
    private ArrayList<Post> posts, newPosts;
    private PostAdapter adapter;
    private SwipeRefreshLayout swipeContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_feed, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        RecyclerView rvPosts = (RecyclerView) view.findViewById(R.id.rvPosts);

        posts = new ArrayList<>();
        newPosts = new ArrayList<>();

        adapter = new PostAdapter(posts);
        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(this.getContext()));

        getPosts();

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPosts();
            }
        });

        swipeContainer.setColorSchemeResources(R.color.ig_purple, R.color.ig_red, R.color.ig_yellow);

        createPostButton = (Button) view.findViewById(R.id.createPostButton);
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
