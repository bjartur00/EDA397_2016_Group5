package se.chalmers.agile.pairprogrammingapp.activities;

/*
 * Copyright (C), Owner, Omar Thor Omarsson and co.
*/

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import se.chalmers.agile.pairprogrammingapp.R;
import se.chalmers.agile.pairprogrammingapp.model.TestCase;
import se.chalmers.agile.pairprogrammingapp.network.TrelloUrls;

// Allows the user to add test cases.
public class NewTestCaseActivity extends AppCompatActivity {
    // Variable that holds all the test cases that are displayed
    public static ArrayList<TestCase> mTestCases = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_test_case);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void openTestCases(View view)
    {
        // TODO return result to calling activity
        /*mTestCases.add(
                new TestCase(
                        ((EditText) this.findViewById(R.id.item_TestCaseTitle)).getText().toString(),
                        ((EditText) this.findViewById(R.id.item_TestCaseDescription)).getText().toString(), 3, 0, ""));
        Intent intent = new Intent(NewTestCaseActivity.this, TestCasesActivity.class);
        intent.putExtra("mTestCases", mTestCases);
        startActivity(intent);*/
    }
}
