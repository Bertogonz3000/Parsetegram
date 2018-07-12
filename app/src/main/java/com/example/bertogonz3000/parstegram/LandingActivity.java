package com.example.bertogonz3000.parstegram;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.bertogonz3000.parstegram.Fragments.CaptureFragment;
import com.example.bertogonz3000.parstegram.Fragments.FeedFragment;
import com.example.bertogonz3000.parstegram.Fragments.ProfileFragment;

public class LandingActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        //instantiate the bottomNav
        bottomNav = (BottomNavigationView) findViewById(R.id.bottomNav);

        //instantiate the fragment manager
        final FragmentManager fragager = getSupportFragmentManager();

        //TODO - Create fragment classes
        //instantiate the fragments
        final Fragment feedFrag = new FeedFragment();
        final Fragment captureFrag = new CaptureFragment();
        final Fragment profileFrag = new ProfileFragment();

        //handle navigation selection
        bottomNav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    FragmentTransaction fragTransaction;
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.feedFragment:
                                fragTransaction = fragager.beginTransaction();
                                fragTransaction.replace(R.id.frameLayout, feedFrag).commit();
                                return true;

                            case R.id.cameraFragment:
                                fragTransaction = fragager.beginTransaction();
                                fragTransaction.replace(R.id.frameLayout, captureFrag).commit();
                                return true;

                            case R.id.profileFragment:
                                fragTransaction = fragager.beginTransaction();
                                fragTransaction.replace(R.id.frameLayout, profileFrag).commit();
                                return true;
                        }
                        return true;
                    }
                });
    }


}
