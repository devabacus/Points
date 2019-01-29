package com.example.user.points;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.points.Database.PointsData;
import com.example.user.points.Dialogs.ConfirmAlertDialog;
import com.example.user.points.ListOfItems.LastItemsFragment;
import com.example.user.points.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatFragment extends Fragment implements View.OnLongClickListener, View.OnClickListener {

    public static final String TAG = "test_point";
    PointsViewModel pointsViewModel;
    Button btnStat;
    public static Integer curStatValue = 0;
    public static Integer newPointValue = 0;
    Integer lastListSize = 0;
    TextView tvStat, tvTodayPoint;
    public static int startTodaysPoint = 0;
    public static Date startDate;
    public static Date endDate;
    boolean tvStatVisibility = false;



    enum Confirm_data{
        DELETE_ITEM,
        DELETE_ALL
    }

    Confirm_data confirm_data;

    public StatFragment() {
        // Required empty public constructor
    }

    void create_alert_dialog(Confirm_data conf_data){

        String dialogTitle = "";
        if (conf_data == Confirm_data.DELETE_ITEM) {
            dialogTitle = "Delete this item?";
        } else if (conf_data == Confirm_data.DELETE_ALL) {
            dialogTitle = "DELETE ALL ITEMS?";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(dialogTitle)
                .setCancelable(true)
                .setPositiveButton("Yes", (dialog, which) -> {
                    if (conf_data == Confirm_data.DELETE_ALL) {
                        pointsViewModel.deleteAll();
                        curStatValue = 0;
                        tvStat.setText("0");
                        Toast.makeText(getContext(), "Данные удалены из базы", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("No", (dialog, which) ->{
                    Toast.makeText(getContext(), "Не удаляем", Toast.LENGTH_SHORT).show();
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stat, container, false);

        startDate = new Date();
        endDate = new Date();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        startDate = cal.getTime();

        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);

        endDate = cal.getTime();


        btnStat = v.findViewById(R.id.btn_stat);
        btnStat.setOnClickListener(this);
        btnStat.setOnLongClickListener(this);
        tvStat = v.findViewById(R.id.gen_points);
        tvTodayPoint = v.findViewById(R.id.tv_todays_point);

        pointsViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(PointsViewModel.class);

        pointsViewModel.getPointsListByTime(startDate, endDate).observe(this, new Observer<List<PointsData>>() {
            @Override
            public void onChanged(@Nullable List<PointsData> pointsData) {
                assert pointsData != null;
                PointsData firstPointItem;
                if (pointsData.size() > 0) {
                    firstPointItem = pointsData.get(pointsData.size() - 1);
                    startTodaysPoint = firstPointItem.getCurValues() - firstPointItem.getPointValue();
                } else {
                    startTodaysPoint = 0;
                }

                //Toast.makeText(getContext(), String.valueOf(startTodaysPoint), Toast.LENGTH_SHORT).show();

                //Log.d(TAG, "onChanged: curStatValue = " + curStatValue);
                //Log.d(TAG, "onChanged: startTodaysPoint = " + startTodaysPoint);


            }
        });


        pointsViewModel.getPointsList().observe(getActivity(), pointsDataList -> {
            if(pointsDataList != null){
                if (pointsDataList.size() > 0) {
                    tvStat.setText(String.valueOf(pointsDataList.get(pointsDataList.size() - 1).getCurValues()));
                    //lastListSize = pointsDataList.size();
                    //if add item into the database
                    Log.d(TAG, "pointsDataList.size() = " + pointsDataList.size());
                    Log.d(TAG, "lastListSize = " + lastListSize);
                    if (pointsDataList.size() >= lastListSize) {
                       curStatValue =  pointsDataList.get(pointsDataList.size() - 1).getCurValues();
                    }
                    // if remove item from the database
                    else {
                        Log.d(TAG, "less");
                       curStatValue = curStatValue - newPointValue;
                    }
                    lastListSize = pointsDataList.size();
                    Log.d(TAG, "onChanged: curStatValue = " + curStatValue);
                    Log.d(TAG, "onChanged: startTodaysPoint = " + startTodaysPoint);
                    tvStat.setText(String.valueOf(curStatValue));
                    tvTodayPoint.setText(String.valueOf(curStatValue - startTodaysPoint));


                } else {
                    ButtonsFragment.curValues = 0;
                    tvStat.setText("0");
                }
            }
            else
                Log.d("myLogs", "pointsDataList == null" );
        });

        return v;
    }
    //click on stat button
    @Override
    public void onClick(View v) {
        if (tvStatVisibility) {
            tvStat.setVisibility(View.GONE);
            tvStatVisibility = false;
        } else {
            tvStat.setVisibility(View.VISIBLE);
            tvStatVisibility = true;
        }
    }


    // long click on stat button
    @Override
    public boolean onLongClick(View v) {
        create_alert_dialog(Confirm_data.DELETE_ALL);
        return false;
    }
}
