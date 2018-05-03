package com.example.user.points.ListOfItems;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.points.Database.PointsData;
import com.example.user.points.PointsViewModel;
import com.example.user.points.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class LastItemsFragment extends Fragment {

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
        pointsAdapter = new PointsAdapter(new ArrayList<PointsData>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(pointsAdapter);

        pointsViewModel.getPointsList1().observe(getActivity(), new Observer<List<PointsData>>() {
            @Override
            public void onChanged(@Nullable List<PointsData> pointsDataList) {
                pointsAdapter.addItems(pointsDataList);
            }
        });

        return v;
    }

}
