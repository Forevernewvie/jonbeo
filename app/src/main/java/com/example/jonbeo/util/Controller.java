package com.example.jonbeo.util;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Controller extends DeviceAdminReceiver {
    @Override
    public void onEnabled(Context context, Intent intent) {
        Toast.makeText(context,"확인",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisabled(Context context, Intent intent) {

    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {

        return super.onDisableRequested(context, intent);
    }
}
