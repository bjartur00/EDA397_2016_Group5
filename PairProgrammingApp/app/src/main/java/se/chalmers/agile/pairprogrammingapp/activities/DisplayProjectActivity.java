package se.chalmers.agile.pairprogrammingapp.activities;

/**
 * Created by wanziguelva on 01-04-24.
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
import android.widget.TextView;


import com.android.volley.Request;

import org.json.JSONArray;

import java.util.ArrayList;

import se.chalmers.agile.pairprogrammingapp.PairProgrammingApplication;
import se.chalmers.agile.pairprogrammingapp.model.Unit;
import se.chalmers.agile.pairprogrammingapp.modelview.DisplayProjectAdapter;
import se.chalmers.agile.pairprogrammingapp.model.Project;
import se.chalmers.agile.pairprogrammingapp.R;
import se.chalmers.agile.pairprogrammingapp.network.JsonSerializer;
import se.chalmers.agile.pairprogrammingapp.network.RequestHandler;
import se.chalmers.agile.pairprogrammingapp.network.TrelloUrls;
import se.chalmers.agile.pairprogrammingapp.utils.ExtraKeys;

/**
 * This activity displays the list of projects assigned to the user.
 */
public class DisplayProjectActivity extends AppCompatActivity implements DisplayProjectAdapter.OnProjectItemClickedListener {
    private final static String TAG = "se.chalmers.agile.pairprogrammingapp.activities.DisplayProjectActivity";

    public final static String EXTRA_MESSAGE = "com.example.wanziguelva.myapplication.MESSAGE";

    private ArrayList<Project> mProjects;
    private RecyclerView mRecyclerView;

    private DisplayProjectAdapter mAdapter;

    //contains the layout of the views inside of the Recycler view.
    private RecyclerView.LayoutManager mLayoutManager;

    public static ArrayList<Unit> mUnits = new ArrayList<Unit>();

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

        Intent intent = getIntent();

        ((TextView) findViewById(R.id.project_title)).setText(intent.getStringExtra(ExtraKeys.USERNAME));

        //assist the variable to find its targeting layout in the layout folder.
        mRecyclerView = (RecyclerView) findViewById(R.id.project_list);

        //use this seeting to improve performance if you know that
        //changes in content do not change the layout size of the Recycler view
        mRecyclerView.setHasFixedSize(true);
        //use a lienar layout manager, the views inside the recycler view should be vertically linear
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Get the data
        RequestHandler.loadJsonArrayGet(TrelloUrls.getProjectsUrl("me", ((PairProgrammingApplication) DisplayProjectActivity.this.getApplication()).getToken()),
                new RequestHandler.OnJsonArrayLoadedListener() {
                    @Override
                    public void onJsonDataLoadedSuccessfully(JSONArray data) {
                        mProjects = JsonSerializer.json2Projects(data);
                        mAdapter = new DisplayProjectAdapter(mProjects, DisplayProjectActivity.this);
                        mRecyclerView.setAdapter(mAdapter);

                        /*((DisplayProjectAdapter) mAdapter).setOnItemClickListener(new DisplayProjectAdapter.MyClickListener() {
                            @Override
                            public void onItemClick(int position, View v) {
                                Intent intent = new Intent(DisplayProjectActivity.this, DisplayUnitsActivity.class);
                                TextView textview = (TextView) ((ViewGroup) v).getChildAt(0);

                                message = textview.getText().toString();

                                intent.putExtra("mUnits", mUnits);
                                intent.putExtra(EXTRA_MESSAGE, message);
                                startActivity(intent);

                            }
                        });*/
                    }

                    @Override
                    public void onJsonDataLoadingFailure(int errorId) {

                    }
                }, Request.Priority.HIGH, TAG);
    }

    /**
     * Creates the data to be filled in the activity.
     *
     * @return the project data to be displayed.
     */
    /*private ArrayList<Project> getDataSet() {
        ArrayList results = new ArrayList<Project>();
        for (int index = 0; index < 4; index++) {
            Project data = new Project("Project " + Integer.toString(index), index);
            results.add(index, data);
        }
        return results;
    }*/

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
                            ((PairProgrammingApplication) getApplication()).logOut(DisplayProjectActivity.this);
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
    public void onProjectItemClicked(int position) {
        Intent intent = new Intent(this, DisplayUnitsActivity.class);
        intent.putExtra(ExtraKeys.PROJECT_ID, mProjects.get(position).getProjectId());
        startActivity(intent);
    }
}
