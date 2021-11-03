package com.example.jonbeo.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import com.example.jonbeo.detectutil.CheckInstalledAdminapp;
import com.example.jonbeo.util.CheckPermission;
import com.example.jonbeo.detectutil.DetectDeleteAdminApp;
import com.example.jonbeo.detectutil.DetectForeGroundTargetApp;
import com.example.jonbeo.detectutil.DetectaBackgroundadminApp;
import com.example.jonbeo.R;

import java.util.ArrayList;

public class Mydetect extends Service {
    private static final String TAG = Mydetect.class.getSimpleName();

    public Mydetect() {
    }

    CheckInstalledAdminapp CIA;
    CheckPermission checkPermission;
    DetectaBackgroundadminApp detectaBackgroundadminApp;
    DetectForeGroundTargetApp detectForeGroundTargetApp;
    DetectDeleteAdminApp deelteAdminapp;
    ArrayList<String> BackgroundApplist = new ArrayList<String>();
    DetectTargetApp detectTargetApp; //쓰레드
    ForegroundAdminApp foregroundAdminApp; //쓰레드
    NotificationManager manager;
    private String className;
    boolean chk = false;
    boolean installed = false;
    private static boolean _run = true;
    boolean backRun = false;
    Handler handler = new Handler(Looper.getMainLooper());
    private final String ch_id = "service";
    private final String ch_name = "serviceRun";

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        manager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        detectaBackgroundadminApp = new DetectaBackgroundadminApp(this);
        checkPermission = new CheckPermission(this);
        CIA = new CheckInstalledAdminapp(this);
        detectForeGroundTargetApp = new DetectForeGroundTargetApp();
        deelteAdminapp = new DetectDeleteAdminApp();
        //쓰레드
        detectTargetApp = new DetectTargetApp();
        foregroundAdminApp = new ForegroundAdminApp();
        //쓰레드
        _run=true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        _run = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                startForegroundService();
                detectTargetApp.start();
                foregroundAdminApp.start();

                Toast.makeText(getApplicationContext(),"감시시작 & 알람시작",Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getApplicationContext(),"오레오 미만 버전 이용 불가",Toast.LENGTH_SHORT).show();
            }

         return START_NOT_STICKY;
        }



    private void startForegroundService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    ch_id,ch_name, NotificationManager.IMPORTANCE_DEFAULT
            );

            manager.createNotificationChannel(channel);


                Notification notification =
                        new Notification.Builder(this, ch_id)
                                .setContentTitle("존-버")
                                .setContentText("감시중")
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .build();

                startForeground(1, notification);
        }

    }



    public class ForegroundAdminApp extends Thread{

        public ForegroundAdminApp(){
            BackgroundApplist  = detectaBackgroundadminApp.getBackGroundPackageNameByUsageStats();
        }


        public void run(){

            while(_run){

                installed = CIA.getInstalledTargetApp();
                backRun = BackgroundApplist.contains("com.example.jonbeo");
                className = deelteAdminapp.getKlassName(getApplicationContext());

                if ( backRun && installed ) {chk=true;}
                else{   chk=false; }


                if (chk){
                    if(className.equals("com.android.settings.DeviceAdminAdd")){
                        gotoHome();

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),"버텨야 합니다",Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                }

                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    public class DetectTargetApp extends Thread{

        public void run(){
            while(_run){

                if(!checkPermission.checkPermission()) continue;

                if  (detectForeGroundTargetApp.getPackageName(getApplicationContext()).equals("com.dunamu.exchange")
                    ||detectForeGroundTargetApp.getPackageName(getApplicationContext()).equals("com.btckorea.bithumb")
                ){

                    gotoHome();
                    handler.post(new Runnable() {
                        @Override
                        public void run() { Toast.makeText(getApplicationContext(),"버텨야 합니다",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void gotoHome() {

        if ( Build.VERSION_CODES.R>=Build.VERSION.SDK_INT) {

            Intent homeIntent;
            homeIntent =createHomeIntent();
            startActivity(homeIntent);
        }
    }


    public Intent createHomeIntent(){
        Intent temp = new Intent();
        temp.setAction("android.intent.action.MAIN");
        temp.addCategory("android.intent.category.HOME");
        temp.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                | Intent.FLAG_ACTIVITY_FORWARD_RESULT
                | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        return temp;
    }


}
