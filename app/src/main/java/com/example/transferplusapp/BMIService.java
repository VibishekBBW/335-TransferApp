package com.example.transferplusapp;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class BMIService extends Service {

    private final IBinder binder = new BMIBinder();


    public class BMIBinder extends Binder{
        public BMIService getService(){
            return  BMIService.this;
        }
    }
    public BMIService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }

    public double getBMI(double weight, double height) {
        return (weight / (height * height)) * 100;
    }
}