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
import android.util.Log;
import android.view.View;

import com.android.volley.Request;

import org.json.JSONArray;

import java.util.ArrayList;

import se.chalmers.agile.pairprogrammingapp.PairProgrammingApplication;
import se.chalmers.agile.pairprogrammingapp.R;
import se.chalmers.agile.pairprogrammingapp.model.TestCase;
import se.chalmers.agile.pairprogrammingapp.modelview.TestCasesListAdapter;
import se.chalmers.agile.pairprogrammingapp.network.RequestHandler;
import se.chalmers.agile.pairprogrammingapp.network.TrelloUrls;
import se.chalmers.agile.pairprogrammingapp.utils.ExtraKeys;

// This class is used for achieving functionality in the Test Case view.
public class TestCasesActivity extends AppCompatActivity implements TestCasesListAdapter.OnTestCaseItemClickedListener {
    private final static String TAG = "se.chalmers.agile.pairprogrammingapp.activities.TestCasesActivity";

    // Variable that holds all the test cases that are displayed
    public static ArrayList<TestCase> mTestCases;
    // Variable the hold the view adapter.
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

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String unitId = extras.getString(ExtraKeys.UNIT_ID);

            RequestHandler.loadJsonArrayGet(TrelloUrls.getTestCasesUrl(unitId, ((PairProgrammingApplication) TestCasesActivity.this.getApplication()).getToken()),
                    new RequestHandler.OnJsonArrayLoadedListener() {
                        @Override
                        public void onJsonDataLoadedSuccessfully(JSONArray data) {
                            mTestCases = new ArrayList<>();

                            int iColor = 0;
                            for (int i = 0; i < data.length(); i++) {
                                try {
                                    String color = "";
                                    try {
                                        color = new JSONArray(data.getJSONObject(i).getString("labels")).getJSONObject(0).getString("color");
                                    } catch (Exception e) {
                                        color = "";
                                    }
                                    if (color.contains("green")) {
                                        iColor = 1;
                                    } else if (color.contains("yellow")) {
                                        iColor = 2;
                                    } else if (color.contains("red")) {
                                        iColor = 3;
                                    }
                                    color = "";
                                    mTestCases.add(new TestCase(data.getJSONObject(i).getString("name"), "", iColor, i, data.getJSONObject(i).getString("idList")));
                                } catch (Exception e) {
                                }
                            }
                            populateRecyclerView();
                        }

                        @Override
                        public void onJsonDataLoadingFailure(int errorId) {
                        }
                    }, Request.Priority.HIGH, TAG);
        }
    }

    // At this point not used, will be used later. The main purpose is to get the item that was clicked
    @Override
    public void onTestCaseItemClicked(int position) {
    }

    @Override
    public void onChangeTestCaseClicked(int position) {
    }

    // Initializes the recycler view for the test cases
    private void populateRecyclerView() {
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
