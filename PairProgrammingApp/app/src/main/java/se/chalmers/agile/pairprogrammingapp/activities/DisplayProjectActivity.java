package se.chalmers.agile.pairprogrammingapp.activities;

/**
 * Created by wanziguelva on 01-04-24.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.android.volley.Request;

import org.json.JSONObject;

import java.util.ArrayList;

import se.chalmers.agile.pairprogrammingapp.PairProgrammingApplication;
import se.chalmers.agile.pairprogrammingapp.model.GetProjectsResponse;
import se.chalmers.agile.pairprogrammingapp.modelview.ProjectListAdapter;
import se.chalmers.agile.pairprogrammingapp.model.Project;
import se.chalmers.agile.pairprogrammingapp.R;
import se.chalmers.agile.pairprogrammingapp.network.JsonSerializer;
import se.chalmers.agile.pairprogrammingapp.network.RequestHandler;
import se.chalmers.agile.pairprogrammingapp.network.TrelloUrls;
import se.chalmers.agile.pairprogrammingapp.utils.Constants;
import se.chalmers.agile.pairprogrammingapp.utils.ExtraKeys;

/**
 * This activity displays the list of projects assigned to the user.
 */
public class DisplayProjectActivity extends AppCompatActivity implements ProjectListAdapter.OnProjectItemClickedListener {
    private final static String TAG = "se.chalmers.agile.pairprogrammingapp.activities.DisplayProjectActivity";

    private ArrayList<Project> mProjects;

    private ProjectListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_project);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //assist the variable to find its targeting layout in the layout folder.
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.project_list);
        recyclerView.setHasFixedSize(true);

        //use a lienar layout manager, the views inside the recycler view should be vertically linear
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Get the data
        RequestHandler.loadJsonDataGet(TrelloUrls.getProjectsUrl("me", ((PairProgrammingApplication) DisplayProjectActivity.this.getApplication()).getToken()),
                new RequestHandler.OnJsonDataLoadedListener() {
                    @Override
                    public void onJsonDataLoadedSuccessfully(JSONObject data) {
                        GetProjectsResponse response = JsonSerializer.json2Projects(data);

                        // Store user's username and id
                        final SharedPreferences.Editor editor = getSharedPreferences(Constants.PREFS_NAME, 0).edit();
                        editor.putString(Constants.PREF_ID, response.getId());
                        editor.putString(Constants.PREF_USERNAME, response.getUsername());
                        editor.commit();

                        mProjects = response.getProjects();
                        mAdapter = new ProjectListAdapter(mProjects, DisplayProjectActivity.this);
                        recyclerView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onJsonDataLoadingFailure(int errorId) {

                    }
                }, Request.Priority.HIGH, TAG);
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
        intent.putExtra(ExtraKeys.PROJECT_NAME, mProjects.get(position).getProjectName());
        startActivity(intent);
    }
}
