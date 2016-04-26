package se.chalmers.agile.pairprogrammingapp.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import se.chalmers.agile.pairprogrammingapp.R;
import se.chalmers.agile.pairprogrammingapp.model.User;
import se.chalmers.agile.pairprogrammingapp.utils.ExtraKeys;
import se.chalmers.agile.pairprogrammingapp.utils.StaticTestIds;

import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    User firstUser = new User("John Kennet", "john@gmail.com", "1234");
    User secondUser = new User("Sarah Smith", "sarah@gmail.com", "abcd");;
    User thirdUser = new User("Tim Burton", "tim@gmail.com", "1234abcd");

    public final static String EXTRA_MESSAGE = "com.example.wanziguelva.myapplication.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        AuthenticateUser(email, password);
    }

    public void AuthenticateUser(String email, String password) {
        // Authenticates the user. If the email and password are correct, then
        // the user is logged in. Otherwise an error message will be displayed.
        if (checkEmailAndPassword(firstUser, email, password)) {
            logInUser(firstUser);
        } else if (checkEmailAndPassword(secondUser, email, password)){
            logInUser(secondUser);
        } else if (checkEmailAndPassword(thirdUser, email, password)) {
            logInUser(thirdUser);
        } else {
            displayErrorMessage();
        }
    }

    public boolean checkEmailAndPassword(User user, String email, String password){
        // Checks if the email and password match the user
        return (email.matches(user.getEmail()) && password.matches(user.getPassword()));
    }

    public void logInUser(User user) {
        // Logs the user in that has already been authenticated.
        Intent loginIntent = new Intent(MainActivity.this, DisplayProjectActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(ExtraKeys.USERNAME, user.getName());
        bundle.putString("email", user.getEmail());
        loginIntent.putExtras(bundle);
        MainActivity.this.startActivity(loginIntent);
    }

    public void displayErrorMessage(){
        // Username or password false, display and an error
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
        dlgAlert.setMessage("Wrong password or username");
        dlgAlert.setTitle("Error Message");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
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
