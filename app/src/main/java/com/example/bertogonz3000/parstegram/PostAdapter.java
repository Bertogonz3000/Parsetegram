package com.example.bertogonz3000.parstegram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bertogonz3000.parstegram.Model.Post;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    private Context context;

    private List<Post> posts;

    //constructor
    public PostAdapter(List<Post> posts) {
        this.posts = posts;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //get context for app
        context = parent.getContext();

        //inflater for views
        LayoutInflater inflater = LayoutInflater.from(context);

        View PostView = inflater.inflate(R.layout.post, parent, false);

        ViewHolder viewHolder = new ViewHolder(PostView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //The post to be referenced by this method
        Post post = posts.get(position);



    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView postImage;
        private TextView postUsername, postDescription;

        public ViewHolder(View itemView) {
            super(itemView);

            postImage = (ImageView) itemView.findViewById(R.id.postImage);
            postUsername = (TextView) itemView.findViewById(R.id.postUsername);
            postDescription = (TextView) itemView.findViewById(R.id.postDescription);

        }
    }
}
