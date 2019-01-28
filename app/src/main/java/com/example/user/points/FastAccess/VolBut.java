package com.example.user.points.FastAccess;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user.points.CategFragment;
import com.example.user.points.Database.PointsData;
import com.example.user.points.PointsViewModel;
import com.example.user.points.R;

import java.util.Date;
import java.util.Objects;

import static com.example.user.points.ButtonsFragment.curValues;


/**
 * A simple {@link Fragment} subclass.
 */
public class VolBut extends Fragment {

    public static final String TAG = "test_point";
    CountDownTimer countDownTimer;
    VolButViewModel volButViewModel;
    PointsViewModel pointsViewModel;
    Integer pointValue = 0;
    Boolean timeIsFired = false;
    Boolean timerStarted = false;

    public VolBut() {
        // Required empty public constructor
    }

    void startTimerWork() {

        timerStarted = true;
        countDownTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
              //  Log.d(TAG, "onTick: millisUntilFinished = " + millisUntilFinished);
            }

            @Override
            public void onFinish() {
                timeIsFired = true;
                curValues = curValues + pointValue;
                //Log.d(TAG, "onFinish: poinValue = " + pointValue);
                pointsViewModel.addPointsItem(new PointsData(

                        new Date(), 3, 0, pointValue, curValues
                ));

                timeIsFired = false;
                volButViewModel.setmNumValue(0);
                timerStarted = false;
            }
        }.start();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        volButViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(VolButViewModel.class);
        pointsViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(PointsViewModel.class);
        View v =  inflater.inflate(R.layout.fragment_volume_button, container, false);

        volButViewModel.getmNumbValue().observe(getActivity(), num ->{
            assert num != null;

            if (num!=0) {
                Log.d(TAG, "num = " + num);
                if(!timerStarted) startTimerWork();
                if (!timeIsFired) {
                    pointValue = num;
                    countDownTimer.cancel();
                    startTimerWork();
                }
            }
        });
        return v;
    }

}
