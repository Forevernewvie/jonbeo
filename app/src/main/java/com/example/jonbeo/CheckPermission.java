package com.example.jonbeo;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

public class CheckPermission {

    private  Context mContext;
    private int mode;
    public CheckPermission(Context context){
        mContext = context;
    }



    public boolean checkPermission(){

        boolean granted;

        AppOpsManager appOps = (AppOpsManager) mContext.getApplicationContext()
                .getSystemService(Context.APP_OPS_SERVICE);

        if (Build.VERSION_CODES.Q>Build.VERSION.SDK_INT){

             mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                    android.os.Process.myUid(), mContext.getApplicationContext().getPackageName());
        }
        else{
            mode = appOps.unsafeCheckOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                    android.os.Process.myUid(), mContext.getApplicationContext().getPackageName());
        }


        if (mode == AppOpsManager.MODE_DEFAULT) {
            granted = (mContext.getApplicationContext().checkCallingOrSelfPermission(
                    android.Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED);
        }
        else {
            granted = (mode == AppOpsManager.MODE_ALLOWED);
        }

        return granted;
    }


}
