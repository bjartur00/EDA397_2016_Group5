package se.chalmers.agile.pairprogrammingapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TrelloApi;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import java.util.concurrent.ExecutionException;
import se.chalmers.agile.pairprogrammingapp.R;
import se.chalmers.agile.pairprogrammingapp.model.Project;
import se.chalmers.agile.pairprogrammingapp.model.TimeService;
import se.chalmers.agile.pairprogrammingapp.model.User;
import se.chalmers.agile.pairprogrammingapp.network.TrelloUrls;
import se.chalmers.agile.pairprogrammingapp.utils.Constants;
import se.chalmers.agile.pairprogrammingapp.utils.ExtraKeys;
import se.chalmers.agile.pairprogrammingapp.utils.SecretKeys;
import se.chalmers.agile.pairprogrammingapp.utils.StaticTestIds;


public class MainActivity extends AppCompatActivity {
    public static User firstUser = new User("John Kennet", "john@gmail.com", "1234");
    public static User secondUser = new User("Sarah Smith", "sarah@gmail.com", "abcd");
    public static User thirdUser = new User("Tim Burton", "tim@gmail.com", "1234abcd");

    public final static String EXTRA_MESSAGE = "com.example.wanziguelva.myapplication.MESSAGE";

    // Important global variables for the timer since the main activity will always run in the background.
    public static boolean timeIsRunning = false;
    public static long remainingTimeMs = 0;
    public static Intent timeServiceIntent;
    public static boolean dontDisplayTextWhenFinished = false;

    private OAuthService service;
    private static Token requestToken;

    // For the test cases
    public User[] oMembers;

    public Project[] mProjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //This is temporary, only to see if the request actually works
        oMembers = TrelloUrls.getMembers("temp", "EwjJOxfr", "e7f2387af84a2e749732e48d8290c204", "e1c839e03bdbaf72f5e798a2a918c2e901a6446593db8ea9679c86952c6c2084");
        //mProjects = TrelloUrls.getProjects("e1c839e03bdbaf72f5e798a2a918c2e901a6446593db8ea9679c86952c6c2084");
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

    public void openNotes(View view) {
        Intent intent = new Intent(MainActivity.this, ViewNotesActivity.class);
        intent.putExtra(ExtraKeys.APPLICATION_ID, StaticTestIds.APP_ID_3);
        startActivity(intent);
    }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Opens the timer activity
    public void openTimer(View view) {
        Intent intent = new Intent(this, PairProgrammingActivity.class);
        startActivity(intent);
    }
}
