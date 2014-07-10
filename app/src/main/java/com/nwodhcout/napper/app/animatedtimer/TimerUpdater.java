package com.nwodhcout.napper.app.animatedtimer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.nwodhcout.napper.app.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Pete on 9.7.2014.
 */
public class TimerUpdater implements Runnable {
    private long mStartTime;
    private int napTime;
    private Handler mHandler;
    private ArrayList<TextSwitcher> switchers;
    private ArrayList<Integer> currentVals;

    public TimerUpdater(int napTime, Activity activity){
        this.napTime = napTime;
        switchers = new ArrayList();
        currentVals = new ArrayList();
        setUpSwitchers(activity);
        this.mHandler = new Handler();

    }

    private void setUpSwitchers(Activity activity){
        switchers.add((TextSwitcher) activity.findViewById(R.id.secondsOnes));
        switchers.add((TextSwitcher) activity.findViewById(R.id.secondsTens));
        switchers.add((TextSwitcher) activity.findViewById(R.id.minutesOnes));
        switchers.add((TextSwitcher) activity.findViewById(R.id.minutesTens));
        switchers.add((TextSwitcher) activity.findViewById(R.id.hoursOnes));
        switchers.add((TextSwitcher) activity.findViewById(R.id.hoursTens));
        this.prepareAnimation(activity);


    }

    public void initSwitchers(){
        final long start = mStartTime;
        long elapseTime = System.currentTimeMillis() - start;
        Log.d("elapsedtime", elapseTime / 1000 + " ELAPSED");
        Log.d("elapsedtime", napTime + " NAPTIME");
        int totalSeconds = napTime - (int) (elapseTime/1000);
        Log.d("elapsedtime", totalSeconds + " TIMETOSHOW");

        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds - (hours * 3600)) / 60;
        int seconds = totalSeconds - (hours* 3600 + minutes * 60);

        int onesSecs = seconds%10;
        int tensSecs = (int) seconds/10;
        int onesMins = minutes%10;
        int tensMins = (int) minutes/10;
        int onesHours = hours%10;
        int tensHours = (int) hours/10;
        updateSwitchers(Arrays.asList(onesSecs, tensSecs, onesMins, tensMins, onesHours, tensHours), true);
    }

    private void prepareAnimation(final Context ctx){
        for(int i = 0; i< switchers.size(); i++){
            TextSwitcher switcher = switchers.get(i);
            currentVals.add(-10);
            if (switcher.getChildCount() != 2) {
                switcher.removeAllViews();
                switcher.setFactory(new ViewSwitcher.ViewFactory() {
                    @Override
                    public View makeView() {
                        TextView myText = new TextView(ctx);
                        myText.setTextSize(25);
                        myText.setTextColor(Color.rgb(255, 255, 255));
                        return myText;
                    }
                });
            }
        }

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
        int totalSeconds = napTime - (int) (elapseTime/1000);
        Log.d("elapsedtime", totalSeconds + " TIMETOSHOW");

        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds - (hours * 3600)) / 60;
        int seconds = totalSeconds - (hours* 3600 + minutes * 60);

        int onesSecs = seconds%10;
        int tensSecs = (int) seconds/10;
        int onesMins = minutes%10;
        int tensMins = (int) minutes/10;
        int onesHours = hours%10;
        int tensHours = (int) hours/10;

        Log.d("onesTens: ", "totalseconds: " + totalSeconds);
        Log.d("onestens: ", "onesHours: " + onesHours);
        Log.d("onestens: ", "tensHours: " + onesHours);
        Log.d("onestens: ", "onesSecs: " + onesSecs);
        Log.d("onestens: ", "tensSecs: " + tensSecs);


        updateSwitchers(Arrays.asList(onesSecs, tensSecs, onesMins, tensMins, onesHours, tensHours), false);
        // updateMins(minutes);
        // updateSecs(seconds);

        // add a delay to adjust for computation time
        long delay = (1000 - (elapseTime%1000));

        if(totalSeconds <= 0){
            mHandler.removeCallbacks(this);
        }else{
            mHandler.postDelayed(this, delay);
        }
    }

    private void updateSwitchers(List<Integer> newVals, boolean noDelay){
        for (int i = 0; i< switchers.size(); i++){
            updateSwitcher(switchers.get(i), currentVals.get(i), newVals.get(i), noDelay);
            currentVals.set(i, newVals.get(i));
        }
    }

    private void updateSwitcher(TextSwitcher switcher, int currentVal, int val, boolean noDelay){
        if(noDelay){
            switcher.setCurrentText("" + val);
        }
        else if(currentVal != val){
            switcher.setText("" + val);
        }
    }

}