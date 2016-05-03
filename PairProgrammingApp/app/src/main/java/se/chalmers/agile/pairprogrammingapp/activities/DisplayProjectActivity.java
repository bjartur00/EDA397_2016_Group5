package se.chalmers.agile.pairprogrammingapp.activities;

/**
 * Created by wanziguelva on 01-04-24.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

import se.chalmers.agile.pairprogrammingapp.modelview.DisplayProjectAdapter;
import se.chalmers.agile.pairprogrammingapp.model.Project;
import se.chalmers.agile.pairprogrammingapp.R;
import se.chalmers.agile.pairprogrammingapp.utils.ExtraKeys;

/**
 * This activity displays the list of projects assigned to the user.
 */
public class DisplayProjectActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_display_project);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO: This part may be modified to add new projects...
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

        ((TextView)findViewById(R.id.project_title)).setText(intent.getStringExtra(ExtraKeys.USERNAME));

        //assist the variable to find its targeting layout in the layout folder.
        mRecyclerView = (RecyclerView) findViewById(R.id.project_list);

        //use this seeting to improve performance if you know that
        //changes in content do not change the layout size of the Recycler view
        mRecyclerView.setHasFixedSize(true);
        //use a lienar layout manager, the views inside the recycler view should be vertically linear
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //specify an adapter
        mAdapter = new DisplayProjectAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);

    }

    /**
     * This method contains an onClickListener that invokes the DisplayUnitsActivity.
     */
    @Override
    protected void onResume() {
        super.onResume();
        ((DisplayProjectAdapter) mAdapter).setOnItemClickListener(new DisplayProjectAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);
                Intent intent = new Intent(DisplayProjectActivity.this, DisplayUnitsActivity.class);
                TextView textview = (TextView)((ViewGroup) v).getChildAt(0);

                message = textview.getText().toString();

                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);

            }
        });
    }

    /**
     * Creates the data to be filled in the activity.
     * @return the project data to be displayed.
     */
    private ArrayList<Project> getDataSet() {
        ArrayList results = new ArrayList<Project>();
        for (int index = 0; index < 4; index++) {
            Project data = new Project("Project " + Integer.toString(index), index);
            results.add(index, data);
        }
        return results;
    }

}
