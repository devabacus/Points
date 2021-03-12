package com.example.user.points;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.user.points.MainActivity;

class AlarmTask extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        MainActivity.mvibrate(1000);
        Toast.makeText(context, "I am work", Toast.LENGTH_SHORT).show();
        Log.d("test", "onReceive: ");
    }
}
