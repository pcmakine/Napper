package com.nwodhcout.napper.app;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Pete on 10.7.2014.
 */
public class NapAlarmManager {
    final public static String ONE_TIME = "onetime";
    private static final String LOG_TAG = AlarmBroadcastReceiver.class.getSimpleName();

    public void setOnetimeTimerSeconds(Context context, Alarm alarm){
        context = context.getApplicationContext();
        android.app.AlarmManager am = (android.app.AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtra(ONE_TIME, Boolean.TRUE);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if(currentapiVersion >= 19){
            am.setExact(android.app.AlarmManager.RTC_WAKEUP, (System.currentTimeMillis() + 1000*alarm.getNapTime()), pi);
        }else{
            am.set(android.app.AlarmManager.RTC_WAKEUP, (System.currentTimeMillis() + 1000*alarm.getNapTime()), pi);
        }

        saveAlarm(context, alarm);
    }


    public static void cancelAlarm(Context context)
    {
        context = context.getApplicationContext();
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        android.app.AlarmManager alarmManager = (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
        removeAlarmFromFile(context);
    }

    public static void saveAlarm(Context context, Alarm alarm){
        SharedPreferences.Editor editor = context.getSharedPreferences("alarmsave", Context.MODE_PRIVATE).edit();
        editor.putBoolean("alarmSet",true);
        editor.putLong("napTime", alarm.getNapTime());
        editor.putLong("startTime", alarm.getStartTime());
        editor.commit();
    }

    public static Alarm retrieveAlarm(Context context){
        SharedPreferences prefs = context.getSharedPreferences("alarmsave", Context.MODE_PRIVATE);
        if(!prefs.getBoolean("alarmSet", false)){
            return null;
        }
        Alarm alarm = new Alarm(prefs.getLong("startTime", -1), prefs.getLong("napTime", -1));
        Log.d(LOG_TAG, "ALARM NAP TIME: " + alarm.getNapTime());
        return alarm;
    }

    public static void removeAlarmFromFile(Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences("alarmsave", Context.MODE_PRIVATE).edit();
        editor.putBoolean("alarmSet",false);
        editor.commit();
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