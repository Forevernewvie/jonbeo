package com.example.jonbeo.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;

import com.example.jonbeo.receiver.AlarmReceiver;
import com.example.jonbeo.util.PreferenceManager;
import com.example.jonbeo.R;
import com.example.jonbeo.receiver.ResultReceiver;
import com.example.jonbeo.service.Mydetect;

import java.util.Calendar;

public class AlarmSetManager {

    private Context mContext;
    private AlarmManager alarmManager;
    private AlarmReceiver alarmBroadcastReceiver;
    private Intent serviceIntent;
    private ResultReceiver receiver;

    public AlarmSetManager(Context context){
        mContext=context;
        serviceIntent = new Intent(mContext, Mydetect.class);
        alarmBroadcastReceiver = new AlarmReceiver();
        receiver = new ResultReceiver();
    }

    public void setAlarm(int id) {

        //AlarmReceiver에 값 전달
        alarmManager  = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent receiverIntent = new Intent(mContext, AlarmReceiver.class);
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
