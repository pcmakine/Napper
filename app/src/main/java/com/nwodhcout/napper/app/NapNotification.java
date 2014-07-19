package com.nwodhcout.napper.app;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.nwodhcout.napper.app.activities.NapCounterActivity;

/**
 * Created by Pete on 19.7.2014.
 */
public class NapNotification {
    private static final int NOTIFICATION_ID = 1;
    private static final int LED_ON_MS = 100;
    private static final int LED_OFF_MS = 1000;
    private static final int LED_COLOR = 0xff800080;

    public static void cancelNotification(Context ctx){
        if (Context.NOTIFICATION_SERVICE!=null) {
            NotificationManager nMgr = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
            nMgr.cancel(NOTIFICATION_ID);
        }
    }

    public static void buildNotification(Context ctx){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ctx);
        mBuilder.setSmallIcon(R.drawable.ic_notification);
        mBuilder.setContentTitle("Nap set");
        mBuilder.setContentText("Napping... sweet dreams");
        mBuilder.setOngoing(true);
        mBuilder.setLights(LED_COLOR, LED_ON_MS, LED_OFF_MS);

        addAlarmActivityIntent(mBuilder, ctx);

        NotificationManager mNotificationManager =
                (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        // notificationID allows you to update the notification later on.
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    private static void addAlarmActivityIntent(NotificationCompat.Builder mBuilder, Context ctx){

        Intent resultIntent = new Intent(ctx, NapCounterActivity.class);
        Alarm alarm = NapAlarmManager.retrieveAlarm(ctx);

        int elapsedSecs = (Common.msToSec(System.currentTimeMillis() - alarm.getStartTime()));
        int napLeft = (int) alarm.getNapTime() - elapsedSecs;
        if(napLeft > 0){
            resultIntent.putExtra("NAP_TIME", napLeft);
        }else{
            resultIntent.putExtra("NAP_TIME", 0);
        }
        resultIntent.putExtra("NAP_START", System.currentTimeMillis());

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ctx);
        stackBuilder.addParentStack(NapCounterActivity.class);

// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);
    }
}

