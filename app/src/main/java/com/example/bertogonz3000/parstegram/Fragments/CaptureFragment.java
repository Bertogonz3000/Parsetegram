package com.example.bertogonz3000.parstegram.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.bertogonz3000.parstegram.R;


public class CaptureFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_capture, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        ImageView checkImage = (ImageView) view.findViewById(R.id.checkImageTwo);
    }

}