package com.example.transferplusapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {

    public static final String vornameIntent = "vorname";
    public static final String nachnameIntent = "nachname";
    public static final String geburtsdatumIntent = "geburtsdatum";
    public static final String groesseIntent = "groesse";
    public static final String gewichtIntent = "gewicht";
    public static final String positionIntent = "position";
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;

    private Button camera;
    private Button gallery;
    private Button next;
    private ImageView selectedImage;
    private String currentPhotoPath;
    private Uri sendUrl;

    private int received = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        setUI();

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askCameraPermission();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataFromMain();
            }
        });
    }

    private void askCameraPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        } else {
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CAMERA_PERM_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Please give Camera Permission in order to use the camera.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*
    private void openCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST_CODE){
            /*
            Bitmap image = (Bitmap) data.getExtras().get("data");
            selectedImage.setImageBitmap(image);
             */
            if(resultCode == Activity.RESULT_OK){
                //Set Image with Uri from File f
                File f = new File(currentPhotoPath);
                selectedImage.setImageURI(Uri.fromFile(f));
                sendUrl = Uri.fromFile(f);
                Log.d("tag", "Absolute Url of image is " + Uri.fromFile(f));

                //Display Image to Gallery with MediaScan
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);
            }
        }
        if(requestCode == GALLERY_REQUEST_CODE){
            /*
            Bitmap image = (Bitmap) data.getExtras().get("data");
            selectedImage.setImageBitmap(image);
             */
            if(resultCode == Activity.RESULT_OK){
                Uri contentUri = data.getData();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "." + getFileExt(contentUri);
                Log.d("tag", "onActivityResult: Gallery Image Uri: " + contentUri);
                selectedImage.setImageURI(contentUri);
                sendUrl = contentUri;
            }
        }

    }

    //get Extension from selected Image
    private String getFileExt(Uri contentUri){
        ContentResolver c = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }

    //Function to create a unique file name without creating the picture itself for now
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    //Function to invoke the camera and take picture with its Uri
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    private void setUI(){
        camera = findViewById(R.id.cameraBtn);
        gallery = findViewById(R.id.galleryBtn);
        selectedImage = findViewById(R.id.imageView);
        next = findViewById(R.id.nextButton2);
    }

    private void getDataFromMain(){
        Intent caller = getIntent();
        String prename = caller.getStringExtra(vornameIntent);
        String surname = caller.getStringExtra(nachnameIntent);
        Double weight = caller.getDoubleExtra(gewichtIntent, 80);
        Double height = caller.getDoubleExtra(groesseIntent, 1.80);
        String date = caller.getStringExtra(geburtsdatumIntent);
        String position = caller.getStringExtra(positionIntent);
        Log.d("tag", "MainData: " + prename + "," + surname + "," + weight + "," + height + "," + date + "," + position);
        if(prename != null && surname != null && date != null && weight != null && height != null
            && position != null && sendUrl != null){
            sendDataToResult(prename, surname, weight, height, date, position, sendUrl);
        }

    }

    private void sendDataToResult(String prename, String surname, Double weight, Double height, String date, String position, Uri sendUrl) {
        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
        Log.d("tag", "url data to send: " + sendUrl);
        intent.putExtra(ResultActivity.vornameIntent1, prename);
        intent.putExtra(ResultActivity.nachnameIntent1, surname);
        intent.putExtra(ResultActivity.geburtsdatumIntent1, date);
        intent.putExtra(ResultActivity.groesseIntent1, weight);
        intent.putExtra(ResultActivity.gewichtIntent1, height);
        intent.putExtra(ResultActivity.positionIntent1, position);
        intent.putExtra(ResultActivity.urlIntent1, sendUrl.toString());
        startActivity(intent);
    }








}