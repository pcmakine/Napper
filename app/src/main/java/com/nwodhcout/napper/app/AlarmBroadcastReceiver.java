package com.nwodhcout.napper.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.nwodhcout.napper.app.activities.AlarmReceiverActivity;

/**
 * Created by Pete on 8.7.2014.
 */
public class AlarmBroadcastReceiver extends BroadcastReceiver {
    final public static String ONE_TIME = "onetime";

    @Override
    public void onReceive(Context context, Intent intent) {
        WakeLocker.acquire(context);
        //You can do the processing here.
        Bundle extras = intent.getExtras();
        StringBuilder msgStr = new StringBuilder();

        if(extras != null && extras.getBoolean(ONE_TIME, Boolean.FALSE)){
            //Make sure this intent has been sent by the one-time timer button.
            msgStr.append("One time Timer : ");
        }
        msgStr.append(Common.getSimpleDate(System.currentTimeMillis()));

      //  Toast.makeText(context, msgStr, Toast.LENGTH_SHORT).show();
        Intent i = new Intent(context, AlarmReceiverActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        //Intent service = new Intent(context, SimpleWakefulService.class);
    }



}

