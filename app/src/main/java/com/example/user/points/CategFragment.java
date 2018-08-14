package com.example.user.points;


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


/**
 * A simple {@link Fragment} subclass.
 */
public class CategFragment extends Fragment {

    public static final String TAG = "test";

    public static String[] cat;
    public static String[] cat_rus;
    public static String[] cat_rus_mind;
    public static int cur_cat;
    public static int cur_cat2;
    RadioGroup radioGroup;
    RadioGroup radioGroup2;
    RadioButton radioButton;
    Button btnBack;

    public CategFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_categ, container, false);
        radioGroup = v.findViewById(R.id.radio_group);
        btnBack = v.findViewById(R.id.btn_home_cat);
        radioGroup2 = v.findViewById(R.id.radio_group_mind);
        radioButton = v.findViewById(R.id.rb3);
        Log.d("test", "onCheckedChanged: ");
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int rbId = radioGroup.getCheckedRadioButtonId();
                RadioButton rb = Objects.requireNonNull(getView()).findViewById(rbId);
                cur_cat = radioGroup.indexOfChild(rb);

                if (checkedId == R.id.rb3) {
                    radioGroup.setVisibility(View.GONE);
                    radioGroup2.setVisibility(View.VISIBLE);
                    btnBack.setVisibility(View.VISIBLE);
                }
            }
        });

        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int rbmId = radioGroup2.getCheckedRadioButtonId();
                RadioButton rbm = Objects.requireNonNull(getView()).findViewById(rbmId);
                cur_cat2 = radioGroup2.indexOfChild(rbm);
                Log.d(TAG, "cur_cat2 = " + String.valueOf(cur_cat2));
            }
        });



        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioGroup.setVisibility(View.VISIBLE);
                radioGroup2.setVisibility(View.GONE);
                btnBack.setVisibility(View.GONE);
            }
        });


        cat = getResources().getStringArray(R.array.cat);
        cat_rus = getResources().getStringArray(R.array.cat_rus);
        cat_rus_mind = getResources().getStringArray(R.array.cat_rus_mind);
        return v;
    }

}
