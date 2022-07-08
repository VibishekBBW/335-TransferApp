package com.example.transferplusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
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

    private Double weight;
    private Double height;

    private BMIService bmiService;
    private boolean serviceBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent bindBmiService = new Intent(this, BMIService.class);
        bindService(bindBmiService, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
        serviceBound = false;
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            BMIService.BMIBinder binder = (BMIService.BMIBinder) iBinder;
            bmiService = binder.getService();
            serviceBound = true;
            calculateBMI();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            serviceBound = false;
        }
    };

    private void getDataFromCamera(){
        Intent caller = getIntent();
        String prename = caller.getStringExtra(vornameIntent1);
        String surname = caller.getStringExtra(nachnameIntent1);
        weight = caller.getDoubleExtra(gewichtIntent1, 80);
        height = caller.getDoubleExtra(groesseIntent1, 1.80);
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

        /*
        if(serviceBound == true){
            calculateBMI();
        }
         */

    }

    private void calculateBMI(){
        if(weight != null && height != null){
            if(serviceBound){
                //double bmiValue = bmiService.getBMI(weight, height);
                //Log.d("tag", "BMIValue: " + bmiValue + " groesse: " + height + " gewicht: " + weight);
                //bmiF.setText(String.valueOf(bmiValue));
            }
        }
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