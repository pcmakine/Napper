package com.nwodhcout.napper.app.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.nwodhcout.napper.app.Alarm;
import com.nwodhcout.napper.app.NapAlarmManager;
import com.nwodhcout.napper.app.Common;
import com.nwodhcout.napper.app.uicomponents.MySeekBar;
import com.nwodhcout.napper.app.R;

public class SetNapActivity extends Activity {
    private static final int DEFAULTNAPTIME = 20; //min
    private static final int MAX_NAP_TIME  = 120; //minutes
    private static final int MIN_NAP_TIME = 2;
    private int napTime;
    private Button customTime;
    private MySeekBar seekBar;
    private Alarm alarm;
    private NapAlarmManager napAlarmManager;
    private ButtonColorManager btnManager;
    private TextView napTimeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_nap);


        //   this.napTime = DEFAULTNAPTIME;
        requestAds();
        this.customTime = (Button) findViewById(R.id.customMin);
        this.seekBar = (MySeekBar) findViewById(R.id.seekBar);
        this.napTimeText = (TextView) findViewById(R.id.napTimeText);
        this.napAlarmManager = new NapAlarmManager();
        Button[] buttons = new Button[3];
        buttons[0] = (Button) findViewById(R.id.twentyMin);
        buttons[1] = (Button) findViewById(R.id.sixtyMin);
        buttons[2] = customTime;
        btnManager = new ButtonColorManager(buttons);

        setBlinkingTextAnimation();

        prepareSeekBar();
    }

    private void requestAds(){
        AdView adView = (AdView) this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().
                addTestDevice("CAA8F5A6A3765BFB3C23E402831649C9").
                build();
        adView.loadAd(adRequest);
    }

    private void checkForGooglePlayServicesAvailability(){

    }

    private void setBlinkingTextAnimation(){
        Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.twinkle);
        Button btn = (Button) findViewById(R.id.alarmNotification);
        btn.startAnimation(myFadeInAnimation);
    }

    private void prepareSeekBar(){
        seekBar.setMax(MAX_NAP_TIME - MIN_NAP_TIME);
        seekBar.setProgress(DEFAULTNAPTIME - MIN_NAP_TIME);
        seekBar.setSecondaryProgress(seekBar.getMax());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int napTime = seekBar.getProgress();
                setNapTimeAndCustomText(napTime);
                setNapTimeText();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void activateButton(Button btn){
        btnManager.activateButton(btn);
        if(btn != findViewById(R.id.customMin)){
            setDefaultTextToCustomButton();
        }
    }

    private void setDefaultTextToCustomButton(){
        customTime.setText(R.string.custom);
    }


    public void setNapTimeAndCustomText(int time){
        this.napTime = time + MIN_NAP_TIME;
       // customTime.setText(napTime + "");
        customTime.setText(Common.timeToString(napTime));
}

    //Called when the blinking text is tapped
    public void showOngoingAlarm(View view){
        Intent intent = new Intent(this, NapCounterActivity.class);
        int elapsedSecs = (Common.msToSec(System.currentTimeMillis() - alarm.getStartTime()));
        int napLeft = (int) alarm.getNapTime() - elapsedSecs;
        if(napLeft > 0){
            intent.putExtra("NAP_TIME", napLeft);
        }else{
            intent.putExtra("NAP_TIME", 0);
        }
        intent.putExtra("NAP_START", System.currentTimeMillis());
        startActivity(intent);
    }

    //Called when the 20min, 60min and custom buttons are pressed
    public void changeNapTime(View view){
        Button btn = (Button) findViewById(view.getId());
        activateButton(btn);
        switch(view.getId()){
            case R.id.twentyMin:
                this.napTime = 20;
                seekBar.setVisibility(View.INVISIBLE);
                break;
            case R.id.sixtyMin:
                this.napTime = 60;
                seekBar.setVisibility(View.INVISIBLE);
                break;
            case R.id.customMin:
                setNapTimeAndCustomText(seekBar.getProgress());
                seekBar.setVisibility(View.VISIBLE);
                break;
        }
        setNapTimeText();
    }

    private void setNapTimeText(){
        this.napTimeText.setText("Set nap for " + Common.timeToString(napTime));
    }

    //called when the big nap button is tapped
    public void nap(View view){
        Intent intent = new Intent(this, NapCounterActivity.class);
        if(alarm != null){
            napAlarmManager.cancelAlarm(this);
        }
        long startTime = System.currentTimeMillis();
        int napSecs = napTime*60;
        Alarm al = new Alarm(startTime, napSecs);
        setAlarm(al);
        intent.putExtra("NAP_TIME", napSecs);
        intent.putExtra("NAP_START", startTime);
        startActivity(intent);
    }

    private void setAlarm(Alarm al){
        Context context = this.getApplicationContext();
        if(napAlarmManager != null){
            napAlarmManager.setOnetimeTimerSeconds(context, al);
        }else{
            Toast.makeText(context, "Alarm is null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        alarm = NapAlarmManager.retrieveAlarm(this);
        Button btn = (Button) findViewById(R.id.alarmNotification);
        if(alarm != null){
            btn.setVisibility(View.VISIBLE);
            btn.setText(R.string.alarm_notification);
        }else{
            btn.setVisibility(View.INVISIBLE);
            btn.setText("");
        }
        btnManager.resumeButtonState();
        resumeNapTime();
    }

    private void resumeNapTime(){
        Button btn = btnManager.getActivatedButton();
        btn.performClick();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
}
