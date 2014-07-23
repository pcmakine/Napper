package com.nwodhcout.napper.app;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Pete on 23.7.2014.
 */
public class AlarmReSchedulerService extends IntentService {
    private static final String LOG_TAG = AlarmReSchedulerService.class.getSimpleName();

    public AlarmReSchedulerService() {
        super("AlarmReSchedulerService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Alarm alarm = NapAlarmManager.retrieveAlarm(this);
        if(alarm != null){
            if(alarmMissed(alarm)){
               // NapNotification.buildNapMissedNotification(this);
                NapAlarmManager.cancelAlarm(this);
            }else{
                NapAlarmManager manager = new NapAlarmManager();
                manager.setOnetimeTimerSeconds(this, alarm);
                NapNotification.buildNapSetNotification(this);
            }
        }
        AlarmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private boolean alarmMissed(Alarm alarm){
        long alarmTime = alarm.getStartTime() + alarm.getNapTime()*1000;
        if(alarmTime < System.currentTimeMillis()){
            return true;
        }
        return false;
    }
}
