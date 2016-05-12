package se.chalmers.agile.pairprogrammingapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TrelloApi;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import java.util.concurrent.ExecutionException;

import se.chalmers.agile.pairprogrammingapp.R;
import se.chalmers.agile.pairprogrammingapp.model.TimeService;
import se.chalmers.agile.pairprogrammingapp.utils.Constants;
import se.chalmers.agile.pairprogrammingapp.utils.SecretKeys;


public class MainActivity extends AppCompatActivity {
    // Important global variables for the timer since the main activity will always run in the background.
    public static boolean timeIsRunning = false;
    public static long remainingTimeMs = 0;
    public static Intent timeServiceIntent;
    public static boolean dontDisplayTextWhenFinished = false;

    // vars for oauth service
    private OAuthService service;
    private static Token requestToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        timeServiceIntent = new Intent(this, TimeService.class);

        // Create the service to authenticate with Trello API
        service = new ServiceBuilder()
                .provider(TrelloApi.class)
                .apiKey(SecretKeys.API_KEY)
                .apiSecret(SecretKeys.API_SECRET)
                .callback(Constants.CALLBACKURL)
                .build();
    }

    /**
     * Initiates the authentication with Trello process
     * @param view
     */
    public void loginViaTrello(View view) {
        String authUrl = null;
        try {
            authUrl = new MainActivity.authUrl().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl)));
    }

    /**
     * Gets the authorization URL
     */
    private class authUrl extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            requestToken = service.getRequestToken();
            return service.getAuthorizationUrl(requestToken);
        }
    }

    /**
     * Gets the access token and stores it
     */
    private class OauthEnd extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            final String verifier = params[0];
            final Verifier v = new Verifier(verifier);

            // Store access token
            final Token accessToken = service.getAccessToken(requestToken, v);

            // Store the access token
            final SharedPreferences.Editor editor = getSharedPreferences(Constants.PREFS_NAME, 0).edit();
            editor.putString(Constants.PREF_ACCESS_TOKEN, accessToken.getToken());
            editor.putString(Constants.PREF_ACCESS_SECRET, accessToken.getSecret());
            editor.commit();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Go to projects activity
            gotoProjectsActivity();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Each time this activity resumes, check if there are stored info
        SharedPreferences prefs = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);

        if (prefs.getString(Constants.PREF_ACCESS_TOKEN, null) != null && prefs.getString(Constants.PREF_ACCESS_SECRET, null) != null) {
            // Token has been found
            // Go to projects activity
            gotoProjectsActivity();
        } else {
            // Check if it's coming back from authentication process
            final Uri uri = this.getIntent().getData();
            // Check whether the uri is valid to do an OAuth Dance
            if (uri != null && uri.toString().startsWith(Constants.CALLBACKURL)) {
                // Get the OAuth verifier from the URI
                String verifier = uri.getQueryParameter("oauth_verifier");
                // If the verifier is not null
                if (verifier != null) new OauthEnd().execute(verifier);
            }
        }
    }

    /**
     * Goes to the projects activity and finishes this activity
     */
    private void gotoProjectsActivity() {
        startActivity(new Intent(this, DisplayProjectActivity.class));
        // Finish this activity
        finish();
    }
}
