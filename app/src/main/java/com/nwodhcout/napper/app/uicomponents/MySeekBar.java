package com.nwodhcout.napper.app.uicomponents;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.SeekBar;

import com.nwodhcout.napper.app.activities.ButtonColorManager;

/**
 * Created by Pete on 9.7.2014.
 */
public class MySeekBar extends SeekBar {
    private Drawable mThumb;

    public MySeekBar(Context context) {
        super(context);
        setColor();
    }
    public MySeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setColor();
    }
    public MySeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setColor();
    }

    public void setColor(){
        getProgressDrawable().setColorFilter(ButtonColorManager.getNormalColor(), PorterDuff.Mode.SRC_IN);
        mThumb.setColorFilter(ButtonColorManager.getActivatedColor(), PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void setThumb(Drawable thumb) {
        super.setThumb(thumb);
        mThumb = thumb;

    }


}
