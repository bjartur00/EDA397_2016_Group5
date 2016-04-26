package se.chalmers.agile.pairprogrammingapp.activities;

/*
 * Copyright (C), Owner, Omar Thor Omarsson and co.
*/

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

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

    @Override
    public void onChangeTestCaseClicked(int position) {
    }

    // Initializes the recycler view for the test cases
    private void populateRecyclerView(){
        RecyclerView rvList = (RecyclerView) findViewById(R.id.listViewTestCases);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TestCasesListAdapter(mTestCases, this);
        rvList.setAdapter(mAdapter);
    }

    @Override
    public void onDeleteTestCaseClicked(final int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle("Delete note");

        // set dialog message
        alertDialogBuilder
                .setMessage("Are you sure you want to delete the test case?")
                .setCancelable(false)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Delete the note
                        mAdapter.deleteItem(position);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Close the dialog
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
