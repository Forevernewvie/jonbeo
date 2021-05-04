package com.example.jonbeo;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class resultReceiver extends BroadcastReceiver {
    private alarmReceiver alarmBroadcastReceiver;
    private Intent serviceIntent;
    @Override
    public void onReceive(Context context, Intent intent) {

        if ("result".equals(intent.getAction())){

            serviceIntent = new Intent(context,Mydetect.class);
            alarmBroadcastReceiver = new alarmReceiver();
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
