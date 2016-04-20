package se.chalmers.agile.pairprogrammingapp.activities;

/*
 * Copyright (C), Owner, Omar Thor Omarsson and co.
*/

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import se.chalmers.agile.pairprogrammingapp.NewTestCaseActivity;
import se.chalmers.agile.pairprogrammingapp.R;
import se.chalmers.agile.pairprogrammingapp.model.TestCase;
import se.chalmers.agile.pairprogrammingapp.modelview.TestCasesListAdapter;

// This class is used for achieving functionality in the Test Case view.
public class TestCasesActivity extends AppCompatActivity implements TestCasesListAdapter.OnTestCaseItemClickedListener {

    private final static String KEY_APP_ID = "KEY_APP_ID";
    public static ArrayList<TestCase> mTestCases = new ArrayList<>();
    private TestCasesListAdapter mAdapter;

    //Used to create the main view for the list of test cases
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_cases);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestCasesActivity.this, NewTestCaseActivity.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (mTestCases.size() <= 0) {
            populateTestCases();
        }
        populateRecyclerView();
    }

    // Creates dummy test cases (Hard coded). This will be deleted when Trello has been implemented.
    private void populateTestCases() {
        mTestCases.add(new TestCase("Display test cases", "For each module the test cases should be displayed in a list.", 1, 0));
        mTestCases.add(new TestCase("Display test cases status", "The status of each test case shall be displayed with a appropriate color.", 2, 0));
        mTestCases.add(new TestCase("Change status of a test case", "The user is able to change the status of each test case.", 3, 0));
        mTestCases.add(new TestCase("Test case 4", "Some basic description", 2, 0));
        mTestCases.add(new TestCase("Test case 5", "Some basic description", 3, 0));
        mTestCases.add(new TestCase("Test case 6", "Some basic description", 1, 0));
    }

    // At this point not used, will be used later. The main purpose is to get the item that was clicked
    @Override
    public void onTestCaseItemClicked(int position) {
    }

    // At this point not used, will be used later. The main purpose is the get the item that the user wants to delete.
    @Override
    public void onDeleteTestCaseClicked(final int position) {
    }

    // Initializes the recycler view for the test cases
    private void populateRecyclerView(){
        RecyclerView rvList = (RecyclerView) findViewById(R.id.listViewTestCases);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TestCasesListAdapter(mTestCases, this);
        rvList.setAdapter(mAdapter);
    }
}
