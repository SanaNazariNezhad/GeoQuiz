package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.example.geoquiz.controller.QuizFragment;
import com.example.geoquiz.model.Setting;

public class SettingActivity extends AppCompatActivity {
    public static final String EXTRA_IS_SAVED_SETTING = "isSaved";
    private SwitchCompat mSwitchBtnTrue;
    private SwitchCompat mSwitchBtnFalse;
    private SwitchCompat mSwitchBtnNext;
    private SwitchCompat mSwitchBtnPrev;
    private SwitchCompat mSwitchBtnFirst;
    private SwitchCompat mSwitchBtnLast;
    private SwitchCompat mSwitchBtnCheat;
    private ImageButton mImageButtonSave;
    private ImageButton mImageButtonDiscard;
    private RadioButton mRadioBtnSmall;
    private RadioButton mRadioBtnMedium;
    private RadioButton mRadioBtnLarge;
    private RadioButton mRadioBtnLightRed;
    private RadioButton mRadioBtnLightBLue;
    private RadioButton mRadioBtnLightGreen;
    private RadioButton mRadioBtnWhite;

    private FrameLayout mLinearLayoutSetting;

    private Setting mSetting;
    private String mBackgroundColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        setTitle(R.string.setting);
        mBackgroundColor = getIntent().getStringExtra(QuizFragment.EXTRA_BACKGROUND_COLOR);
        mSetting =(Setting) getIntent().getSerializableExtra(QuizFragment.EXTRA_SETTING_STATUS);

