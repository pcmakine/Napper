package com.nwodhcout.napper.app;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by Pete on 10.7.2014.
 */
public class AlarmManager {
    final public static String ONE_TIME = "onetime";

    public void setOnetimeTimerSeconds(Context context, int seconds, long startTime){
        android.app.AlarmManager am = (android.app.AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtra(ONE_TIME, Boolean.TRUE);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        am.set(android.app.AlarmManager.RTC_WAKEUP, (System.currentTimeMillis() + 1000*seconds), pi);
     //   saveAlarm(context);
    }


    public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        android.app.AlarmManager alarmManager = (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public void saveAlarm(Context context, int seconds, long startTime){
        Activity activity = (Activity) context;
        long endTime = startTime + (seconds*1000);
        SharedPreferences.Editor editor = activity.getPreferences(activity.MODE_PRIVATE).edit();
        editor.putBoolean("alarmSet",true);
        editor.putLong("endTime", endTime);
        editor.apply();
    }

    public long retrieveAlarm(Context context){
        Activity activity = (Activity) context;
        SharedPreferences prefs = activity.getPreferences(activity.MODE_PRIVATE);
        if(!prefs.getBoolean("alarmSet", false)){
            return -1;
        }
        return 0;
    }

}
/**
 *     public void SetAlarm(Context context)
 {
 AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
 Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
 intent.putExtra(ONE_TIME, Boolean.FALSE);
 PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
 //After after 5 seconds
 am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 5 , pi);
 }
 */