package com.example.transferplusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {
    private EditText vorname;
    private EditText nachname;
    private EditText geburtsdatum;
    private EditText groesse;
    private EditText gewicht;
    private EditText position;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUI();

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                sendDataToCamera();
            }
        });
    }

    private void sendDataToCamera(){
        Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
        if(!TextUtils.isEmpty(vorname.getText().toString()) && !TextUtils.isEmpty(nachname.getText().toString()) &&
                !TextUtils.isEmpty(geburtsdatum.getText().toString()) && !TextUtils.isEmpty(groesse.getText().toString())
        && !TextUtils.isEmpty(gewicht.getText().toString()) && !TextUtils.isEmpty(position.getText().toString())){
            intent.putExtra(CameraActivity.vornameIntent, vorname.getText().toString());
            intent.putExtra(CameraActivity.nachnameIntent, nachname.getText().toString());
            intent.putExtra(CameraActivity.geburtsdatumIntent, geburtsdatum.getText().toString());
            intent.putExtra(CameraActivity.groesseIntent, Double.parseDouble(groesse.getText().toString()));
            intent.putExtra(CameraActivity.gewichtIntent, Double.parseDouble(gewicht.getText().toString()));
            intent.putExtra(CameraActivity.positionIntent, position.getText().toString());
            startActivity(intent);
        } else {
            Toast.makeText(this, "Please fill all the data.", Toast.LENGTH_SHORT).show();
        }


    }

    /*
    private void next(){
        Toast.makeText(this, "Next Request called", Toast.LENGTH_SHORT).show();
    }
     */

    private void setUI(){
        vorname = findViewById(R.id.vornameInput);
        nachname = findViewById(R.id.nachnameInput);
        geburtsdatum = findViewById(R.id.datumInput);
        groesse = findViewById(R.id.groesseInput);
        gewicht = findViewById(R.id.gewichtInput);
        position = findViewById(R.id.positionInput);
        next = findViewById(R.id.nextButton1);
    }
}