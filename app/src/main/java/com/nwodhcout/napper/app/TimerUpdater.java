package com.nwodhcout.napper.app;

import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Pete on 9.7.2014.
 */
public class TimerUpdater implements Runnable {
    private long mStartTime;
    private int napTime;
    private TextView timerText;
    private Handler mHandler;

    public TimerUpdater(int napTime, TextView timerText){
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
        int timeToShow = napTime - (int) (elapseTime/1000);
        Log.d("elapsedtime", timeToShow + " TIMETOSHOW");

        timerText.setText(Common.getSimpleDate(timeToShow));

        int minutes = timeToShow / 60;
        timeToShow     = timeToShow % 60;

        if (timeToShow < 10) {
            timerText.setText("" + minutes + ":0" + timeToShow);
        } else {
            timerText.setText("" + minutes + ":" + timeToShow);
        }

        // add a delay to adjust for computation time
        long delay = (1000 - (elapseTime%1000));

        if(timeToShow <= 0){
            mHandler.removeCallbacks(this);
        }else{
            mHandler.postDelayed(this, delay);
        }
    }
}