package com.example.user.points;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.example.user.points.ButtonsFragment.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class BottomFragment extends Fragment {

    private Chronometer chronometer;
    private boolean mRunning;
    private long elapsedTime = 0;
    TextView tvStartTime, tvEndTime, tvCategory;
    SharedPreferences sharedPreferences;
    boolean mVibrated = false;
    boolean isAlarmSet = false;
    public static final String KEY_TIMER_START_VALUE = "timer_start_value";
    public static final String KEY_TIMER_CATEGORY = "timer_category";
    public static final String KEY_TIMER_ELAPSED = "timer_elapsed_base";

    public BottomFragment() {
        // Required empty public constructor
    }

    void alarmSet(int minutes) {
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(BottomFragment.this.getActivity(), AlarmTask.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, 0);
        alarmManager.setRepeating(AlarmManager.RTC, (TimeUnit.MINUTES.toMillis(minutes)), AlarmManager.INTERVAL_DAY, pendingIntent);
        Log.d(TAG, "alarmSet: created alarm task");
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bottom, container, false);
        chronometer = v.findViewById(R.id.chronometer);
        tvStartTime = v.findViewById(R.id.tv_time_st);
        tvEndTime = v.findViewById(R.id.tv_time_end);
        tvCategory = v.findViewById(R.id.tv_category);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String startValue = sharedPreferences.getString(KEY_TIMER_START_VALUE, "00:00");
        String catName = sharedPreferences.getString(KEY_TIMER_CATEGORY, "");
//        elapsedTime = Long.parseLong(sharedPreferences.getString(KEY_TIMER_ELAPSED, "0"));
        Log.d(TAG, "elapsedTime: " + elapsedTime);
//        String elapsedTimeBase = formattedDate(elapsedTime);
        tvCategory.setText(catName);
        if (!startValue.equals("00:00")) {
            tvStartTime.setText(startValue);
            tvStartTime.setVisibility(View.VISIBLE);
            tvCategory.setVisibility(View.VISIBLE);
//            setStartData();
//            chronStart();
        } else {
            tvStartTime.setVisibility(View.GONE);
            tvCategory.setVisibility(View.GONE);
        }
        chronometer.setOnChronometerTickListener(chronometer -> {
            long vibrateMinutes = TimeUnit.MINUTES.toMillis(1);
//                if(!isAlarmSet){
//                    alarmSet(1);
//                    isAlarmSet = true;
//                }
            if(SystemClock.elapsedRealtime() - chronometer.getBase() > vibrateMinutes){
                if(!mVibrated){
                    MainActivity.mvibrate(1000);
                    mVibrated = true;
                }
            }
        });
        chronometer.setOnClickListener(v1 -> {
            setStartData();
            chronHandle();
            ((MainActivity) Objects.requireNonNull(getActivity())).mvibrate(100);

        });

        chronometer.setOnLongClickListener(v1 -> {
            chronometer.setBase(SystemClock.elapsedRealtime());
            elapsedTime = 0;
            chronometer.stop();
            clearStartData();
            return true;

        });

        return v;
    }


    void chronStart(){
        long curTimeElapsed = SystemClock.elapsedRealtime() - elapsedTime;

        chronometer.setBase(curTimeElapsed);
        Log.d(TAG, "elapsedTime: " + elapsedTime);
        Log.d(TAG, "currentTime: " + SystemClock.elapsedRealtime());
        saveToSP(KEY_TIMER_ELAPSED, String.valueOf(curTimeElapsed));
        chronometer.start();
        mRunning = true;
    }

    void chronStop(){
        elapsedTime = SystemClock.elapsedRealtime() - chronometer.getBase();
        Log.d(TAG, "elapsedTime: " + elapsedTime);
        Log.d(TAG, "currentTime: " + SystemClock.elapsedRealtime());
        chronometer.stop();
        mRunning = false;
    }

    void chronHandle(){
        if (!mRunning) {
            chronStart();
        } else {
           chronStop();
        }
    }


    void saveToSP(String key, String str) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, str);
        editor.apply();
    }

    String formattedDate(long unixTime) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss", new Locale("ru"));
        return df.format(new Date(unixTime));
    }

    private void setStartData() {
        long unixTime = new Date().getTime();
        saveToSP(KEY_TIMER_START_VALUE, formattedDate(unixTime));

        Log.d(TAG, "KEY_TIMER_START_VALUE: " + unixTime);
        if (!mRunning && elapsedTime == 0) {
            tvStartTime.setText(formattedDate(unixTime));
            tvStartTime.setVisibility(View.VISIBLE);
            Log.d(TAG, "setStartData: start");
        } else if (mRunning) {
            Log.d(TAG, "setStartData: end");
            tvEndTime.setText(formattedDate(unixTime));
            tvEndTime.setVisibility(View.VISIBLE);
        }

        String catName = CategFragment.cat[(CategFragment.cur_cat)];
        tvCategory.setText(catName);
        saveToSP(KEY_TIMER_CATEGORY, catName);
        tvCategory.setVisibility(View.VISIBLE);
    }

    private void clearStartData() {
        tvStartTime.setVisibility(View.GONE);
        tvEndTime.setVisibility(View.GONE);
        tvCategory.setVisibility(View.GONE);
        saveToSP(KEY_TIMER_START_VALUE, "00:00");
        saveToSP(KEY_TIMER_CATEGORY, "");
        mVibrated = false;
    }
}
