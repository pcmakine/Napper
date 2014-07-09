package com.nwodhcout.napper.app;

import android.util.Log;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Pete on 9.7.2014.
 */
public class Common {
    public static String getSimpleDate(long msSeconds){
        Format formatter = new SimpleDateFormat("hh:mm:ss a");
        return formatter.format(new Date(msSeconds));
    }

    public static void debugTime(long msSeconds, String tag, String debugText){
        StringBuilder dbgStrng = new StringBuilder();
        Calendar c = Calendar.getInstance();
        String time = Common.getSimpleDate(msSeconds);
        dbgStrng.append(debugText);
        dbgStrng.append(time);
        Log.d("Alarmtime: ", dbgStrng.toString());
    }
}
