package com.nwodhcout.napper.app;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Pete on 19.7.2014.
 */
public class ButtonActivator {
    private static final PorterDuffColorFilter GRAY_FILTER = new PorterDuffColorFilter(Color.rgb(234, 234, 234), PorterDuff.Mode.MULTIPLY);
    private Drawable bg;

    public ButtonActivator(Button btn, final ButtonActivatorListener activity, final ArrayList<View> additionalViews){
        this.bg = btn.getBackground();
        setOnTouchListener(btn, activity, additionalViews);

    }

    //todo implement some kind of button lock. Currently if the user clicks the button multiple times he can start many activities
    public void setOnTouchListener(Button btn, final ButtonActivatorListener activity, final ArrayList<View> additionalViews){
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Rect bounds = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                switch (event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        setFilters(additionalViews);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        if(!bounds.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())){
                            clearFilters(additionalViews);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        if(bounds.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())){
                            activity.handleButtonPress(v);
                        }
                        clearFilters(additionalViews);
                        break;
                }

                return false;
            }});
    }

    private void setFilters(ArrayList<View> additionalViews){
        bg.setColorFilter(GRAY_FILTER);
        if(additionalViews != null){
            for(View v: additionalViews){
                if(v instanceof TextView){
                    ((TextView) v).setTextColor(Color.LTGRAY);
                }else{
                    v.getBackground().setColorFilter(GRAY_FILTER);
                }

            }
        }

    }

    private void clearFilters(ArrayList<View> additionalViews){
        bg.clearColorFilter();
        if(additionalViews != null) {
            for (View v : additionalViews) {
                if(v instanceof TextView) {
                    ((TextView) v).setTextColor(Color.WHITE);
                }else{
                    v.getBackground().clearColorFilter();
                }

            }
        }
    }

}
