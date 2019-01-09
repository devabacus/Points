package com.example.user.points;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

public class StateViewModel extends AndroidViewModel {


    private MutableLiveData<Boolean> mCbEasyState = new MutableLiveData<>();

    public StateViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Boolean> getCbEasyState() {
        return mCbEasyState;
    }

    void setCbEasyState(Boolean cbEasyState) {
        mCbEasyState.setValue(cbEasyState);
    }
}
