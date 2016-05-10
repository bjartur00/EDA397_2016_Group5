package se.chalmers.agile.pairprogrammingapp.activities;

/**
 * Created by wanziguelva on 16-04-24.
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

import com.android.volley.Request;

import org.json.JSONArray;

import java.util.ArrayList;

import se.chalmers.agile.pairprogrammingapp.PairProgrammingApplication;
import se.chalmers.agile.pairprogrammingapp.R;
import se.chalmers.agile.pairprogrammingapp.model.TestCase;
import se.chalmers.agile.pairprogrammingapp.model.Unit;
import se.chalmers.agile.pairprogrammingapp.modelview.DisplayUnitAdapter;
import se.chalmers.agile.pairprogrammingapp.network.JsonSerializer;
import se.chalmers.agile.pairprogrammingapp.network.RequestHandler;
import se.chalmers.agile.pairprogrammingapp.network.TrelloUrls;
import se.chalmers.agile.pairprogrammingapp.utils.ExtraKeys;

/**
 * This activity displays the list of units selected by the user.
 */
public class DisplayUnitsActivity extends AppCompatActivity implements DisplayUnitAdapter.OnUnitItemClickedListener {
    private final static String TAG = "se.chalmers.agile.pairprogrammingapp.activities.DisplayUnitsActivity";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    //contains the layout of the views inside of the Recycler view.
    private RecyclerView.LayoutManager mLayoutManager;

    public static ArrayList<Unit> mUnits;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_units);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.unit_list);

        //use this seeting to improve performance if you know that
        //changes in content do not change the layout size of the Recycler view
        mRecyclerView.setHasFixedSize(true);

        //use a lienar layout manager, the views inside the recycler view should be vertically linear
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String projectId = extras.getString(ExtraKeys.PROJECT_ID);

            RequestHandler.loadJsonArrayGet(TrelloUrls.getUnitsUrl(projectId, ((PairProgrammingApplication) DisplayUnitsActivity.this.getApplication()).getToken()),
                    new RequestHandler.OnJsonArrayLoadedListener() {
                        @Override
                        public void onJsonDataLoadedSuccessfully(JSONArray data) {
                            mUnits = JsonSerializer.json2Units(data);

                            mAdapter = new DisplayUnitAdapter(mUnits, DisplayUnitsActivity.this);
                            mRecyclerView.setAdapter(mAdapter);
                        }

                        @Override
                        public void onJsonDataLoadingFailure(int errorId) {
                            Log.d("wissam", errorId + "");
                        }
                    }, Request.Priority.HIGH, TAG);
        }
    }

    @Override
    public void onUnitItemClick(int position) {
        Intent intent = new Intent(this, WorkSessionActivity.class);
        intent.putExtra(ExtraKeys.UNIT_ID, mUnits.get(position).getID());
        startActivity(intent);
    }
}
