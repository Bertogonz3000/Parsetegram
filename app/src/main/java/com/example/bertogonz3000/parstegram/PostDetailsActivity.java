package com.example.bertogonz3000.parstegram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.bertogonz3000.parstegram.Model.Post;
import com.example.bertogonz3000.parstegram.Model.TimeFormatter;
import com.parse.GetCallback;
import com.parse.ParseException;

import org.w3c.dom.Text;

public class PostDetailsActivity extends AppCompatActivity {

    private ImageView postDetailsImage, profileImageDetails;
    private TextView postDetailsUsername, postDetailsDescription, tvPostTimeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        postDetailsDescription = (TextView) findViewById(R.id.postDetailsDescription);
        postDetailsImage = (ImageView) findViewById(R.id.postDetailsImage);
        postDetailsUsername = (TextView) findViewById(R.id.postDetailsUsername);
        tvPostTimeStamp = (TextView) findViewById(R.id.tvPostTimeStamp);
        profileImageDetails = (ImageView) findViewById(R.id.profileImageDetails);

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

                    tvPostTimeStamp.setText(TimeFormatter.getTimeDifference
                            (object.getCreatedAt().toString()));


                    try {
                        if (object.getUser().fetchIfNeeded().getParseFile("profilePicture") != null) {
                            //Set the profileimage from data using Glide
                            Glide.with(getApplicationContext())
                                    .load(object.getUser().getParseFile("profilePicture").getUrl())
                                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                                    .into(profileImageDetails);
                        }
                    } catch (Exception error){
                        error.printStackTrace();
                    }

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
