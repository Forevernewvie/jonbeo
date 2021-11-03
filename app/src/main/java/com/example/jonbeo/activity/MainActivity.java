package com.example.jonbeo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.jonbeo.R;
import com.example.jonbeo.alarm.AlarmSetManager;
import com.example.jonbeo.service.Mydetect;
import com.example.jonbeo.util.CheckPermission;
import com.example.jonbeo.util.Controller;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static Context mContext;
    private ImageButton btn;
    private Button swing,Long;
    public  DevicePolicyManager devicePolicyManager;
    public ComponentName componentName;
    private Intent serviceIntent;
    private AlarmSetManager getAlarmSetManager;
    private CheckPermission checkPermission;
    private static boolean onOff = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getAlarmSetManager = new AlarmSetManager(getApplicationContext());
        checkPermission = new CheckPermission(this);
        devicePolicyManager = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName= new ComponentName(MainActivity.this, Controller.class);
        serviceIntent = new Intent(MainActivity.this, Mydetect.class);
        //---버튼부-----
        btn =  findViewById(R.id.popupBtn);
        Long = findViewById(R.id.Long);
        swing = findViewById(R.id.swing);
        Long.setOnClickListener(this);
        swing.setOnClickListener(this);
        btn.setOnClickListener(this);
        //---버튼부-----

        if(onOff){
           enableButton();
        }else{
            disableButton();
        }


    }


    @Override
    protected void onStart() {
        super.onStart();
        checkAlertPermissionRequest();

        if(!checkPermission.checkPermission()) {
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);

            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "관리자 권한을 실행해야 이용가능합니다.\n 남은 권한도 전부 허용해야 앱 사용가능합니다.");
            startActivity(intent);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.popupBtn:
                Intent intent = new Intent(getApplicationContext(),PopupActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.swing:
                getAlarmSetManager.SetAlarmStartService(R.id.swing);
                disableButton();
                break;

            case R.id.Long:
                getAlarmSetManager.SetAlarmStartService(R.id.Long);
                disableButton();
                break;
        }
    }

    public boolean checkAlertPermissionRequest() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivity(intent);
                return false;
            }
        }
        return true;
    }

    public void disableButton(){
        swing.setEnabled(false);
        Long.setEnabled(false);
        onOff=false;
    }

    public void enableButton(){
        swing.setEnabled(true);
        Long.setEnabled(true);
        onOff=true;
    }

}



