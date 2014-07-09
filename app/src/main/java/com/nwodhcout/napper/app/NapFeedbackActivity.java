package com.nwodhcout.napper.app;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class NapFeedbackActivity extends ActionBarActivity {
    private AlarmManagerBroadcastReceiver alarm;
    private static final int NAPTIME = 15; //naptime in seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nap_feedback);
        alarm = new AlarmManagerBroadcastReceiver();
        setFeedbackText();
        setAlarm();
    }


    private void setFeedbackText(){
        TextView text = (TextView) findViewById(R.id.feedbackText);
        Calendar c = Calendar.getInstance();
        long timeInMillis = c.getTimeInMillis();
        timeInMillis = timeInMillis + 20*60*1000;
        Date date = new Date(timeInMillis);
        text.setText(text.getText() + (date + ""));
    }

    private void setAlarm(){
        Context context = this.getApplicationContext();
        if(alarm != null){
            alarm.setOnetimeTimerSeconds(context, NAPTIME);
            Calendar c = Calendar.getInstance();
            Common.debugTime(c.getTimeInMillis() + NAPTIME*1000, "Alarmtime: ", "alarm set to: ");
        }else{
            Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelAlarm(View view){
        Context context = this.getApplicationContext();
        if(alarm != null){
            alarm.cancelAlarm(context);
        }else{
            Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
        }
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
}
