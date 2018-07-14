package com.example.bertogonz3000.parstegram.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bertogonz3000.parstegram.Model.Post;
import com.example.bertogonz3000.parstegram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class CaptureFragment extends Fragment {

    //Path you need for Uri
    private static final String AUTHORITY = "com.example.bertogonz3000.parstegram";

    //Image used for testing feed
    private static final String testImagePath = "/storage/emulated/0/DCIM/Camera/IMG_20180710_131557.jpg";

    private EditText etDescription;
    private Button createButton, photoButton;

    private ImageView photoView;

    //global var for image file to be posted
    private File file;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_capture, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

        etDescription = (EditText) view.findViewById(R.id.etDescription);
        createButton = (Button) view.findViewById(R.id.createButton);
        photoView = (ImageView) view.findViewById(R.id.photoView);
        photoButton = (Button) view.findViewById(R.id.photoButton);

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent(view);
            }
        });

        createButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final String description = etDescription.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();

                if (file != null && !description.equals("")){


                    final ParseFile parseFile = new ParseFile(file);
                parseFile.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        createPost(description, parseFile, user);
                    }
                });
            } else {
                    Toast.makeText(getContext(), "Please take a photo and enter a description"
                            , Toast.LENGTH_SHORT).show();
                }

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

        //get context()?
        File directory = this.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        try{
            file = File.createTempFile("photo", ".jpg", directory);
        } catch (IOException e){
            e.printStackTrace();
        }

        //Get uri for file
        Uri uri = FileProvider.getUriForFile(this.getContext(), AUTHORITY, file);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null){
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){

            Bitmap bitmap = rotateBitmapOrientation(file.getAbsolutePath());
            photoView.setImageBitmap(bitmap);
        }
    }

    //Code to rotate the camera
    public Bitmap rotateBitmapOrientation(String photoFilePath) {
        // Create and configure BitmapFactory
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoFilePath, bounds);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(photoFilePath, opts);
        // Read EXIF Data
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(photoFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
        int rotationAngle = 0;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;
        // Rotate Bitmap
        Matrix matrix = new Matrix();
        matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
        // Return result
        return rotatedBitmap;
    }
}
