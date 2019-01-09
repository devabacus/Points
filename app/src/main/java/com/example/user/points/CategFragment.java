package com.example.user.points;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Objects;

public class CategFragment extends Fragment {

    public static final String TAG = "test";

    public static String[] cat;
    public static String[] cat_rus;
    public static String[] cat_mind;
    public static int cur_cat;
    public static int cur_cat3;
    RadioGroup radioGroup;
    RadioGroup radioGroup2;
    RadioGroup radioGroup3;
    RadioButton radioButton;
    Button btnBack;

    StateViewModel stateViewModel;

    public CategFragment() {
        // Required empty public constructor
    }


    void main_cat() {
        radioGroup.setVisibility(View.VISIBLE);
        radioGroup2.setVisibility(View.GONE);
        radioGroup3.setVisibility(View.GONE);
        btnBack.setVisibility(View.GONE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_categ, container, false);
        radioGroup = v.findViewById(R.id.radio_group);
        btnBack = v.findViewById(R.id.btn_home_cat);
        radioGroup2 = v.findViewById(R.id.radio_group_fit);
        radioGroup3 = v.findViewById(R.id.radio_group_mind);
        radioButton = v.findViewById(R.id.rb3);
        stateViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(StateViewModel.class);

        //disable nested list
//        stateViewModel.getCbEasyState().observe(getActivity(), state ->{
//            assert state != null;
//            if (state) {
////                radioGroup.setVisibility(View.GONE);
////                radioGroup2.setVisibility(View.GONE);
////                radioGroup3.setVisibility(View.VISIBLE);
//                //set "general" of "mind" category as defualt radio button
//                //radioGroup3.check(R.id.rb3_1);
//                //btnBack.setVisibility(View.VISIBLE);
//                radioGroup.check(R.id.rb3);
//
//            } else {
//                //main_cat();
//                radioGroup.check(R.id.rb0);
//
//            }

//        });


        //I don't need that yet
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int rbId = radioGroup.getCheckedRadioButtonId();
                RadioButton rb = Objects.requireNonNull(getView()).findViewById(rbId);
                cur_cat = radioGroup.indexOfChild(rb);

//                if (cur_cat == 2) {
//                    radioGroup.setVisibility(View.GONE);
//                    radioGroup3.setVisibility(View.GONE);
//                    radioGroup2.setVisibility(View.VISIBLE);
//                    btnBack.setVisibility(View.VISIBLE);
//                }
//
//                if (cur_cat == 3) {
//                    radioGroup.setVisibility(View.GONE);
//                    radioGroup2.setVisibility(View.GONE);
//                    radioGroup3.setVisibility(View.VISIBLE);
//                    //set "general" of "mind" category as defualt radio button
//                    radioGroup3.check(R.id.rb3_1);
//                    btnBack.setVisibility(View.VISIBLE);
//                }
            }
        });



        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_cat();
            }
        });

        cat = getResources().getStringArray(R.array.cat);
        cat_mind = getResources().getStringArray(R.array.cat_mind);
        return v;
    }

}
