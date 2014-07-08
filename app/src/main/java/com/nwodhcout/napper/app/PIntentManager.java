package com.nwodhcout.napper.app;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Pete on 8.7.2014.
 */
public class PIntentManager {

    public static PendingIntent getPendingIntent(Context context, Intent intent){
        return PendingIntent.getActivity(context,
                12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }
}
