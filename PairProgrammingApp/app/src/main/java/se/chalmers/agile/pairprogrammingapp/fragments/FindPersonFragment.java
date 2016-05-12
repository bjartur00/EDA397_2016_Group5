package se.chalmers.agile.pairprogrammingapp.fragments;

/*
 * Copyright (C), Owner, Omar Thor Omarsson and co.
*/

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import se.chalmers.agile.pairprogrammingapp.PairProgrammingApplication;
import se.chalmers.agile.pairprogrammingapp.R;
import se.chalmers.agile.pairprogrammingapp.activities.MainActivity;
import se.chalmers.agile.pairprogrammingapp.modelview.DisplayUnitAdapter;
import se.chalmers.agile.pairprogrammingapp.network.JsonSerializer;
import se.chalmers.agile.pairprogrammingapp.network.RequestHandler;
import se.chalmers.agile.pairprogrammingapp.network.TrelloUrls;
import se.chalmers.agile.pairprogrammingapp.model.User;
import se.chalmers.agile.pairprogrammingapp.utils.ExtraKeys;
import se.chalmers.agile.pairprogrammingapp.utils.SecretKeys;

public class FindPersonFragment extends Fragment {
    private final static String TAG = "se.chalmers.agile.pairprogrammingapp.fragments.FindPersonFragment";
    private ArrayList<User> mMembers;
    private Button findPersonButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        String projectID = "EwjJOxfr";
        String userToken = SecretKeys.API_AUTH_KEY;
        /*
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String projectId = extras.getString(ExtraKeys.PROJECT_ID);
            String userToken = ((PairProgrammingApplication) FindPersonFragment.this.getApplication()).getToken()
        }
        */
        RequestHandler.loadJsonArrayGet(TrelloUrls.getMembersUrl(projectID, userToken),
                new RequestHandler.OnJsonArrayLoadedListener() {
                    @Override
                    public void onJsonDataLoadedSuccessfully(JSONArray data) {
                        mMembers = JsonSerializer.json2Members(data);
                    }

                    @Override
                    public void onJsonDataLoadingFailure(int errorId) {
                        Log.d("wissam", errorId + "");
                    }
                }, Request.Priority.HIGH, TAG);


        View view = inflater.inflate(R.layout.fragment_find_person, container, false);
        findPersonButton = (Button) view.findViewById(R.id.item_findPerson);
        findPersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i("respond", mMembers.toString());

                TextView displayPerson = (TextView) getView().findViewById(R.id.item_thePerson);
                Collections.shuffle(mMembers);
                displayPerson.setText(mMembers.get(0).getFullName());
                /*
                if (randomGenerator() <= 0.33) {
                    displayPerson.setText(mMembers.get(0).getFullName());
                } else if (randomGenerator() > 0.33 && randomGenerator() <= 0.66) {
                    displayPerson.setText(mMembers.get(1).getFullName());
                } else {
                    displayPerson.setText(mMembers.get(2).getFullName());
                }
                */

            }
        });
        return view;


    }

    private Random generator = new Random();
    double randomGenerator() {
        return generator.nextDouble();
    }

}
