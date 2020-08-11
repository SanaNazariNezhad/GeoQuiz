package com.example.geoquiz.model;

import com.example.geoquiz.BackgroundColor;
import com.example.geoquiz.TextSize;

import java.io.Serializable;

public class Setting implements Serializable {
    private int mLength = 7;
    private boolean[] mHideButtons = new boolean[mLength];
    private TextSize mTextSize;
    private BackgroundColor mBackgroundColor;

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

    public TextSize getTextSize() {
        return mTextSize;
    }

    public void setTextSize(TextSize textSize) {
        mTextSize = textSize;
    }

    public BackgroundColor getBackgroundColor() {
        return mBackgroundColor;
    }

    public void setBackgroundColor(BackgroundColor backgroundColor) {
        mBackgroundColor = backgroundColor;
    }
}
