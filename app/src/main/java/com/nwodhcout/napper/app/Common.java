package com.nwodhcout.napper.app;

import android.app.NotificationManager;
import android.content.Context;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Pete on 9.7.2014.
 */
public class Common {

    public static int msToSec(long ms){
        return (int) (ms/1000);
    }

    public static long secondsToMs(int secs){
        return secs*1000;
    }

    public static String timeToString(int minutes){
        StringBuilder sb = new StringBuilder();
        int hrs = getHours(minutes);

        if(hrs > 0){
            sb.append(getHours(minutes));
            sb.append("h ");
        }
        int mins = getExtraMinutes(minutes);
        if(mins > 0){
            sb.append(getExtraMinutes(minutes));
            sb.append("m");
        }
        return sb.toString();
    }

    /**
     * Returns minutes that are not a part of a full hour
     * @param minutes
     * @return
     */
    private static int getExtraMinutes(int minutes){
        return minutes % 60;
    }

    private static int getHours(int minutes){
        return minutes/60;
    }

/*    public static String getSimpleDate(long msSeconds){
        Format formatter = new SimpleDateFormat("hh:mm:ss a");
        return formatter.format(new Date(msSeconds));
    }*/

/*    public static void debugTime(long msSeconds, String tag, String debugText){
        StringBuilder dbgStrng = new StringBuilder();
        Calendar c = Calendar.getInstance();
        String time = Common.getSimpleDate(msSeconds);
        dbgStrng.append(debugText);
        dbgStrng.append(time);
        Log.d("Alarmtime: ", dbgStrng.toString());
    }*/
}
