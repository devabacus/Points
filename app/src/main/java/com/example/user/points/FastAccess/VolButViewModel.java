package com.example.user.points.FastAccess;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

public class VolButViewModel extends AndroidViewModel {

    public static final String TAG = "test_point";
    private final MutableLiveData<Integer> mNumValue = new MutableLiveData<>();


    public LiveData<Integer> getmNumbValue(){return mNumValue;}

    public void setmNumValue(final Integer numValue) {

       // Log.d(TAG, "setmNumValue = " + numValue);
        mNumValue.setValue(numValue);
    }
    public VolButViewModel(@NonNull Application application) {
        super(application);
    }
}
