package com.example.jonbeo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.jonbeo.activity.MainActivity;
import com.example.jonbeo.service.Mydetect;

public class ResultReceiver extends BroadcastReceiver {
    private AlarmReceiver alarmBroadcastReceiver;
    private Intent serviceIntent;


    @Override
    public void onReceive(Context context, Intent intent) {

        if ("result".equals(intent.getAction())){

            serviceIntent = new Intent(context, Mydetect.class);
            alarmBroadcastReceiver = new AlarmReceiver();
            ((MainActivity)MainActivity.mContext).enableButton();
            context.stopService(serviceIntent);

            ((MainActivity) MainActivity.mContext).devicePolicyManager.removeActiveAdmin(((MainActivity) MainActivity.mContext).componentName);
            try {
                context.unregisterReceiver(alarmBroadcastReceiver);
                context.unregisterReceiver(this);
            }catch (final Exception ec){

            }
        }

    }
}
