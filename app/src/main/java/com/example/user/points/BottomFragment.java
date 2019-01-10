package com.example.user.points;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static com.example.user.points.ButtonsFragment.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class BottomFragment extends Fragment {

    private Chronometer chronometer;
    private boolean mRunning;
    private long elapsedTime = 0;
    TextView tvStartTime, tvEndTime, tvCategory;

    public BottomFragment() {
        // Required empty public constructor
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

        chronometer.setOnClickListener(v1 -> {
            setStartData();
            if (!mRunning) {
                chronometer.setBase(SystemClock.elapsedRealtime() - elapsedTime);
                chronometer.start();
                mRunning = true;

            } else {
                elapsedTime = SystemClock.elapsedRealtime() - chronometer.getBase();
                chronometer.stop();
                mRunning = false;
            }

            ((MainActivity)Objects.requireNonNull(getActivity())).mvibrate(100);

        });

        chronometer.setOnLongClickListener(v1 -> {
            chronometer.setBase(SystemClock.elapsedRealtime());
            elapsedTime = 0;
            clearStartData();
            return true;

        });

        return v;
    }

    private void setStartData() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss", new Locale("ru"));
        String formattedDate = df.format(new Date());
        if (!mRunning && elapsedTime == 0) {
            tvStartTime.setText(formattedDate);
            tvStartTime.setVisibility(View.VISIBLE);
            Log.d(TAG, "setStartData: start");
        } else if (mRunning){
            Log.d(TAG, "setStartData: end");
            tvEndTime.setText(formattedDate);
            tvEndTime.setVisibility(View.VISIBLE);
        }
        
        tvCategory.setText(CategFragment.cat[(CategFragment.cur_cat)]);
        tvCategory.setVisibility(View.VISIBLE);
    }

    private void clearStartData() {
        tvStartTime.setVisibility(View.GONE);
        tvEndTime.setVisibility(View.GONE);
        tvCategory.setVisibility(View.GONE);
    }
}
