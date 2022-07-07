package com.example.transferplusapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class BMIService extends Service {
    public BMIService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}