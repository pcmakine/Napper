package com.nwodhcout.napper.app.activities;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.nwodhcout.napper.app.AlarmManager;
import com.nwodhcout.napper.app.R;
import com.nwodhcout.napper.app.animatedtimer.TimerUpdater;

public class NapFeedbackActivity extends ActionBarActivity {
    private AlarmManager alarm;
    private int napTime; //naptime in seconds
    private TimerUpdater updater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nap_feedback);
        Bundle extras = getIntent().getExtras();
        long startTime = -1;

        if (extras != null) {
            this.napTime = extras.getInt("NAP_TIME");
            startTime = extras.getLong("NAP_START");
        }
        this.alarm = new AlarmManager();
        this.updater = new TimerUpdater(napTime, this);
        updater.setmStartTime(startTime);
        updater.initSwitchers();

        startTimerUpdates();
    }

    private void startTimerUpdates(){
        updater.cancel();
        updater.run();
    }


    public void cancelAlarm(View view){
        Context context = this.getApplicationContext();
        if(alarm != null){
            alarm.cancelAlarm(context);
            Toast.makeText(context, R.string.alarm_cancelled, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
        }
        this.onBackPressed();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        updater.cancel();
    }
}
