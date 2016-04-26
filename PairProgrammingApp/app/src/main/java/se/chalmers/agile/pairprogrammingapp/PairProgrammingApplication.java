package se.chalmers.agile.pairprogrammingapp;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by wissam on 25/04/16.
 */
public class PairProgrammingApplication extends Application {
    private static PairProgrammingApplication sInstance;

    public static synchronized PairProgrammingApplication getInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
