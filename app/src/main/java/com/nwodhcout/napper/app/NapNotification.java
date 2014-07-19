package com.nwodhcout.napper.app;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Pete on 19.7.2014.
 */
public class NapNotification {
   private static final int NOTIFICATION_ID = 1;

    public static void cancelNotification(Context ctx){
        if (Context.NOTIFICATION_SERVICE!=null) {
            NotificationManager nMgr = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
            nMgr.cancel(1);
        }
    }

    public static void buildNotification(Context ctx){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ctx);
        mBuilder.setSmallIcon(R.drawable.ic_notification);
        mBuilder.setContentTitle("Nap set");
        mBuilder.setContentText("Napping... sweet dreams");
        mBuilder.setOngoing(true);
       // mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        // notificationID allows you to update the notification later on.
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
