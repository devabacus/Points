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
import com.example.user.points.R;

import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatFragment extends Fragment implements View.OnLongClickListener {

    public static final String TAG = "test_point";
    PointsViewModel pointsViewModel;
    Button btnStat;
    public static Integer curStatValue = 0;
    public static Integer newPointValue = 0;
    Integer lastListSize = 0;


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
                        //pointsViewModel.deleteAll();
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



        btnStat = v.findViewById(R.id.btn_stat);
        btnStat.setOnLongClickListener(this);
        final TextView tvStat = v.findViewById(R.id.gen_points);
        pointsViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(PointsViewModel.class);
        pointsViewModel.getPointsList().observe(getActivity(), pointsDataList -> {
            if(pointsDataList != null){
                if (pointsDataList.size() > 0) {
                    tvStat.setText(String.valueOf(pointsDataList.get(pointsDataList.size() - 1).getCurValues()));
                    //lastListSize = pointsDataList.size();
                    //if add item into the database
                    Log.d(TAG, "pointsDataList.size() = " + pointsDataList.size());
                    Log.d(TAG, "lastListSize = " + lastListSize);
                    if (pointsDataList.size() >= lastListSize) {
                        Log.d(TAG, "pointsDataList.size() >= lastListSize");
                       curStatValue =  pointsDataList.get(pointsDataList.size() - 1).getCurValues();
                    }
                    // if remove item from the database
                    else {
                        Log.d(TAG, "less");
                       curStatValue = curStatValue - newPointValue;
                    }
                    lastListSize = pointsDataList.size();
                    tvStat.setText(String.valueOf(curStatValue));
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

    @Override
    public boolean onLongClick(View v) {
        create_alert_dialog(Confirm_data.DELETE_ALL);
        return false;
    }
}
