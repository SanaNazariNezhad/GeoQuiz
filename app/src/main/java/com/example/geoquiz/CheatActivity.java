package com.example.geoquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.geoquiz.controller.QuizActivity;

public class CheatActivity extends AppCompatActivity {

    public static final String EXTRA_IS_CHEAT = "isCheat";
    public static final String BUNDLE_KEY_IS_CHEATER = "clickCheatButton";
    public static final String BUNDLE_KEY_ANSWER = "CheatAnswer";
    public static final String BUNDLE_KEY_ANSWER_DISABLE = "CheatAnswerDisable";

    private Button mButtonBack , mButtonShowAnswer;
    private TextView mTextViewAnswer;
    private boolean mButtonShowAnswerDisable = false;
    private boolean mIsAnswerTrue;
    private boolean mCheck = false;
    private String mAnswerTxt = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){
            mCheck = savedInstanceState.getBoolean(BUNDLE_KEY_IS_CHEATER);
            mAnswerTxt = savedInstanceState.getString(BUNDLE_KEY_ANSWER);
            mButtonShowAnswerDisable = savedInstanceState.getBoolean(BUNDLE_KEY_ANSWER_DISABLE);

        }

            setContentView(R.layout.activity_cheat);


        if (mCheck)
            setShownAnswerResult(mCheck);
        mIsAnswerTrue = getIntent().getBooleanExtra(QuizActivity.EXTRA_QUESTION_ANSWER, false);
        findViews();
        setDisable(mButtonShowAnswerDisable);
        mTextViewAnswer.setText(mAnswerTxt);
        setListeners();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(BUNDLE_KEY_IS_CHEATER, mCheck);
        outState.putBoolean(BUNDLE_KEY_ANSWER_DISABLE, mButtonShowAnswerDisable);
        outState.putString(BUNDLE_KEY_ANSWER, mTextViewAnswer.getText().toString());
    }

    private void setListeners() {
        mButtonShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIsAnswerTrue)
                    mTextViewAnswer.setText(R.string.button_true);
                else
                    mTextViewAnswer.setText(R.string.button_false);

                mButtonShowAnswerDisable = true;
                setDisable(mButtonShowAnswerDisable);
                mCheck = true;
                setShownAnswerResult(mCheck);

            }
        });
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setShownAnswerResult(mCheck);
                finish();
            }
        });
    }

    private void setDisable(boolean b) {
        if (b)
            mButtonShowAnswer.setEnabled(false);
        else
            mButtonShowAnswer.setEnabled(true);
    }

    private void findViews() {
        mButtonShowAnswer = findViewById(R.id.btn_show_answer);
        mTextViewAnswer = findViewById(R.id.txtview_answer);
        mButtonBack = findViewById(R.id.btn_back);

    }

    private void setShownAnswerResult(boolean isCheat) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_IS_CHEAT, isCheat);
        setResult(RESULT_OK, intent);
    }
}