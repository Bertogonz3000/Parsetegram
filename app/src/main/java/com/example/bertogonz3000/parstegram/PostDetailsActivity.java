package com.example.bertogonz3000.parstegram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bertogonz3000.parstegram.Model.Post;
import com.example.bertogonz3000.parstegram.Model.TimeFormatter;
import com.parse.GetCallback;
import com.parse.ParseException;

import org.w3c.dom.Text;

public class PostDetailsActivity extends AppCompatActivity {

    private ImageView postDetailsImage;
    private TextView postDetailsUsername, postDetailsDescription, tvTimeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        postDetailsDescription = (TextView) findViewById(R.id.postDetailsDescription);
        postDetailsImage = (ImageView) findViewById(R.id.postDetailsImage);
        postDetailsUsername = (TextView) findViewById(R.id.postDetailsUsername);
        tvTimeStamp = (TextView) findViewById(R.id.tvTimeStamp);

        populatePost();
    }

    public void populatePost(){
        final Post.Query query = new Post.Query();
        query.getInBackground(getIntent().getStringExtra("post"), new GetCallback<Post>() {
            @Override
            public void done(Post object, ParseException e) {
                if (e == null) {
                    postDetailsDescription.setText(object.getDescription());

                    //set the image from data using Glide
                    Glide.with(getApplicationContext())
                            .load(object.getImage().getUrl())
                            .into(postDetailsImage);

                    tvTimeStamp.setText(TimeFormatter.getTimeDifference
                            (object.getCreatedAt().toString()));

                    try {
                        postDetailsUsername.setText(object.getUser().fetchIfNeeded().getUsername());
                    } catch (Exception error) {
                    error.printStackTrace();
                    }
                } else {
                    Log.e("PostDetailsActivity", "Failed to get posts");
                }
            }
        });

    }

}
