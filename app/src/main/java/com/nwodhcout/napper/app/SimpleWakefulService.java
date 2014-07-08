package com.nwodhcout.napper.app;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Pete on 8.7.2014.
 */
//http://developer.android.com/reference/android/support/v4/content/WakefulBroadcastReceiver.html
public class SimpleWakefulService extends IntentService {
    public static Intent wakeLockIntent;

    public SimpleWakefulService() {
        super("SimpleWakefulService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        wakeLockIntent = intent;
        // At this point SimpleWakefulReceiver is still holding a wake lock
        // for us.  We can do whatever we need to here and then tell it that
        // it can release the wakelock.  This sample just does some slow work,
        // but more complicated implementations could take their own wake
        // lock here before releasing the receiver's.
        //
        // Note that when using this approach you should be aware that if your
        // service gets killed and restarted while in the middle of such work
        // (so the Intent gets re-delivered to perform the work again), it will
        // at that point no longer be holding a wake lock since we are depending
        // on SimpleWakefulReceiver to that for us.  If this is a concern, you can
        // acquire a separate wake lock here.
        Intent i = new Intent(this, AlarmReceiverActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        Log.d("Simplewakefulservice", "starting activity");
    }

    public static void releaseWakeLock(){
       // AlarmManagerBroadcastReceiver.completeWakefulIntent(wakeLockIntent);
    }
}
