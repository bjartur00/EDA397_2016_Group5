package se.chalmers.agile.pairprogrammingapp.fragments;

/*
 * Copyright (C), Owner, Omar Thor Omarsson and co.
*/

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.android.volley.Request;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import se.chalmers.agile.pairprogrammingapp.PairProgrammingApplication;
import se.chalmers.agile.pairprogrammingapp.R;
import se.chalmers.agile.pairprogrammingapp.activities.MainActivity;
import se.chalmers.agile.pairprogrammingapp.model.TimeService;
import se.chalmers.agile.pairprogrammingapp.model.User;
import se.chalmers.agile.pairprogrammingapp.network.JsonSerializer;
import se.chalmers.agile.pairprogrammingapp.network.RequestHandler;
import se.chalmers.agile.pairprogrammingapp.network.TrelloUrls;
import se.chalmers.agile.pairprogrammingapp.utils.Constants;

public class TimerFragment extends Fragment {
    private final static String TAG = "se.chalmers.agile.pairprogrammingapp.fragments.TimerFragment";
    private static final String ARG_PROJECT_ID = "projectId";

    private String mProjectId;
    private MalibuCountDownTimer countDownTimer;
    private boolean timerHasStarted = false;
    private NumberPicker item_seconds;
    private NumberPicker item_minutes;
    private NumberPicker item_hours;
    private Button startB;
    private ArrayList<User> mMembers;
    private ArrayList<User> mOtherMembers;

    private long startTime = 0;
    private final long interval = 1000;

    public static TimerFragment newInstance(String projectId) {
        TimerFragment fragment = new TimerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PROJECT_ID, projectId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProjectId = getArguments().getString(ARG_PROJECT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        // Method when the start button is pressed.
        startB = (Button) view.findViewById(R.id.item_startTime);
        startB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item_seconds = (NumberPicker) getView().findViewById(R.id.item_seconds);
                item_minutes = (NumberPicker) getView().findViewById(R.id.item_minutes);
                item_hours = (NumberPicker) getView().findViewById(R.id.item_hours);
                if (!MainActivity.timeIsRunning) {
                    MainActivity.timeIsRunning = true;
                    MainActivity.dontDisplayTextWhenFinished = true;
                    startTime = 0;
                    startTime += TimeUnit.HOURS.toMillis(item_hours.getValue());
                    startTime += TimeUnit.MINUTES.toMillis(item_minutes.getValue());
                    startTime += TimeUnit.SECONDS.toMillis(item_seconds.getValue());
                    initTimerStart(startTime);
                    MainActivity.timeServiceIntent.putExtra("time", startTime);
                    getActivity().startService(MainActivity.timeServiceIntent);
                } else {
                    countDownTimer.cancel();
                    timerHasStarted = false;
                    startB.setText("Start again");
                    item_hours.setEnabled(true);
                    item_minutes.setEnabled(true);
                    item_seconds.setEnabled(true);
                    MainActivity.timeIsRunning = false;
                    MainActivity.dontDisplayTextWhenFinished = false;
                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RequestHandler.loadJsonArrayGet(TrelloUrls.getMembersUrl(mProjectId, ((PairProgrammingApplication) getActivity().getApplication()).getToken()),
                new RequestHandler.OnJsonArrayLoadedListener() {
                    @Override
                    public void onJsonDataLoadedSuccessfully(JSONArray data) {
                        mMembers = JsonSerializer.json2Members(data);
                        mOtherMembers = new ArrayList<>();
                        String selfUsername = getActivity().getApplication().getSharedPreferences(Constants.PREFS_NAME, 0).getString(Constants.PREF_USERNAME, null);
                        for (User user : mMembers) {
                            if (!user.getUsername().equals(selfUsername)) {
                                mOtherMembers.add(user);
                            }
                        }

                        view.findViewById(R.id.item_findPerson).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TextView displayPerson = (TextView) view.findViewById(R.id.item_thePerson);
                                Collections.shuffle(mOtherMembers);
                                displayPerson.setText(mOtherMembers.get(0).getFullName());
                            }
                        });
                    }

                    @Override
                    public void onJsonDataLoadingFailure(int errorId) {
                    }
                }, Request.Priority.HIGH, TAG);


    }

    public void initTimerStart(long time){
        item_seconds = (NumberPicker) getView().findViewById(R.id.item_seconds);
        item_minutes = (NumberPicker) getView().findViewById(R.id.item_minutes);
        item_hours = (NumberPicker) getView().findViewById(R.id.item_hours);
        item_hours.setEnabled(false);
        item_minutes.setEnabled(false);
        item_seconds.setEnabled(false);
        startB.setText("Pause");
        countDownTimer = new MalibuCountDownTimer(time, interval);
        countDownTimer.start();
        timerHasStarted = true;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(MainActivity.timeIsRunning){
            initTimerStart(MainActivity.remainingTimeMs);
        }
    }

    // CountDownTimer class
    public class MalibuCountDownTimer extends CountDownTimer
    {
        public MalibuCountDownTimer(long startTime, long interval)
        {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            timerHasStarted = false;
            startB.setText("Start again");
            item_hours.setValue(0);
            item_minutes.setValue(0);
            item_seconds.setValue(0);
            item_hours.setEnabled(true);
            item_minutes.setEnabled(true);
            item_seconds.setEnabled(true);
            MainActivity.timeIsRunning = false;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            item_hours.setValue((int)toTimeHH(millisUntilFinished));
            item_minutes.setValue((int)toTimeMM(millisUntilFinished));
            item_seconds.setValue((int)toTimeSS(millisUntilFinished));
        }

        public long toTimeHH(long ms){
            return TimeUnit.MILLISECONDS.toHours(ms);
        }

        public long toTimeMM(long ms){
            return TimeUnit.MILLISECONDS.toMinutes(ms) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(ms));
        }

        public long toTimeSS(long ms){
            return TimeUnit.MILLISECONDS.toSeconds(ms) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(ms));
        }
  }
}
