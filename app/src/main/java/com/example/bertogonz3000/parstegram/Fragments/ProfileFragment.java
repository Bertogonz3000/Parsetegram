package com.example.bertogonz3000.parstegram.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.bertogonz3000.parstegram.LoginActivity;
import com.example.bertogonz3000.parstegram.R;
import com.parse.ParseUser;


public class ProfileFragment extends Fragment {

    Button logoutButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_profile, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        logoutButton = (Button) view.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                Intent i = new Intent(view.getContext(), LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
