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
import se.chalmers.agile.pairprogrammingapp.network.TrelloUrls;

// This class is used for achieving functionality in the Test Case view.
public class TestCasesActivity extends AppCompatActivity implements TestCasesListAdapter.OnTestCaseItemClickedListener {

    // Variable that holds all the test cases that are displayed
    public static ArrayList<TestCase> mTestCases = new ArrayList<>();
    // Variable the hold the view adapter.
    private TestCasesListAdapter mAdapter;

    //Used to create the main view for the list of test cases
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mTestCases = (ArrayList<TestCase>)getIntent().getSerializableExtra("mTestCases");
        String k = this.getIntent().getExtras().getString("listID");
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
        populateRecyclerView();
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
