package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.geoquiz.controller.QuizActivity;
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
    private Button mButtonSave;
    private Button mButtonDiscard;
    private RadioButton mRadioBtnSmall;
    private RadioButton mRadioBtnMedium;
    private RadioButton mRadioBtnLarge;
    private RadioButton mRadioBtnLightRed;
    private RadioButton mRadioBtnLightBLue;
    private RadioButton mRadioBtnLightGreen;
    private RadioButton mRadioBtnWhite;

    private Setting mSetting;
    private String mTitleOfApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mTitleOfApp = getIntent().getStringExtra(LoginActivity.EXTRA_APP_TITLE);
        setTitle(mTitleOfApp);
        mSetting =(Setting) getIntent().getSerializableExtra(QuizActivity.EXTRA_SETTING_STATUS);

        findViews();
        updateSetting();
        Listeners();
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
        mButtonSave.setOnClickListener(new View.OnClickListener() {
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
        mButtonDiscard.setOnClickListener(new View.OnClickListener() {
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
        mButtonSave = findViewById(R.id.btnSave);
        mButtonDiscard = findViewById(R.id.btnDiscard);

    }

    private void setSavedResult(Setting setting) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_IS_SAVED_SETTING,setting);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
    }
}