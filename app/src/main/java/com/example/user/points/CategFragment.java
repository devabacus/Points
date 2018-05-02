package com.example.user.points;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategFragment extends Fragment {

    public static String[] cat;
    public static String[] cat_rus;
    public static int cur_cat;
    RadioGroup radioGroup;

    public CategFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_categ, container, false);
        radioGroup = v.findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int rbId = radioGroup.getCheckedRadioButtonId();
                RadioButton rb = Objects.requireNonNull(getView()).findViewById(rbId);
                cur_cat =  radioGroup.indexOfChild(rb);
            }
        });

        cat = getResources().getStringArray(R.array.cat);
        cat_rus = getResources().getStringArray(R.array.cat_rus);
        return v;
    }

}
