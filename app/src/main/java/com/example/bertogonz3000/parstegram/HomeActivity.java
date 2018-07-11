package com.example.bertogonz3000.parstegram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.preference.EditTextPreference;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.bertogonz3000.parstegram.Model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.io.IOException;
import java.util.List;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity {

    //Path you need for Uri
    private static final String AUTHORITY = "com.example.bertogonz3000.parstegram";

    //Image used for testing feed
    private static final String testImagePath = "/storage/emulated/0/DCIM/Camera/IMG_20180710_131557.jpg";

    private EditText etDescription;
    private Button createButton, refreshButton, feedButton;

    private ImageView photoView;

    //global var for image file to be posted
    private File file;



    //camera vars
//    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
//    public String photoFileName = "photo.jpg";
//    File photoFile;
//    private final String APP_TAG = "Parstegram";

    static final int REQUEST_IMAGE_CAPTURE = 1;

    //TODO - allow users to log out

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        etDescription = (EditText) findViewById(R.id.etDescription);
        createButton = (Button) findViewById(R.id.createButton);
        refreshButton = (Button) findViewById(R.id.refreshButton);
        photoView = (ImageView) findViewById(R.id.photoView);
        feedButton = (Button) findViewById(R.id.feedButton);

        createButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final String description = etDescription.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();

                final ParseFile parseFile = new ParseFile(file);
                parseFile.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        createPost(description, parseFile, user);
                    }
                });

                createPost(description, parseFile, user);
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                loadTopPosts();

            }
        });



    }

    private void createPost(String description, ParseFile imageFile, ParseUser user){

        final Post newPost = new Post();
        newPost.setDescription(description);
        newPost.setImage(imageFile);
        newPost.setUser(user);

        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if (e == null){
                    Log.d("Home Activity", "Create Post Success");
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadTopPosts(){
        //init a new Post Query
        final Post.Query postQuery = new Post.Query();
        postQuery.getTop().withUser();


        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {

                if(e == null){
                    for (int i = 0; i < objects.size(); i++){
                        Log.d("HomeActivity", "Post[" + i + "] = "
                                + objects.get(i).getDescription()
                                + "/username = " + objects.get(i).getUser().getUsername());
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    public void dispatchTakePictureIntent(View view){

        File directory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        try{
            file = File.createTempFile("photo", ".jpg", directory);
        } catch (IOException e){
            e.printStackTrace();
        }

        //Get uri for file
        Uri uri =
                FileProvider
                        .getUriForFile(this,
                AUTHORITY,
                file);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null){
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            photoView.setImageBitmap(bitmap);
        }
    }

    //onClick for feedButton, moves to FeedActivity
    public void viewFeed(View view){
        Intent i = new Intent(this, FeedActivity.class);
        startActivity(i);
    }

    //Launch the camera

//    public void onLaunchCamera(View view){
//        //create Intent to take a picture and return control to the calling application
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        //Create a file reference to access to future access
//        photoFile = getPhotoFileUri(photoFileName);
//
//        //Wrap file object into a content provider
//
//    }
//
//    // Returns the File for a photo stored on disk given the fileName
//    public File getPhotoFileUri(String fileName) {
//        // Get safe storage directory for photos
//        // Use `getExternalFilesDir` on Context to access package-specific directories.
//        // This way, we don't need to request external read/write runtime permissions.
//        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);
//
//        // Create the storage directory if it does not exist
//        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
//            Log.d(APP_TAG, "failed to create directory");
//        }
//
//        // Return the file target for the photo based on filename
//        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
//
//        return file;
//    }


}
