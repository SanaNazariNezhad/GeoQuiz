package com.example.geoquiz.model;

import com.example.geoquiz.BackgroundColor;
import com.example.geoquiz.TextSize;

import java.io.Serializable;

public class Setting implements Serializable {
    public static int mLength = 7;
    private boolean[] mHideButtons = new boolean[mLength];
    private String  mTextSize;
    private String  mBackgroundColor;

    public Setting(boolean[] hideButtons, String textSize, String backgroundColor) {
        mHideButtons = hideButtons;
        mTextSize = textSize;
        mBackgroundColor = backgroundColor;
    }

    {
        for (int i = 0; i <mLength ; i++) {
            mHideButtons[i] = false;
        }
    }


    public boolean[] getHideButtons() {
        return mHideButtons;
    }

    public void setHideButtons(boolean[] hideButtons) {
        mHideButtons = hideButtons;
    }

    public String getTextSize() {
        return mTextSize;
    }

    public void setTextSize(String  textSize) {
        mTextSize = textSize;
    }

    public String getBackgroundColor() {
        return mBackgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        mBackgroundColor = backgroundColor;
    }
}
