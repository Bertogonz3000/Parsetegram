package com.example.bertogonz3000.parstegram;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bertogonz3000.parstegram.Model.Post;
import com.example.bertogonz3000.parstegram.Model.TimeFormatter;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;


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

        //set the description from data
        holder.postDescription.setText(post.getDescription());

        //set the username from data
        try {
            holder.postUsername.setText(post.getUser().fetchIfNeeded().getUsername());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Set timestamp
        holder.tvPostTimeStamp.setText(TimeFormatter.getTimeDifference(post.getCreatedAt().toString()));

        //set the image from data using Glide
        Glide.with(context)
                .load(post.getImage().getUrl())
                .into(holder.postImage);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView postImage;
        private TextView postUsername, postDescription, tvPostTimeStamp;

        public ViewHolder(View itemView) {
            super(itemView);

            postImage = (ImageView) itemView.findViewById(R.id.postImage);
            postUsername = (TextView) itemView.findViewById(R.id.postUsername);
            postDescription = (TextView) itemView.findViewById(R.id.postDescription);
            tvPostTimeStamp = (TextView) itemView.findViewById(R.id.tvPostTimeStamp);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION){

                Post post = posts.get(position);

                Intent intent = new Intent(context, PostDetailsActivity.class);

                intent.putExtra("post", post.getObjectId());

                context.startActivity(intent);

            }
        }

    }


    //clean all items of the recycler
    public void clear(){
        posts.clear();
        notifyDataSetChanged();
    }

    //Add all items of one list to the recycler
    public void addAll(List<Post> list){
        posts.addAll(list);
        notifyDataSetChanged();
    }
}
