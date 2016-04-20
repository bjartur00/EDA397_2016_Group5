package se.chalmers.agile.pairprogrammingapp.activities;

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

public class TestCasesActivity extends AppCompatActivity implements TestCasesListAdapter.OnTestCaseItemClickedListener {
    private final static String KEY_APP_ID = "KEY_APP_ID";

    private String mAppId = null;
    public static ArrayList<TestCase> mTestCases = new ArrayList<>();
    private TestCasesListAdapter mAdapter;

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
        populatRecyclerView();
    }

    private void populateTestCases() {
        mTestCases.add(new TestCase("Display test cases", "For each module the test cases should be displayed in a list.", 1, 0));
        mTestCases.add(new TestCase("Display test cases status", "The status of each test case shall be displayed with a appropriate color.", 2, 0));
        mTestCases.add(new TestCase("Change status of a test case", "The user is able to change the status of each test case.", 3, 0));
        mTestCases.add(new TestCase("Test case 4", "Some basic description", 2, 0));
        mTestCases.add(new TestCase("Test case 5", "Some basic description", 3, 0));
        mTestCases.add(new TestCase("Test case 6", "Some basic description", 1, 0));
    }

    @Override
    public void onTestCaseItemClicked(int position) {
    }

    @Override
    public void onDeleteTestCaseClicked(final int position) {
    }

    private void populatRecyclerView(){
        RecyclerView rvList = (RecyclerView) findViewById(R.id.listViewTestCases);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TestCasesListAdapter(mTestCases, this);
        rvList.setAdapter(mAdapter);
    }

    private class MyListAdapter extends ArrayAdapter<TestCase>{
        public MyListAdapter() {
            super(TestCasesActivity.this, R.layout.testcase, mTestCases);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure that we have a view to work with (may have been given null)
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.testcase, parent, false);
            }

            TestCase currentTestCase = mTestCases.get(position);

            // Title
            TextView titleText = (TextView) itemView.findViewById(R.id.item_TestCaseTitle);
            titleText.setText(currentTestCase.getTitle());

            //Description
            TextView descriptionText = (TextView) itemView.findViewById(R.id.item_TestCaseDescription);
            descriptionText.setText(currentTestCase.getDescription());

            switch (currentTestCase.getStatus()) {
                case 1:  itemView.setBackgroundColor(Color.GREEN);
                    break;
                case 2:  itemView.setBackgroundColor(Color.YELLOW);
                    break;
                case 3:  itemView.setBackgroundColor(Color.RED);
                    break;
                default: itemView.setBackgroundColor(Color.WHITE);
                    break;
            }
            return itemView;
        }
    }
}
