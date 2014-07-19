package com.nwodhcout.napper.app.uicomponents;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.nwodhcout.napper.app.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Pete on 9.7.2014.
 */
public class AnimatedCountDown implements Runnable {
    private long mStartTime;
    private int napTime;
    private Handler mHandler;
    private ArrayList<TextSwitcher> switchers;
    private ArrayList<Integer> currentVals;

    public AnimatedCountDown(int napTime, Activity activity){
        this.napTime = napTime;
        switchers = new ArrayList<TextSwitcher>();
        currentVals = new ArrayList<Integer>();
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

    public void start(long startTime){
        this.mStartTime = startTime;
        int secsLeft = timeLeft();

        int hours = secsLeft / 3600;
        int minutes = (secsLeft - (hours * 3600)) / 60;
        int seconds = secsLeft - (hours* 3600 + minutes * 60);

        updateSwitchers(Arrays.asList(secondDigit(seconds), firstDigit(seconds),
                secondDigit(minutes), firstDigit(minutes),
                secondDigit(hours), firstDigit(hours)), true);
    }

    private int timeLeft(){
        long elapseTime = System.currentTimeMillis() - mStartTime;
        return napTime - (int) (elapseTime/1000);
    }

    private int firstDigit(int val){
        return (val/10);
    }

    private int secondDigit(int val){
        return val%10;
    }

    private void prepareAnimation(final Context ctx){
        for (TextSwitcher switcher : switchers) {
            currentVals.add(-10);
            if (switcher.getChildCount() != 2) {
                switcher.removeAllViews();
                switcher.setFactory(new ViewSwitcher.ViewFactory() {
                    @Override
                    public View makeView() {
                        TextView myText = new TextView(ctx);
                        myText.setTextAppearance(ctx, R.style.smallText);
                        return myText;
                    }
                });
            }
        }

    }

    public void cancel(){
        mHandler.removeCallbacks(this);
    }

    public void run() {
        long elapseTime = System.currentTimeMillis() - mStartTime;
        int totalSeconds = napTime - (int) (elapseTime/1000);

        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds - (hours * 3600)) / 60;
        int seconds = totalSeconds - (hours* 3600 + minutes * 60);

        updateSwitchers(Arrays.asList(secondDigit(seconds), firstDigit(seconds),
                secondDigit(minutes), firstDigit(minutes),
                secondDigit(hours), firstDigit(hours)), false);

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