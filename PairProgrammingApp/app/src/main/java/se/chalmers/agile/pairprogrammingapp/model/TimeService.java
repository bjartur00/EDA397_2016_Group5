package se.chalmers.agile.pairprogrammingapp.model;

/*
 * Copyright (C), Owner, Omar Thor Omarsson and co.
*/

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import se.chalmers.agile.pairprogrammingapp.activities.MainActivity;
import se.chalmers.agile.pairprogrammingapp.activities.PairProgrammingActivity;

/**
 * Created by Omar on 29.4.2016.
 */
public class TimeService extends Service {
    private static final String TAG = "HelloService";
    private boolean isRunning  = false;

    @Override
    public void onCreate() {
        Log.i(TAG, "Service onCreate");

        isRunning = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();
        final long startTime = extras.getLong("time");
        final long iterations = startTime / 1000;

        Log.i(TAG, "Service onStartCommand");

        //Creating new thread for my service
        //Always write your long running tasks in a separate thread, to avoid ANR
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Your logic that service will perform will be placed here
                //In this example we are just looping and waits for 1000 milliseconds in each loop.
                for (int i = 0; i < iterations; i++) {
                    try {
                        MainActivity.remainingTimeMs = (iterations - i) * 1000;
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                    Log.i(TAG, "Service running");
                    if(!MainActivity.timeIsRunning){
                        break;
                    }
                }
                MainActivity.timeIsRunning = false;
                //Stop service once it finishes its task
                stopSelf();
            }
        }).start();

        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        Log.i(TAG, "Service onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        if(MainActivity.dontDisplayTextWhenFinished) {
            Context context = getApplicationContext();
            CharSequence text = "Pair programming time is up!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}
