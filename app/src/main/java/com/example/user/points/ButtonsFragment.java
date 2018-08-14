package com.example.user.points;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.points.Database.PointsData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class ButtonsFragment extends Fragment implements View.OnClickListener {

    private Date date;
    private PointsViewModel pointsViewModel;
    private PointsData lastPointsData;
    RadioButton rbMinus;
    public static final String TAG = "test";
    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btnRes, btnSave;
    TextView tvPoint;

    int mValue;
    int mPoint;
    public static int curValues = 0;
    String point = "";

    public ButtonsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        pointsViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(PointsViewModel.class);

        View v = inflater.inflate(R.layout.fragment_buttons, container, false);

        pointsViewModel.getPointsList().observe(getActivity(), new Observer<List<PointsData>>() {
            @Override
            public void onChanged(@Nullable List<PointsData> pointsDataList) {

                if (pointsDataList != null) {
                    if (pointsDataList.size() > 0)
                        curValues = pointsDataList.get(pointsDataList.size() - 1).getCurValues();
                } else
                    Log.d("myLogs", "pointsDataList == null");
            }
        });


        rbMinus = v.findViewById(R.id.rbMinus);

        btn0 = v.findViewById(R.id.but0);
        btn1 = v.findViewById(R.id.but1);
        btn2 = v.findViewById(R.id.but2);
        btn3 = v.findViewById(R.id.but3);
        btn4 = v.findViewById(R.id.but4);
        btn5 = v.findViewById(R.id.but5);
        btn6 = v.findViewById(R.id.but6);
        btn7 = v.findViewById(R.id.but7);
        btn8 = v.findViewById(R.id.but8);
        btn9 = v.findViewById(R.id.but9);
        btn10 = v.findViewById(R.id.but10);
        btnRes = v.findViewById(R.id.but_с);
        btnSave = v.findViewById(R.id.but_save);

        tvPoint = v.findViewById(R.id.tv_point);

        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);
        btnRes.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        // PointsData pointsData = new PointsData();

        String value = "0";


        switch (v.getId()) {
            case R.id.but0:
                value = "0";
                break;
            case R.id.but1:
                value = "1";
                break;
            case R.id.but2:
                value = "2";
                break;
            case R.id.but3:
                value = "3";
                break;
            case R.id.but4:
                value = "4";
                break;
            case R.id.but5:
                value = "5";
                break;
            case R.id.but6:
                value = "6";
                break;
            case R.id.but7:
                value = "7";
                break;
            case R.id.but8:
                value = "8";
                break;
            case R.id.but9:
                value = "9";
                break;
            case R.id.but10:
                value = "10";
                break;
            case R.id.but_с:
                value = "";
                point = "";
                tvPoint.setText("");
                break;
            case R.id.but_save:

                if (tvPoint.getText().toString().equals(""))
                    break;
                else {

                    mPoint = Integer.parseInt(point);
                    if(rbMinus.isChecked()){
                        mPoint = -mPoint;
                    }
                    curValues = curValues + mPoint;

                    savePointItem();

                    value = "";
                    point = "";
                }
        }

        if (!value.equals("")) {
            point += value;
            tvPoint.setText(point);
        }

//        if(!point.equals("0"))


    }

    private void savePointItem() {

        if (!tvPoint.getText().toString().equals(""))
            //если не мораль выбрана
                pointsViewModel.addPointsItem(new PointsData(
                        new Date(), CategFragment.cur_cat, CategFragment.cur_cat2, mPoint, curValues
                ));
        Log.d(TAG, "cur_cat = " + CategFragment.cur_cat + ", " + "cur_cat2 = " + CategFragment.cur_cat2);

            }

    }

