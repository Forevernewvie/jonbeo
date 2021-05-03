package com.example.jonbeo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.List;

public class checkInstalledAdminapp {


    private Context mContext;
    public checkInstalledAdminapp (Context context) {
        this.mContext = context;
    }

    public boolean getInstalledTargetApp() {


        final PackageManager pm =   mContext.getPackageManager();
        //get a list of installed apps.
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            if (packageInfo.packageName.equals("com.example.jonbeo")) {
                return true;
            }

        }

        return false;
    }
}
