package com.nwodhcout.napper.app;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

/**todo 10.7: refactor time extractor out of timerupdater. Give out a hashmap of keys like hoursOnes --> 1
 * and so on. Also make setcurrent time method and make the class remember the current and old seconds
 * then the class can just be asked whether the timeswitchers need to be updated
 *
 * save the alarm time in shared preferences for example so that when the user comes back to the app he
 * is directed right away to the feedback screen
*/
public class MainActivity extends ActionBarActivity {
    private static final int DEFAULTNAPTIME = 20; //min
    private static final int ACTIVATEDCOLOR = Color.rgb(134, 76, 158);
    private static final int DEACTIVATEDCOLOR = Color.rgb(194, 170, 210);
    private int napTime;
    private Button customTime;
    private Button activatedButton;
    private MySeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.napTime = DEFAULTNAPTIME;
        setContentView(R.layout.activity_main);
        this.customTime = (Button) findViewById(R.id.customMin);
        this.seekBar = (MySeekBar) findViewById(R.id.seekBar);

        setSeekBarListener();
        activateButton((Button) findViewById(R.id.twentyMin));
    }

    public void nap(View view){
        Intent intent = new Intent(this, NapFeedbackActivity.class);
        intent.putExtra("NAP_TIME", napTime);
        startActivity(intent);
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
        seekBar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        seekBar.getSeekBarThumb().setColorFilter(Color.LTGRAY, PorterDuff.Mode.SRC_IN);

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
