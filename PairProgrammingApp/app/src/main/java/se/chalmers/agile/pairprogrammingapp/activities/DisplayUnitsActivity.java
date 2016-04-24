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
import se.chalmers.agile.pairprogrammingapp.model.Unit;
import se.chalmers.agile.pairprogrammingapp.modelview.DisplayUnitAdapter;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_units);
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

//        ((TextView)findViewById(R.id.unit_title)).setText(intent.getStringExtra(ExtraKeys.USERNAME));

        message = intent.getStringExtra(DisplayProjectActivity.EXTRA_MESSAGE);

        mRecyclerView = (RecyclerView) findViewById(R.id.unit_list);

        //use this seeting to improve performance if you know that
        //changes in content do not change the layout size of the Recycler view
        mRecyclerView.setHasFixedSize(true);
        //use a lienar layout manager, the views inside the recycler view should be vertically linear
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //specify an adapter
        mAdapter = new DisplayUnitAdapter(getDataSet());
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
                TextView textview = (TextView) ((ViewGroup) v).getChildAt(0);

                message = textview.getText().toString();

                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);

            }
        });
    }

    /**
     * Creates the data to be filled in the activity.
     * @return the unit data to be displayed.
     */
    private ArrayList<Unit> getDataSet() {
        ArrayList results = new ArrayList<Unit>();

        if (message.equals("Project 0")) {
            for (int index = 0; index < 4; index++) {
                Unit data = new Unit("Project 0, Unit " + index, "Progress " + index);
                results.add(index, data);
            }
        } else if (message.equals("Project 1")) {
            for (int index = 0; index < 4; index++) {
                Unit data = new Unit("Project 1, Unit " + index, "Progress " + index);
                results.add(index, data);
            }
        } else if (message.equals("Project 2")) {
            for (int index = 0; index < 4; index++) {
                Unit data = new Unit("Project 2, Unit " + index, "Progress " + index);
                results.add(index, data);
            }
        } else if (message.equals("Project 3")) {
            for (int index = 0; index < 4; index++) {
                Unit data = new Unit("Project 3, Unit " + index, "Progress " + index);
                results.add(index, data);
            }
        }

        return results;
    }

}
