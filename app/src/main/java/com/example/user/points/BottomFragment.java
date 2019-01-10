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

import java.util.Objects;

import static com.example.user.points.ButtonsFragment.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class BottomFragment extends Fragment {

    private Chronometer chronometer;
    private boolean mRunning;
    private long elapsedTime = 0;

    public BottomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bottom, container, false);
        chronometer = v.findViewById(R.id.chronometer);

        chronometer.setOnClickListener(v1 -> {
            if (!mRunning) {
                //SystemClock.elapsedRealtime() is current elapsed time - constantly change
                //chronometer.getBase() is time when the chronometer.start() is called. So every time we call this function we change getBase
                //if we don't setbase start function as default start to count from getBase value (first time when the app is started)
                //CHRONOMETER ALWAYS SHOW DIFFERENCE BETWEEN SystemClock.elapsedRealtime() AND GETBASE мы как бы его наебываем и перед каждым продолж. стартом
                //мы отнимаем от текущего времени, то которое сохранили (которое на хронометре отображается) и говорим, и устанавливаем это как базовое время
                //соответственно хронометер и будет отображать сохраненное время и соответственно начнет считать с него же
                Log.d(TAG, "---------------------start-------------------");
                Log.d(TAG, "elapsedTime = " + elapsedTime/1000);
                Log.d(TAG, "SystemClock.elapsedRealtime() = " + SystemClock.elapsedRealtime()/1000);
                Log.d(TAG, "chronometer.getBase() = " + chronometer.getBase()/1000);
                Log.d(TAG, "SystemClock.elapsedRealtime() - elapsedTime = " + SystemClock.elapsedRealtime()/1000 + " - " + elapsedTime/1000 + " = " + (SystemClock.elapsedRealtime()/1000 - elapsedTime/1000));
                chronometer.setBase(SystemClock.elapsedRealtime() - elapsedTime);
                Log.d(TAG, "after chronometer.getBase() = " + chronometer.getBase()/1000);

                chronometer.start();
                mRunning = true;
            } else {
                Log.d(TAG, "---------------------stop-------------------");
                Log.d(TAG, "SystemClock.elapsedRealtime() = " + SystemClock.elapsedRealtime()/1000);
                Log.d(TAG, "chronometer.getBase() = " + chronometer.getBase()/1000);
                elapsedTime = SystemClock.elapsedRealtime() - chronometer.getBase();
                Log.d(TAG, "elapsedTime = " + elapsedTime/1000);
                chronometer.stop();
                mRunning = false;
            }
            ((MainActivity)Objects.requireNonNull(getActivity())).mvibrate(100);

        });

        chronometer.setOnLongClickListener(v1 -> {
            chronometer.setBase(SystemClock.elapsedRealtime());
            elapsedTime = 0;
            return true;
        });

        return v;
    }
}
