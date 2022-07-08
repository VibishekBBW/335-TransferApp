package com.example.transferplusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    public static final String vornameIntent1 = "vorname";
    public static final String nachnameIntent1 = "nachname";
    public static final String geburtsdatumIntent1 = "geburtsdatum";
    public static final String groesseIntent1 = "groesse";
    public static final String gewichtIntent1 = "gewicht";
    public static final String positionIntent1 = "position";
    public static final String urlIntent1 = "url";

    private ImageView selectedImage;
    private TextView vorname;
    private TextView nachname;
    private TextView geburtsdatum;
    private TextView gewicht;
    private TextView groesse;
    private TextView positionF;
    private TextView bmiF;

    private BMIService bmiService;
    private boolean serviceBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setUI();
    }



    private void getDataFromCamera(){
        Intent caller = getIntent();
        String prename = caller.getStringExtra(vornameIntent1);
        String surname = caller.getStringExtra(nachnameIntent1);
        Double weight = caller.getDoubleExtra(gewichtIntent1, 80);
        Double height = caller.getDoubleExtra(groesseIntent1, 1.80);
        String date = caller.getStringExtra(geburtsdatumIntent1);
        String position = caller.getStringExtra(positionIntent1);
        String url = caller.getStringExtra(urlIntent1);
        //Log.d("tag", "ResultData: " + prename + "," + surname + "," + weight + "," + height + "," + date + "," + position + "," + url);
        Uri rurl = Uri.parse(url);
        Log.d("tag", "ImageView: " + url);
        Log.d("tag", "ImageView to Uri Parse: " + rurl);
        setImage(rurl);
        //selectedImage.setImageURI(Uri.parse(url));
        vorname.setText(prename);
        nachname.setText(surname);
        geburtsdatum.setText(date);
        gewicht.setText(weight.toString());
        groesse.setText(height.toString());
        positionF.setText(position);

    }

    private void setImage(Uri rurl){
        selectedImage.setImageURI(rurl);
    }

    private void setUI(){
        selectedImage = (ImageView) findViewById(R.id.imageView2);
        vorname = findViewById(R.id.vornameData);
        nachname = findViewById(R.id.nachnameData);
        geburtsdatum = findViewById(R.id.datumData);
        gewicht = findViewById(R.id.gewichtData);
        groesse = findViewById(R.id.groesseData);
        positionF = findViewById(R.id.positionData);
        bmiF = findViewById(R.id.bmiData);
        getDataFromCamera();
    }


}