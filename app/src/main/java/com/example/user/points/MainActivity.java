package com.example.user.points;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.example.user.points.Dialogs.ConfirmAlertDialog;
import com.example.user.points.FastAccess.VolBut;
import com.example.user.points.FastAccess.VolButViewModel;

public class MainActivity extends AppCompatActivity {


    public static final String TAG = "test";
    static Vibrator vibrator;
    Integer volButNum = 0;
    VolButViewModel volButViewModel;
    boolean longPress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        volButViewModel = ViewModelProviders.of(this).get(VolButViewModel.class);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        VolBut volButFragment = new VolBut();
        fragmentTransaction.add(R.id.frag_volbut, volButFragment);
        fragmentTransaction.commit();
        volButViewModel.getmNumbValue().observe(this, num ->{
            volButNum = num;
        });
    }

    //pressed volume button
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            //отслеживание дальнейшее поведение кнопки
            event.startTracking();
        }
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            event.startTracking();
        }
        return true;

    }

    //released volume button
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (!longPress) {
            if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
                volButNum+=5;

            } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
                volButNum-=5;
            }
            mvibrate(100);

            volButViewModel.setmNumValue(volButNum);
            Log.d(TAG, "volButNum = " + volButNum);
        }
        return true;
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            mvibrate(200);
            volButNum+=20;
            longPress = true;
            Log.d(TAG, "onKeyLongPress: up");
        }
        else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            mvibrate(200);
            volButNum-=20;
            longPress = true;
            Log.d(TAG, "onKeyLongPress: down");
        }
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            longPress = false;
            Log.d(TAG, "run: post delayed");
        }, 500);

        volButViewModel.setmNumValue(volButNum);
        Log.d(TAG, "volButNum = " + volButNum);

        return super.onKeyLongPress(keyCode, event);
    }

    public static void mvibrate(int num) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(num, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(num);
        }
    }

}
