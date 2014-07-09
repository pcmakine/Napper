package com.nwodhcout.napper.app;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

/**
 * Created by Pete on 9.7.2014.
 */
public class TimerUpdater implements Runnable {
    private long mStartTime;
    private int napTime;
    private TextSwitcher timerText;
    private Handler mHandler;

    public TimerUpdater(int napTime, TextSwitcher timerText, final Context ctx){
        if (timerText.getChildCount() != 2) {
            timerText.removeAllViews();
            timerText.setFactory(new ViewSwitcher.ViewFactory() {
                @Override
                public View makeView() {
                    return new TextView(ctx);
                }
            });
        }
        this.napTime = napTime;
        this.timerText = timerText;
        this.mHandler = new Handler();
    }

    public void setmStartTime(long startTime){
        this.mStartTime = startTime;
    }

    public long getmStartTime(){
        return this.mStartTime;
    }

    public void cancel(){
        mHandler.removeCallbacks(this);
    }

    public int getNapTime(){
        return this.napTime;
    }

    public void run() {
        final long start = mStartTime;
        long elapseTime = System.currentTimeMillis() - start;
        Log.d("elapsedtime", elapseTime / 1000 + " ELAPSED");
        Log.d("elapsedtime", napTime + " NAPTIME");
        int seconds = napTime - (int) (elapseTime/1000);
        Log.d("elapsedtime", seconds + " TIMETOSHOW");

        int minutes = seconds / 60;
        seconds = seconds % 60;

        if (seconds < 10) {
            timerText.setText("" + minutes + ":0" + seconds);
        } else {
            timerText.setText("" + minutes + ":" + seconds);
        }

        // add a delay to adjust for computation time
        long delay = (1000 - (elapseTime%1000));

        if(seconds <= 0 && minutes == 0){
            mHandler.removeCallbacks(this);
        }else{
            mHandler.postDelayed(this, delay);
        }
    }
}