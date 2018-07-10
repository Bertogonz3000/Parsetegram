package com.example.bertogonz3000.parstegram;

import android.app.Application;

import com.example.bertogonz3000.parstegram.Model.Post;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Register the subclass - tell parse its custom to encapsulate our data
        ParseObject.registerSubclass(Post.class);

        //Setup parse server so we can make network calls!
        //all spec info for our instance of parse defined here
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("bertogonz3000-parstegram")
                .clientKey("Barrothfailz300^")
                .server("http://bertogonz3000-fbu-parstegram.herokuapp.com/parse")
                .build();
        Parse.initialize(configuration);
    }
}