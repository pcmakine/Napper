package com.nwodhcout.napper.app;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nwodhcout.napper.app.animatedtimer.TimerUpdater;

import java.util.Calendar;
import java.util.Date;

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
        }else{
            Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
        }
        this.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nap_feedback, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        updater.cancel();
        if(alarm != null) {     //cancel the alarm if back button is pressed
            /**
             * todo don't cancel the alarm, instead show an indicator in
             * the main screen showing that the alarm is on
             */
            alarm.cancelAlarm(this.getApplicationContext());
            Toast.makeText(this, "Alarm cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}
