package com.nwodhcout.napper.app.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.nwodhcout.napper.app.Alarm;
import com.nwodhcout.napper.app.AlarmManager;
import com.nwodhcout.napper.app.Common;
import com.nwodhcout.napper.app.MySeekBar;
import com.nwodhcout.napper.app.R;

public class MainActivity extends Activity {
    private static final int DEFAULTNAPTIME = 20; //min
    private static final int ACTIVATEDCOLOR = Color.rgb(134, 76, 158);
    private static final int DEACTIVATEDCOLOR = Color.WHITE;
    private int napTime;
    private Button customTime;
    private Button activatedButton;
    private MySeekBar seekBar;
    private Alarm alarm;
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.napTime = DEFAULTNAPTIME;
        setContentView(R.layout.activity_main);
        this.customTime = (Button) findViewById(R.id.customMin);
        this.seekBar = (MySeekBar) findViewById(R.id.seekBar);
        this.alarmManager = new AlarmManager();

        Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.twinkle);
        Button btn = (Button) findViewById(R.id.alarmNotification);
        btn.startAnimation(myFadeInAnimation);
        setSeekBarListener();
        activateButton((Button) findViewById(R.id.twentyMin));
    }

    public void showOngoingAlarm(View view){
        Intent intent = new Intent(this, NapFeedbackActivity.class);
        int elapsedSecs = (int) (Common.msToSec(System.currentTimeMillis() - alarm.getStartTime()));
        int napLeft = (int) alarm.getNapTime() - elapsedSecs;
        intent.putExtra("NAP_TIME", napLeft);
        intent.putExtra("NAP_START", System.currentTimeMillis());
        startActivity(intent);
    }

    public void nap(View view){
        Intent intent = new Intent(this, NapFeedbackActivity.class);
        if(alarm != null){
            alarmManager.cancelAlarm(this);
        }
        long startTime = System.currentTimeMillis();
        int napSecs = napTime;
        Alarm al = new Alarm(startTime, napSecs);
        setAlarm(al);
        intent.putExtra("NAP_TIME", napSecs);
        intent.putExtra("NAP_START", startTime);
        startActivity(intent);
    }


    private void setAlarm(Alarm al){
        Context context = this.getApplicationContext();
        if(alarmManager != null){
            alarmManager.setOnetimeTimerSeconds(context, al);
        }else{
            Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
        }
    }

    public void changeNapTime(View view){
        Button btn = (Button) findViewById(view.getId());
        activateButton(btn);
        switch(view.getId()){
            case R.id.twentyMin:
                this.napTime = 20;
                seekBar.setVisibility(View.INVISIBLE);
                setDefaultTextToCustomButton();
                break;
            case R.id.sixtyMin:
                this.napTime = 60;
                seekBar.setVisibility(View.INVISIBLE);
                setDefaultTextToCustomButton();
                break;
            case R.id.customMin:
                this.napTime = seekBar.getProgress();
                customTime.setText(napTime + "");
                seekBar.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void setDefaultTextToCustomButton(){
        customTime.setText(R.string.custom);
    }

    private void activateButton(Button btn){
        btn.setTextColor(ACTIVATEDCOLOR);
        if(activatedButton != null && activatedButton != btn){
            activatedButton.setTextColor(DEACTIVATEDCOLOR);
        }
        this.activatedButton = btn;
    }

    private void setSeekBarListener(){
        seekBar.getProgressDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        seekBar.getSeekBarThumb().setColorFilter(ACTIVATEDCOLOR, PorterDuff.Mode.SRC_IN);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                napTime = seekBar.getProgress();
                customTime.setText(napTime + "");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        alarm = AlarmManager.retrieveAlarm(this);
        Button btn = (Button) findViewById(R.id.alarmNotification);
        if(alarm != null){
            btn.setVisibility(View.VISIBLE);
            btn.setText(R.string.alarm_notification);
        }else{
            btn.setVisibility(View.INVISIBLE);
            btn.setText("");
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
