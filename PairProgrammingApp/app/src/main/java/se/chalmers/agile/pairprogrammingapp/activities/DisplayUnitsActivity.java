package se.chalmers.agile.pairprogrammingapp.activities;

/**
 * Created by wanziguelva on 16-04-24.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import se.chalmers.agile.pairprogrammingapp.R;
import se.chalmers.agile.pairprogrammingapp.activities.DisplayProjectActivity;
import se.chalmers.agile.pairprogrammingapp.model.TestCase;
import se.chalmers.agile.pairprogrammingapp.model.Unit;
import se.chalmers.agile.pairprogrammingapp.modelview.DisplayUnitAdapter;
import se.chalmers.agile.pairprogrammingapp.network.TrelloUrls;
import se.chalmers.agile.pairprogrammingapp.utils.ExtraKeys;

/**
 * This activity displays the list of units selected by the user.
 */
public class DisplayUnitsActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.wanziguelva.myapplication.MESSAGE";
    public String message = null;

    private static String LOG_TAG = "MyRecyclerViewAdapter";

    private RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;

    //contains the layout of the views inside of the Recycler view.
    private RecyclerView.LayoutManager mLayoutManager;

    public static ArrayList<TestCase> mTestCases = new ArrayList<TestCase>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        populateTestCases();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_units);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        message = intent.getStringExtra(DisplayProjectActivity.EXTRA_MESSAGE);
        mRecyclerView = (RecyclerView) findViewById(R.id.unit_list);

        //use this seeting to improve performance if you know that
        //changes in content do not change the layout size of the Recycler view
        mRecyclerView.setHasFixedSize(true);
        //use a lienar layout manager, the views inside the recycler view should be vertically linear
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<Unit> u = (ArrayList<Unit>)getIntent().getSerializableExtra("mUnits");

        //specify an adapter
        mAdapter = new DisplayUnitAdapter((ArrayList<Unit>)getIntent().getSerializableExtra("mUnits"));
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * This method contains an onClickListener that invokes the TestCaseActivity.
     */
    @Override
    protected void onResume() {
        super.onResume();
        ((DisplayUnitAdapter) mAdapter).setOnItemClickListener(new DisplayUnitAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);
                Intent intent = new Intent(DisplayUnitsActivity.this, TestCasesActivity.class);
                TextView textview = (TextView) ((ViewGroup) v).findViewById(R.id.unit_id);
                message = textview.getText().toString();
                intent.putExtra("mTestCases", mTestCases);
                intent.putExtra("listID", message);
                startActivity(intent);
            }
        });
    }

    // Gets the test cases from Trello.
    private void populateTestCases() {
        mTestCases.clear();
        mTestCases = TrelloUrls.getTestCases("e1c839e03bdbaf72f5e798a2a918c2e901a6446593db8ea9679c86952c6c2084");
    }

    /**
     * Creates the data to be filled in the activity.
     * @return the unit data to be displayed.
     */

}
