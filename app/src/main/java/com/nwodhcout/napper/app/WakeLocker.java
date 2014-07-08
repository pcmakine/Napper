package com.nwodhcout.napper.app;

import android.content.Context;
import android.os.PowerManager;
import android.util.Log;

/**
 * Created by Pete on 8.7.2014.
 */
public abstract class WakeLocker {
    private static PowerManager.WakeLock wakeLock;

    public static void acquire(Context ctx) {
        if (wakeLock != null) wakeLock.release();

        PowerManager pm = (PowerManager) ctx.getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK |
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.ON_AFTER_RELEASE, "taggg");
        wakeLock.acquire();
        Log.d("wakelocker", "wakelock acquired!");
    }

    public static void release() {
        if (wakeLock != null) wakeLock.release(); wakeLock = null;
    }
}
