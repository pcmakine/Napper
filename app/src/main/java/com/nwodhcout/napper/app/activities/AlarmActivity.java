package com.nwodhcout.napper.app.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.nwodhcout.napper.app.ButtonActivator;
import com.nwodhcout.napper.app.ButtonActivatorListener;
import com.nwodhcout.napper.app.NapAlarmManager;
import com.nwodhcout.napper.app.Common;
import com.nwodhcout.napper.app.NapNotification;
import com.nwodhcout.napper.app.R;

import java.io.IOException;
import java.util.ArrayList;

public class AlarmActivity extends Activity implements ButtonActivatorListener {
    private MediaPlayer mMediaPlayer;
    private static final int ALARMEXPIRATION = 120; // seconds
    private CountDownTimer timer;
    private boolean stoppedByUser;
    private Button stopAlarm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setWindowFlags();
        setContentView(R.layout.activity_alarm_receiver);

        stopAlarm = (Button) findViewById(R.id.stop);
        ArrayList views = new ArrayList();
        views.add(findViewById(R.id.rectangle));
        views.add(findViewById(R.id.stopText));

        ButtonActivator.setOnTouchListener(stopAlarm, this, views);
        playSound(this, getAlarmUri());
        setAlarmExpiration();
        setAnimation();
        NapNotification.cancelNotification(this);
    }

    public void handleButtonPress(View v){
        cleanUp();
        onBackPressed();
    }

    private void setAnimation(){
        final Animation animationScale = AnimationUtils.loadAnimation(this, R.anim.scale);
        Button stopBtn = (Button) findViewById(R.id.stop);
        stopBtn.startAnimation(animationScale);
    }

    private void setAlarmExpiration(){
        timer = new CountDownTimer(Common.secondsToMs(ALARMEXPIRATION), Common.secondsToMs(ALARMEXPIRATION)) {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                onBackPressed();
            }
        }.start();
    }

    //http://stackoverflow.com/questions/11823259/using-flag-show-when-locked-with-disablekeyguard-in-secured-android-lock-scree
    private void setWindowFlags(){
        Window window = this.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        );
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void playSound(Context context, Uri alert) {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mMediaPlayer.start();
            }
        });
        try {
            mMediaPlayer.setDataSource(context, alert);
            final AudioManager audioManager = (AudioManager) context
                    .getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mMediaPlayer.prepareAsync();
                mMediaPlayer.start();
            }
        } catch (IOException e) {
            System.out.println("OOPS");
        }
    }

    private void releaseMediaPlayer(){
        if(mMediaPlayer != null){
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    //Get an alarm sound. Try for an alarm. If none set, try notification,
    //Otherwise, ringtone.
    private Uri getAlarmUri() {
        Uri alert = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alert == null) {
            alert = RingtoneManager
                    .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (alert == null) {
                alert = RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            }
        }
        return alert;
    }

    @Override
    public void onPause(){
        super.onPause();
        if (stoppedByUser){
            cleanUp();
            finish();
        }
    }

    private void cleanUp(){
        releaseMediaPlayer();
        NapAlarmManager.cancelAlarm(this);
        if(timer != null){
            timer.cancel();
        }
        NapNotification.cancelNotification(this);
    }

    @Override
    public void onStart(){
        super.onStart();
        this.stoppedByUser = false;
    }

    @Override
    public void onUserLeaveHint(){
        super.onUserLeaveHint();
        this.stoppedByUser = true;
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        this.stoppedByUser = true;
    }
}