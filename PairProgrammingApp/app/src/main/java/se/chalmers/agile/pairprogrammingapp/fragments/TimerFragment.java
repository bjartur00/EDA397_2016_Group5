package se.chalmers.agile.pairprogrammingapp.fragments;


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

import java.util.concurrent.TimeUnit;

import se.chalmers.agile.pairprogrammingapp.R;
import se.chalmers.agile.pairprogrammingapp.activities.MainActivity;
import se.chalmers.agile.pairprogrammingapp.model.TimeService;


public class TimerFragment extends Fragment {


    private static final String tag = "Main";
    private MalibuCountDownTimer countDownTimer;
    private boolean timerHasStarted = false;
    private NumberPicker item_seconds;
    private NumberPicker item_minutes;
    private NumberPicker item_hours;
    private Button startB;

    private long startTime = 0;
    private final long interval = 1000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        startB = (Button) view.findViewById(R.id.item_startTime);
        startB.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                item_seconds = (NumberPicker) getView().findViewById(R.id.item_seconds);
                item_minutes = (NumberPicker) getView().findViewById(R.id.item_minutes);
                item_hours = (NumberPicker) getView().findViewById(R.id.item_hours);
                if (!timerHasStarted)
                {
                    startTime = 0;
                    startTime += TimeUnit.HOURS.toMillis(item_hours.getValue());
                    startTime += TimeUnit.MINUTES.toMillis(item_minutes.getValue());
                    startTime += TimeUnit.SECONDS.toMillis(item_seconds.getValue());
                    initTimerStart(startTime);
                    Intent intent = new Intent(getActivity(), TimeService.class);
                    intent.putExtra("time", startTime);
                    getActivity().startService(intent);
                }
                else
                {
                    countDownTimer.cancel();
                    timerHasStarted = false;
                    startB.setText("Start again");
                    item_hours.setEnabled(true);
                    item_minutes.setEnabled(true);
                    item_seconds.setEnabled(true);
                }
            }
        });
        return view;
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
            Log.i("boolean", "YES");
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
            // item_time_remain.setText("Time's up!");
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
