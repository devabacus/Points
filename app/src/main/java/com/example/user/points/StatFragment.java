package com.example.user.points;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import com.example.user.points.R;

import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatFragment extends Fragment implements View.OnLongClickListener {

    PointsViewModel pointsViewModel;
    Button btnStat;

    public StatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stat, container, false);

        btnStat = v.findViewById(R.id.btn_stat);
        btnStat.setOnLongClickListener(this);

        final TextView tvStat = v.findViewById(R.id.gen_points);
        pointsViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(PointsViewModel.class);
        pointsViewModel.getPointsList().observe(getActivity(), new Observer<List<PointsData>>() {
            @Override
            public void onChanged(@Nullable List<PointsData> pointsDataList) {
                if(pointsDataList != null){
                    if (pointsDataList.size() > 0) {
                        tvStat.setText(String.valueOf(pointsDataList.get(pointsDataList.size() - 1).getCurValues()));
                    } else {
                        ButtonsFragment.curValues = 0;
                        tvStat.setText("0");
                    }
                }
                else
                    Log.d("myLogs", "pointsDataList == null" );
            }
        });
        return v;
    }

    @Override
    public boolean onLongClick(View v) {
        pointsViewModel.deleteAll();
        Toast.makeText(getContext(), "clear all", Toast.LENGTH_SHORT).show();
        return false;
    }
}
