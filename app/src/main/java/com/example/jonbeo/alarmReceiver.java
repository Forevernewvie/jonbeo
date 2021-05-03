package com.example.jonbeo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class alarmReceiver extends BroadcastReceiver {

    public alarmReceiver() {}
    Notification notification;
    NotificationManager manager;
    NotificationChannel notificationChannel;
    private final String ch_id = "alarm";
    private final String ch_name = "alarmRun";

    @Override
    public void onReceive(Context context, Intent intent) {


        manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(ch_id, ch_name, NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(notificationChannel);
            Intent mainIntent = new Intent(context, resultReceiver.class);
            mainIntent.setAction("result");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 101, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            if ("wakeup".equals(intent.getAction()))
            {
                Log.d("alarmReceiver", "onReceive: received? ");
                notification = new Notification.Builder(context, ch_id)
                        .setContentTitle("다 버텼습니다.")
                        .setContentText("축하합니다! 알림을 눌러주세요.")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .build();

                manager.notify(2, notification);

            }
        }
    }
}
