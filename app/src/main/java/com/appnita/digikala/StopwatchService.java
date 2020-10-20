package com.appnita.digikala;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

/**
 * Created by ramil2319 on 4/4/2017.
 */

public class StopwatchService extends Service {

    private final static String TAG = "StopwatchService";
    public static final String STOPWATCH_BR = "com.stopwatch.stopwatch";
    Intent bi = new Intent(STOPWATCH_BR);

    private Handler mHandler = new Handler();
    private long startTime;
    private long elapsedTime;
    private final int REFRESH_RATE = 100;
    private String hours,minutes,seconds,milliseconds;
    private long secs,mins,hrs;
    private boolean stopped = false;
    private long pauseTime;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "Starting timer...");
        pauseTime = (long) getSharedPreferences("time",MODE_PRIVATE).getFloat("time",0);

        if (stopped) {
            startTime = System.currentTimeMillis() - elapsedTime;
        }
        else {
            startTime = System.currentTimeMillis();
        }
        mHandler.removeCallbacks(startTimer);
        mHandler.postDelayed(startTimer, 0);
    }

    @Override
    public void onDestroy() {
        mHandler.removeCallbacks(startTimer);
        stopped = true;
        Log.i(TAG, "destroy timer...");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        addNotification();
        return START_STICKY;
    }

    private void addNotification() {
        createNotificationChannel();
        Notification notification = new NotificationCompat.Builder(this,"101")
                .setContentTitle("در حال مطالعه")
                .setContentText("فقط درس میخونم")
                .setSmallIcon(R.drawable.ic_notification)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        startForeground(101,notification);
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "music";
            String describtion = "play music";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("101",name,importance);
            channel.setDescription(describtion);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void updateTimer (float time){
        secs = (long)(time/1000);
        mins = (long)((time/1000)/60);
        hrs = (long)(((time/1000)/60)/60);

        /* Convert the seconds to String
         * and format to ensure it has
         * a leading zero when required
         */
        secs = secs % 60;
        seconds=String.valueOf(secs);
        if(secs == 0){
            seconds = "00";
        }
        if(secs <10 && secs > 0){
            seconds = "0"+seconds;
        }

        /* Convert the minutes to String and format the String */
        mins = mins % 60;
        minutes=String.valueOf(mins);
        if(mins == 0){
            minutes = "00";
        }
        if(mins <10 && mins > 0){
            minutes = "0"+minutes;
        }

        /* Convert the hours to String and format the String */
        hours=String.valueOf(hrs);
        if(hrs == 0){
            hours = "00";
        }
        if(hrs <10 && hrs > 0){
            hours = "0"+hours;
        }

        /* Although we are not using milliseconds on the timer in this example
         * I included the code in the event that you wanted to include it on your own
         */
        milliseconds = String.valueOf((long)time);
        if(milliseconds.length()==2){
            milliseconds = "0"+milliseconds;
        }
        if(milliseconds.length()<=1){
            milliseconds = "00";
        }

        milliseconds = milliseconds.substring(milliseconds.length()-2, milliseconds.length()-1);

        bi.putExtra("hours", hours);
        bi.putExtra("minutes", minutes);
        bi.putExtra("seconds", seconds);
        bi.putExtra("milliseconds", milliseconds);
        bi.putExtra("time" , time);
        sendBroadcast(bi);

    }//end Update Timer

    private Runnable startTimer = new Runnable() {
        @Override
        public void run() {
            elapsedTime = System.currentTimeMillis() - startTime + pauseTime;
            updateTimer(elapsedTime);
            mHandler.postDelayed(this, REFRESH_RATE);
        }
    };
}
