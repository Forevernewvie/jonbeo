package com.example.jonbeo;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Build;

import java.util.ArrayList;

public class DetectaBackgroundadminApp {

    private Context mContetx = null;

    public DetectaBackgroundadminApp(Context context){
        this.mContetx=context;
    }


    public ArrayList<String> getBackGroundPackageNameByUsageStats() {
        ArrayList<String> list = new ArrayList<String>();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager mUsageStatsManager = (UsageStatsManager) mContetx.getSystemService(Context.USAGE_STATS_SERVICE);
            final long INTERVAL = 1000000;
            final long end = System.currentTimeMillis();
            final long begin = end - INTERVAL;
            final UsageEvents usageEvents = mUsageStatsManager.queryEvents(begin, end);
            while (usageEvents.hasNextEvent()) {
                UsageEvents.Event event = new UsageEvents.Event();
                usageEvents.getNextEvent(event);
                if (event.getEventType() == UsageEvents.Event.MOVE_TO_BACKGROUND) {
                    list.add(event.getPackageName());
                }
            }
        }


        return list;

    }


}
