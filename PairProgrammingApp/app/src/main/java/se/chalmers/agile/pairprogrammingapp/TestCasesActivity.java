package se.chalmers.agile.pairprogrammingapp;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class TestCasesActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private List<TestCaseObject> testCases = new ArrayList<TestCaseObject>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_cases);

        setTitle("Test cases - Module name");
        populateTestCases();
        populateListView();
        registerClickCallback();

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
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void populateTestCases() {
        testCases.add(new TestCaseObject("Display test cases", "For each module the test cases should be displayed in a list.", 1, 0));
        testCases.add(new TestCaseObject("Display test cases status", "The status of each test case shall be displayed with a appropriate color.", 2, 0));
        testCases.add(new TestCaseObject("Change status of a test case", "The user is able to change the status of each test case.", 3, 0));
        testCases.add(new TestCaseObject("Test case 4", "Some basic description", 2, 0));
        testCases.add(new TestCaseObject("Test case 5", "Some basic description", 3, 0));
        testCases.add(new TestCaseObject("Test case 6", "Some basic description", 1, 0));
    }

    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.listViewTestCases);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TestCaseObject clickedTestCase = testCases.get(position);
                switch (clickedTestCase.getStatus()) {
                    case 1:
                        viewClicked.setBackgroundColor(Color.YELLOW);
                        clickedTestCase.setStatus(clickedTestCase.getStatus() + 1);
                        break;
                    case 2:
                        viewClicked.setBackgroundColor(Color.RED);
                        clickedTestCase.setStatus(clickedTestCase.getStatus() + 1);
                        break;
                    case 3:
                        viewClicked.setBackgroundColor(Color.GREEN);
                        clickedTestCase.setStatus(clickedTestCase.getStatus() - 2);
                        break;
                    default:
                        viewClicked.setBackgroundColor(Color.WHITE);
                        break;
                }
            }
        });
    }

    private void populateListView(){
        ArrayAdapter<TestCaseObject> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.listViewTestCases);
        list.setAdapter(adapter);
    }


    private class MyListAdapter extends ArrayAdapter<TestCaseObject>{
        public MyListAdapter() {
            super(TestCasesActivity.this, R.layout.testcase, testCases);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure that we have a view to work with (may have been given null)
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.testcase, parent, false);
            }

            TestCaseObject currentTestCase = testCases.get(position);

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

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "TestCases Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://se.chalmers.agile.pairprogrammingapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "TestCases Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://se.chalmers.agile.pairprogrammingapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
