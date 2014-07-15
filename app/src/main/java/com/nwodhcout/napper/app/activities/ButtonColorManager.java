package com.nwodhcout.napper.app.activities;

import android.graphics.Color;
import android.widget.Button;

/**
 * Class to manage buttons so that one of them has a text color of "activated color" and the others
 * have normal color.
 */
public class ButtonColorManager {

    private static final int NORMAL_COLOR = Color.WHITE;
    private static final int ACTIVATED_COLOR = Color.rgb(134, 76, 158);
    private Button[] buttons;
    private Button activatedButton;
    private Button defaultActivatedButton;

    public ButtonColorManager(Button[] buttons){
        this.buttons = buttons;
        this.defaultActivatedButton = buttons[0];
    }

    public void activateButton(Button btn){
        deactivateAllButtons();
        btn.setTextColor(ACTIVATED_COLOR);
        activatedButton = btn;
    }

    private void deactivateAllButtons(){
        for (int i = 0; i < buttons.length; i++){
            buttons[i].setTextColor(NORMAL_COLOR);
        }
    }

    public void resumeButtonState(){
        if(activatedButton == null){
            activateButton(defaultActivatedButton);
        }else{
            activateButton(activatedButton);
        }
    }

    public static int getActivatedColor(){
        return ACTIVATED_COLOR;
    }

    public static int getNormalColor(){
        return NORMAL_COLOR;
    }
}
