package com.example.bertogonz3000.parstegram.Fragments;

import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.bertogonz3000.parstegram.LoginActivity;
import com.example.bertogonz3000.parstegram.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static com.example.bertogonz3000.parstegram.Fragments.CaptureFragment.REQUEST_IMAGE_CAPTURE;


public class ProfileFragment extends Fragment {

    //Path you need for Uri
    private static final String AUTHORITY = "com.example.bertogonz3000.parstegram";

    //global var for image file to be posted
    private File file;

    TextView profileUsername;
    Button logoutButton, changePicButton, saveButton;
    ImageView ivProfile;
    final ParseUser user = ParseUser.getCurrentUser();
    final String userName = user.getUsername();
    ParseFile profilePic = user.getParseFile("profilePicture");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_profile, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        logoutButton = (Button) view.findViewById(R.id.logoutButton);
        ivProfile = (ImageView) view.findViewById(R.id.ivProfile);
        changePicButton = (Button) view.findViewById(R.id.changePicButton);
        saveButton = (Button) view.findViewById(R.id.saveButton);
        profileUsername = (TextView) view.findViewById(R.id.profileUsername);

        profileUsername.setText(userName);

        //set prof pic imageView
        if (profilePic != null) {
            Glide.with(view.getContext())
                    .load(profilePic.getUrl())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(ivProfile);
        }

        changePicButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent(view);

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Toast.makeText(getContext(), "Please wait", Toast.LENGTH_SHORT).show();


                if(file != null){
                    final ParseFile parseFile = new ParseFile(file);
                    parseFile.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            //Set the profilepic in parse
                            user.put("profilePicture", parseFile);
                            user.saveInBackground();
                            Toast.makeText(getContext(), "Save Successful", Toast.LENGTH_SHORT).show();

                            Glide.with(getContext())
                                    .load(parseFile.getUrl())
                                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                                    .into(ivProfile);

                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Failed to save new profile pic", Toast.LENGTH_SHORT).show();
                }
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                Intent i = new Intent(view.getContext(), LoginActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){

            Bitmap bitmap = rotateBitmapOrientation(file.getAbsolutePath());
            ivProfile.setImageBitmap(bitmap);

            Toast.makeText(getContext(), "Press save to upload and format", Toast.LENGTH_SHORT).show();

        }
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
