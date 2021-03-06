package se.chalmers.agile.pairprogrammingapp.activities;

/**
 * Created by wanziguelva on 16-04-24.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;

import org.json.JSONArray;

import java.util.ArrayList;

import se.chalmers.agile.pairprogrammingapp.PairProgrammingApplication;
import se.chalmers.agile.pairprogrammingapp.R;
import se.chalmers.agile.pairprogrammingapp.model.Unit;
import se.chalmers.agile.pairprogrammingapp.modelview.UnitListAdapter;
import se.chalmers.agile.pairprogrammingapp.network.JsonSerializer;
import se.chalmers.agile.pairprogrammingapp.network.RequestHandler;
import se.chalmers.agile.pairprogrammingapp.network.TrelloUrls;
import se.chalmers.agile.pairprogrammingapp.utils.ExtraKeys;

/**
 * This activity displays the list of units selected by the user.
 */
public class DisplayUnitsActivity extends AppCompatActivity implements UnitListAdapter.OnUnitItemClickedListener {
    private final static String TAG = "se.chalmers.agile.pairprogrammingapp.activities.DisplayUnitsActivity";

    private ArrayList<Unit> mUnits;
    private String mProjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_units);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.unit_list);

        //use this seeting to improve performance if you know that
        //changes in content do not change the layout size of the Recycler view
        recyclerView.setHasFixedSize(true);

        //use a lienar layout manager, the views inside the recycler view should be vertically linear
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mProjectId = extras.getString(ExtraKeys.PROJECT_ID);
            String projectName = extras.getString(ExtraKeys.PROJECT_NAME);

            getSupportActionBar().setTitle(projectName);

            RequestHandler.loadJsonArrayGet(TrelloUrls.getUnitsUrl(mProjectId, ((PairProgrammingApplication) DisplayUnitsActivity.this.getApplication()).getToken()),
                    new RequestHandler.OnJsonArrayLoadedListener() {
                        @Override
                        public void onJsonDataLoadedSuccessfully(JSONArray data) {
                            mUnits = JsonSerializer.json2Units(data);

                            recyclerView.setAdapter(new UnitListAdapter(mUnits, DisplayUnitsActivity.this));
                        }

                        @Override
                        public void onJsonDataLoadingFailure(int errorId) {
                        }
                    }, Request.Priority.HIGH, TAG);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_projects, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }
        if (id == R.id.action_logout) {
            //((PairProgrammingApplication) getApplication()).showLogoutDialog(DisplayProjectActivity.this);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            // set title
            alertDialogBuilder.setTitle("Log out");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Are you sure you want to log out?")
                    .setCancelable(false)
                    .setPositiveButton("Log out", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ((PairProgrammingApplication) getApplication()).logOut(DisplayUnitsActivity.this);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Close dialog
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onUnitItemClick(int position) {
        Intent intent = new Intent(this, WorkSessionActivity.class);
        intent.putExtra(ExtraKeys.UNIT_ID, mUnits.get(position).getID());
        intent.putExtra(ExtraKeys.UNIT_NAME, mUnits.get(position).getUnitName());
        intent.putExtra(ExtraKeys.PROJECT_ID, mProjectId);
        startActivity(intent);
    }
}
