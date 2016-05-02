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

import java.util.Random;

import se.chalmers.agile.pairprogrammingapp.R;
import se.chalmers.agile.pairprogrammingapp.activities.MainActivity;

public class FindPersonFragment extends Fragment {
    private Button findPersonButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find_person, container, false);
        findPersonButton = (Button) view.findViewById(R.id.item_findPerson);
        findPersonButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                TextView displayPerson = (TextView) getView().findViewById(R.id.item_thePerson);
                if(randomGenerator() <= 0.33) {
                    displayPerson.setText(MainActivity.firstUser.getName());
                } else if(randomGenerator() > 0.33 && randomGenerator() <= 0.66){
                    displayPerson.setText(MainActivity.secondUser.getName());
                } else {
                    displayPerson.setText(MainActivity.thirdUser.getName());
                }
            }
        });
        return view;
    }

    private Random generator = new Random();
    double randomGenerator() {
        return generator.nextDouble();
    }

}
