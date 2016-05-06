package se.chalmers.agile.pairprogrammingapp;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.UUID;

import se.chalmers.agile.pairprogrammingapp.activities.DisplayProjectActivity;
import se.chalmers.agile.pairprogrammingapp.activities.MainActivity;
import se.chalmers.agile.pairprogrammingapp.utils.Constants;

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


    /**
     * Gets the token for use in all requests
     *
     * @return token as {@link String} if it exists in the shared preferences or null if not
     */
    public String getToken() {
        SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);
        return settings.getString(Constants.PREF_ACCESS_TOKEN, null);
    }


    /**
     * Shows the logout dialog within the context of the passed in activity
     *
     * @param activity the activity acting as context for the dialog
     */
    public void showLogoutDialog(final AppCompatActivity activity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle("Log out");

        // set dialog message
        alertDialogBuilder
                .setMessage("Are you sure you want to log out?")
                .setCancelable(false)
                .setPositiveButton("Log out", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        logOut(activity);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Close dialog
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    /**
     * Logs the user out of their account by deleting all prefs, starts the main activity in a new task and finishes the calling activity
     *
     * @param activity the activity calling the logout function and that needs to be finished
     */
    public void logOut(AppCompatActivity activity) {
        // Clear settings
        getSharedPreferences(Constants.PREFS_NAME, 0).edit().clear().commit();

        // Start the main activity in a new task
        Intent intent = new Intent(activity.getApplicationContext(),
                MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        // Finish the current activity
        activity.finish();
    }
}
