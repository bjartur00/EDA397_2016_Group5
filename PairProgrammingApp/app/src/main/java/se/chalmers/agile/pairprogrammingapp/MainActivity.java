package se.chalmers.agile.pairprogrammingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.support.design.widget.Snackbar;

import se.chalmers.agile.pairprogrammingapp.activities.ViewNotesActivity;
import se.chalmers.agile.pairprogrammingapp.utils.ExtraKeys;
import se.chalmers.agile.pairprogrammingapp.utils.StaticTestIds;

import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    User firstUser;
    User secondUser;
    User thirdUser;

    public final static String EXTRA_MESSAGE = "com.example.wanziguelva.myapplication.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstUser = new User("John Kennet", "john@gmail.com", "1234");
        secondUser = new User("Sarah Smith", "sarah@gmail.com", "abcd");
        thirdUser = new User("Tim Burton", "tim@gmail.com", "1234abcd");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void openNotes(View view) {
        Intent intent = new Intent(MainActivity.this, ViewNotesActivity.class);
        intent.putExtra(ExtraKeys.APPLICATION_ID, StaticTestIds.APP_ID_3);
        startActivity(intent);
    }

    public void openTestCases(View view) {
        //((Button) view).setText("clicked");
        Intent intent = new Intent(this, TestCasesActivity.class);
        startActivity(intent);
    }

    public void openLoginScreen(View view) {
        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (email.matches(firstUser.getEmail()) && password.matches(firstUser.getPassword())) {
            Intent loginIntent = new Intent(MainActivity.this, DisplayProjectActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(ExtraKeys.USERNAME, firstUser.getName());
            bundle.putString("email", firstUser.getEmail());
            loginIntent.putExtras(bundle);
            MainActivity.this.startActivity(loginIntent);
        } else if (email.matches(secondUser.getEmail()) && password.matches(secondUser.getPassword())) {
            Intent loginIntent = new Intent(MainActivity.this, DisplayProjectActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(ExtraKeys.USERNAME, secondUser.getName());
            bundle.putString("email", secondUser.getEmail());
            loginIntent.putExtras(bundle);
            MainActivity.this.startActivity(loginIntent);
        } else if (email.matches(thirdUser.getEmail()) && password.matches(thirdUser.getPassword())) {
            Intent loginIntent = new Intent(MainActivity.this, DisplayProjectActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(ExtraKeys.USERNAME, thirdUser.getName());
            bundle.putString("email", thirdUser.getEmail());
            loginIntent.putExtras(bundle);
            MainActivity.this.startActivity(loginIntent);
        } else {
            Snackbar.make(view, "Wrong email and/or password", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
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
}
