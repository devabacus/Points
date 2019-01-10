package com.example.user.points.ListOfItems;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user.points.Database.PointsData;
import com.example.user.points.PointsViewModel;
import com.example.user.points.R;
import com.example.user.points.StatFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LastItemsFragment extends Fragment implements View.OnLongClickListener{

    public static final String TAG = "test_point";

    private PointsViewModel pointsViewModel;
    private PointsAdapter pointsAdapter;

    public LastItemsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        pointsViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(PointsViewModel.class);
        View v = inflater.inflate(R.layout.fragment_last_item, container, false);

        final RecyclerView recyclerView = v.findViewById(R.id.rec_view);
        pointsAdapter = new PointsAdapter(new ArrayList<>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(pointsAdapter);
        pointsViewModel.getPointsList1().observe(getActivity(), pointsDataList -> pointsAdapter.addItems(pointsDataList));
        return v;
    }

    @Override
    public boolean onLongClick(View v) {
        PointsData pointsData = (PointsData) v.getTag();
        pointsViewModel.deleteItem(pointsData);
        StatFragment.newPointValue = pointsData.getPointValue();
        return true;
    }


}
