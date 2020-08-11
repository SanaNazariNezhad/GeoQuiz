package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class SettingActivity extends AppCompatActivity {
    private SwitchCompat mSwitchBtnTrue;
    private SwitchCompat mSwitchBtnFalse;
    private SwitchCompat mSwitchBtnNext;
    private SwitchCompat mSwitchBtnPrev;
    private SwitchCompat mSwitchBtnFirst;
    private SwitchCompat mSwitchBtnLast;
    private SwitchCompat mSwitchBtnCheat;
    private Button mButtonSave;
    private Button mButtonDiscard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        findViews();
        Listeners();
    }

    private void Listeners() {
        mSwitchBtnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSwitchText(mSwitchBtnTrue);
            }
        });
        mSwitchBtnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSwitchText(mSwitchBtnFalse);
            }
        });
        mSwitchBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSwitchText(mSwitchBtnNext);
            }
        });
        mSwitchBtnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSwitchText(mSwitchBtnPrev);
            }
        });
        mSwitchBtnFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSwitchText(mSwitchBtnFirst);
            }
        });
        mSwitchBtnLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSwitchText(mSwitchBtnLast);
            }
        });
        mSwitchBtnCheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSwitchText(mSwitchBtnCheat);
            }
        });
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mButtonDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void changeSwitchText(SwitchCompat switchBtn) {
        if (switchBtn.getText().equals("Hide"))
            switchBtn.setText(R.string.show_setting);
        else
            switchBtn.setText(R.string.hide_setting);
    }

    private void findViews() {
        mSwitchBtnTrue = findViewById(R.id.switch_btnTrue);
        mSwitchBtnFalse = findViewById(R.id.switch_btnFalse);
        mSwitchBtnNext = findViewById(R.id.switch_btnNext);
        mSwitchBtnPrev = findViewById(R.id.switch_btnPrev);
        mSwitchBtnFirst = findViewById(R.id.switch_btnFirst);
        mSwitchBtnLast = findViewById(R.id.switch_btnLast);
        mSwitchBtnCheat = findViewById(R.id.switch_btnCheat);

    }
}