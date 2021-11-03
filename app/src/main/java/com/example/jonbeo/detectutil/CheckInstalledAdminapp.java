package com.example.jonbeo.detectutil;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.List;

public class CheckInstalledAdminapp {


    private Context mContext;
    public CheckInstalledAdminapp(Context context) {
        this.mContext = context;
    }

    public boolean getInstalledTargetApp() {


        final PackageManager pm =   mContext.getPackageManager();

        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            if (packageInfo.packageName.equals("com.example.jonbeo")) {
                return true;
            }

        }

        return false;
    }
}
