package com.nwodhcout.napper.app.activities;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.nwodhcout.napper.app.NapAlarmManager;
import com.nwodhcout.napper.app.R;
import com.nwodhcout.napper.app.uicomponents.AnimatedCountDown;

public class NapCounterActivity extends ActionBarActivity {
    private NapAlarmManager alarm;
    private int napTime; //naptime in seconds
    private AnimatedCountDown timer;

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
        this.alarm = new NapAlarmManager();
        this.timer = new AnimatedCountDown(napTime, this);
        timer.start(startTime);

        startTimerUpdates();
    }

    @Override
    public void onStart(){
        super.onStart();
        //we don't want the user to see this screen if there is no alarm to count down for
        if(NapAlarmManager.retrieveAlarm(this) == null){
            finish();
        }
    }

    private void startTimerUpdates(){
        timer.cancel();
        timer.run();
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
        timer.cancel();
    }
}
