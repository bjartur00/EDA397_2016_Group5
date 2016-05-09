package se.chalmers.agile.pairprogrammingapp.activities;

/*
 * Copyright (C), Owner, Omar Thor Omarsson and co.
*/

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import se.chalmers.agile.pairprogrammingapp.R;

// Opens the activity that allows the user to use timer and find random Trello users to pair programm with.
public class PairProgrammingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pair_programming);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
