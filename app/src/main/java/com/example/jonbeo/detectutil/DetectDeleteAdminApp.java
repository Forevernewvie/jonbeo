package com.example.jonbeo.detectutil;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Build;
import android.util.LongSparseArray;

import androidx.annotation.NonNull;
import androidx.core.os.BuildCompat;

import com.example.jonbeo.BuildConfig;

public class DetectDeleteAdminApp {

    public  String getKlassName(@NonNull Context context) {

        UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);

        long lastRunAppTimeStamp = 0L;

        final long INTERVAL = 2000;
        final long end = System.currentTimeMillis();
        final long begin = end - INTERVAL;

        LongSparseArray packageNameMap = new LongSparseArray<>();

        final UsageEvents usageEvents = usageStatsManager.queryEvents(begin, end);

        while (usageEvents.hasNextEvent()) {

            UsageEvents.Event event = new UsageEvents.Event();
            usageEvents.getNextEvent(event);

            if(isForeGroundEvent(event)) {
                packageNameMap.put(event.getTimeStamp(), event.getClassName());
                if(event.getTimeStamp() > lastRunAppTimeStamp) {
                    lastRunAppTimeStamp = event.getTimeStamp();
                }
            }

        }
        return packageNameMap.get(lastRunAppTimeStamp, "").toString();
    }





    private static boolean isForeGroundEvent(UsageEvents.Event event) {

        if(event == null) return false;

        if(BuildConfig.VERSION_CODE >= 29)
            return event.getEventType() == UsageEvents.Event.ACTIVITY_RESUMED;

        return event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND;
    }

}
