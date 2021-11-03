package com.example.jonbeo.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.jonbeo.activity.MainActivity;
import com.example.jonbeo.util.PreferenceManager;
import com.example.jonbeo.alarm.AlarmSetManager;


public class BootReceiver extends BroadcastReceiver {

    AlarmSetManager manager;
    private int mode;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())
        || Intent.ACTION_LOCKED_BOOT_COMPLETED.equals(intent.getAction())
        ) {
            Log.d("bootReceiver", "onReceive: boot!");

            mode = PreferenceManager.getInt(context,"type");
            manager = new AlarmSetManager(context);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ((MainActivity)MainActivity.mContext).disableButton();
                manager.SetAlarmStartService(mode);
            } else {
                manager.SetAlarmStartService(mode);
            }

        }

    }

}
