package com.example.jonbeo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;

public class alarmSetManager {

    private Context mContext;
    private AlarmManager alarmManager;
    private alarmReceiver alarmBroadcastReceiver;
    private Intent serviceIntent;
    private resultReceiver receiver;

    public alarmSetManager(Context context){
        mContext=context;
        serviceIntent = new Intent(mContext,Mydetect.class);
        alarmBroadcastReceiver = new alarmReceiver();
        receiver = new resultReceiver();
    }

    public void setAlarm(int id) {

        //AlarmReceiver에 값 전달
        alarmManager  = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent receiverIntent = new Intent(mContext, alarmReceiver.class);
        receiverIntent.setAction("wakeup");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, receiverIntent, 0);
        Calendar cal = Calendar.getInstance();

        if ( id == R.id.swing) {
            cal.add(Calendar.DATE,14);
            PreferenceManager.setInt(mContext,"type",R.id.swing);
        }
        else if( id == R.id.Long){
            cal.add(Calendar.DATE,30);
            PreferenceManager.setInt(mContext,"type",R.id.swing);
        }

        alarmManager.setExactAndAllowWhileIdle(android.app.AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),pendingIntent);
        Log.d("alarm", "SetAlarmStartService: registed?");
    }

    public void SetAlarmStartService(int id){

        IntentFilter intentFilter = new IntentFilter("wakeup");
        IntentFilter intentFilter2 = new IntentFilter("result");
        intentFilter.setPriority(900);
        intentFilter2.setPriority(700);
        mContext.getApplicationContext().registerReceiver(alarmBroadcastReceiver,intentFilter);
        mContext.getApplicationContext().registerReceiver(receiver,intentFilter2);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            setAlarm(id);
            mContext.startForegroundService(serviceIntent);

        }
        else {
            setAlarm(id);
            mContext.startService(serviceIntent);
        }
    }



}
