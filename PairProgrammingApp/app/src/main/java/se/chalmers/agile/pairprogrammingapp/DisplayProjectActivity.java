package se.chalmers.agile.pairprogrammingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import se.chalmers.agile.pairprogrammingapp.utils.ExtraKeys;

public class DisplayProjectActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.wanziguelva.myapplication.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_project);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        ((TextView)findViewById(R.id.project_heading)).setText(intent.getStringExtra(ExtraKeys.USERNAME));
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
//        TextView textView = new TextView(this);
//        textView.setTextSize(20);
//        if (message == "Project 1") {

//        }
//        textView.setText(message);

//        RelativeLayout layout = (RelativeLayout) findViewById(R.id.project_content);
        //accroding to the message from the MainActivity, the corresponding projects need to be displayed for him/her. Projects maybe added or removed if hardcoded.
        //If a database is connected, they need to be displayed according to the database record!
          findViewById(R.id.project_content);
//        layout.addView(textView);
    }

    public void showProject1(View view) {
        Intent intent = new Intent(this, DisplayUnitsActivity.class);
        //finds the textview through the user interface
        TextView textView = (TextView) findViewById(R.id.project_name1);
        String message = textView.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void showProject2(View view) {
        Intent intent = new Intent(this, DisplayUnitsActivity.class);
        //finds the textview through the user interface
        TextView textView = (TextView) findViewById(R.id.project_name2);
        String message = textView.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void showProject3(View view) {
        Intent intent = new Intent(this, DisplayUnitsActivity.class);
        //finds the textview through the user interface
        TextView textView = (TextView) findViewById(R.id.project_name3);
        String message = textView.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void showProject4(View view) {
        Intent intent = new Intent(this, DisplayUnitsActivity.class);
        //finds the textview through the user interface
        TextView textView = (TextView) findViewById(R.id.project_name4);
        String message = textView.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void showProject5(View view) {
        Intent intent = new Intent(this, DisplayUnitsActivity.class);
        //finds the textview through the user interface
        TextView textView = (TextView) findViewById(R.id.project_name5);
        String message = textView.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

}