        findViews();
        setBackColor();
        updateSetting();
        Listeners();
    }

    private void setBackColor() {
        if (mBackgroundColor.equals("LIGHT_RED")) {
            setColor(R.color.light_red);
        }
        else if (mBackgroundColor.equals("LIGHT_BLUE")) {
            setColor(R.color.light_blue);
        }
        else if (mBackgroundColor.equals("LIGHT_GREEN")) {
            setColor(R.color.light_green);
        }
        else {
            setColor(R.color.white);
        }
    }

    private void setColor(int p) {
        mLinearLayoutSetting.setBackgroundResource(p);
        mImageButtonSave.setBackgroundResource(p);
        mImageButtonDiscard.setBackgroundResource(p);
    }

    private void updateSetting() {
        updateBtn();
        updateSize();
        updateBackgroundColor();
    }

    private void updateBackgroundColor() {
        if (mSetting.getBackgroundColor().equalsIgnoreCase("LIGHT_RED")) {
            mRadioBtnLightRed.setChecked(true);
            mRadioBtnLightBLue.setChecked(false);
            mRadioBtnLightGreen.setChecked(false);
            mRadioBtnWhite.setChecked(false);
        }
        else if (mSetting.getBackgroundColor().equalsIgnoreCase("LIGHT_BLUE")) {
            mRadioBtnLightRed.setChecked(false);
            mRadioBtnLightBLue.setChecked(true);
            mRadioBtnLightGreen.setChecked(false);
            mRadioBtnWhite.setChecked(false);
        }
        else if (mSetting.getBackgroundColor().equalsIgnoreCase("LIGHT_GREEN")) {
            mRadioBtnLightRed.setChecked(false);
            mRadioBtnLightBLue.setChecked(false);
            mRadioBtnLightGreen.setChecked(true);
            mRadioBtnWhite.setChecked(false);
        }
        else {
            mRadioBtnLightRed.setChecked(false);
            mRadioBtnLightBLue.setChecked(false);
            mRadioBtnLightGreen.setChecked(false);
            mRadioBtnWhite.setChecked(true);
        }
    }

    private void updateSize() {
        if (mSetting.getTextSize().equalsIgnoreCase("SMALL")){
            mRadioBtnSmall.setChecked(true);
            mRadioBtnMedium.setChecked(false);
            mRadioBtnLarge.setChecked(false);
        }
        else if (mSetting.getTextSize().equalsIgnoreCase("MEDIUM")){
            mRadioBtnSmall.setChecked(false);
            mRadioBtnMedium.setChecked(true);
            mRadioBtnLarge.setChecked(false);
        }
        else{
            mRadioBtnSmall.setChecked(false);
            mRadioBtnMedium.setChecked(false);
            mRadioBtnLarge.setChecked(true);
        }
    }

    private void updateBtn() {
        boolean[] btnStatus = mSetting.getHideButtons();
        checkingbtnstatus(btnStatus, 0, mSwitchBtnTrue);

        checkingbtnstatus(btnStatus, 1, mSwitchBtnFalse);

        checkingbtnstatus(btnStatus, 2, mSwitchBtnNext);

        checkingbtnstatus(btnStatus, 3, mSwitchBtnPrev);

        checkingbtnstatus(btnStatus, 4, mSwitchBtnFirst);

        checkingbtnstatus(btnStatus, 5, mSwitchBtnLast);

        checkingbtnstatus(btnStatus, 6, mSwitchBtnCheat);
    }

    private void checkingbtnstatus(boolean[] btnStatus, int i, SwitchCompat switchBtn) {
        if (btnStatus[i])
            switchBtn.setChecked(false);
        else
            switchBtn.setChecked(true);
    }

    private void Listeners() {
        mImageButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean[] checkHide = btnStatus();
                mSetting.setHideButtons(checkHide);
                String  size = checkSize();
                mSetting.setTextSize(size);
                String color = checkColor();
                mSetting.setBackgroundColor(color);
                setSavedResult(mSetting);
                finish();

            }
        });
        mImageButtonDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String checkColor() {
        String color;
        if (mRadioBtnLightRed.isChecked())
            color = "Light_Red";
        else if (mRadioBtnLightBLue.isChecked())
            color = "Light_Blue";
        else if (mRadioBtnLightGreen.isChecked())
            color = "Light_Green";
        else
            color = "White";
        return color;
    }

    private String checkSize() {
        String size;
        if (mRadioBtnSmall.isChecked())
            size = "Small";
        else if (mRadioBtnMedium.isChecked())
            size = "Medium";
        else
            size = "Large";
        return size;
    }

    private boolean[] btnStatus() {
        boolean [] status = new boolean[Setting.mLength];
        checkState(status, mSwitchBtnTrue, 0);
        checkState(status, mSwitchBtnFalse, 1);
        checkState(status, mSwitchBtnNext, 2);
        checkState(status, mSwitchBtnPrev, 3);
        checkState(status, mSwitchBtnFirst, 4);
        checkState(status, mSwitchBtnLast, 5);
        checkState(status, mSwitchBtnCheat, 6);

        return status;
    }

    private void checkState(boolean[] status, SwitchCompat switchBtnTrue, int i) {
        if (switchBtnTrue.isChecked())
            status[i] = false;
        else
            status[i] = true;
    }

    private void findViews() {
        mSwitchBtnTrue = findViewById(R.id.switch_btnTrue);
        mSwitchBtnFalse = findViewById(R.id.switch_btnFalse);
        mSwitchBtnNext = findViewById(R.id.switch_btnNext);
        mSwitchBtnPrev = findViewById(R.id.switch_btnPrev);
        mSwitchBtnFirst = findViewById(R.id.switch_btnFirst);
        mSwitchBtnLast = findViewById(R.id.switch_btnLast);
        mSwitchBtnCheat = findViewById(R.id.switch_btnCheat);
        mRadioBtnSmall = findViewById(R.id.radioBtnSmall);
        mRadioBtnMedium = findViewById(R.id.radioBtnMedium);
        mRadioBtnLarge = findViewById(R.id.radioBtnLarge);
        mRadioBtnLightRed = findViewById(R.id.radioBtnLightRed);
        mRadioBtnLightBLue = findViewById(R.id.radioBtnLightBlue);
        mRadioBtnLightGreen = findViewById(R.id.radioBtnLightGreen);
        mRadioBtnWhite = findViewById(R.id.radioBtnWhite);
        mImageButtonSave = findViewById(R.id.btnSave);
        mImageButtonDiscard = findViewById(R.id.btnDiscard);
        mLinearLayoutSetting = findViewById(R.id.layout_setting);

    }

    private void setSavedResult(Setting setting) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_IS_SAVED_SETTING,setting);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
    }
}