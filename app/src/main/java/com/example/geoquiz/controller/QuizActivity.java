package com.example.geoquiz.controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.geoquiz.CheatActivity;
import com.example.geoquiz.LoginActivity;
import com.example.geoquiz.SettingActivity;
import com.example.geoquiz.model.Question;
import com.example.geoquiz.R;
import com.example.geoquiz.model.Setting;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String BUNDLE_KEY_CURRENT_INDEX = "currentIndex";
    private static final String BUNDLE_KEY_SCORE_NUMBER = "scoreNumber";
    private static final String BUNDLE_KEY_QUESTION_BANK = "questionBank";
    private static final String BUNDLE_KEY_SETTING = "setting";
    public static final String EXTRA_QUESTION_ANSWER = "com.example.geoquiz.questionAnswer";
    public static final String EXTRA_SETTING_STATUS = "Setting";
    public static final String EXTRA_APP_TITLE_FOR_CHEAT = "extraUsername";
    public static final String EXTRA_APP_TITLE_FOR_SETTING = "extraUsername";
    public static final int REQUEST_CODE_CHEAT = 0;
    public static final int REQUEST_CODE_SETTING = 1;
    public static final String FLAG = "flag";
    public static final String EXTRA_BACKGROUND_COLOR = "backgroundColor";
    private boolean flag;
    private String mAppName;
    private String mColor = "WHITE";

    private ImageButton mImageButtonNext, mImageButtonPrev, mImageButtonFirst, mImageButtonLast,
            mImageButtonReset, mImageButtonSetting, mImageButtonLogOut, mImageButtonCheat,
            mImageButtonTrue, mImageButtonFalse,mImageButtonScore;
    private TextView mTextViewQuestion, mScoreNumber, mScoreNumberGameOver;
    private String mScoreValue = "0";
    private FrameLayout mLinearLayoutMain;
    private FrameLayout mLinearLayoutGameOver;

    private int mCurrentIndex = 0;
    private Question[] mQuestionBank = setQuestion();
    boolean[] mBooleansBtn = {false, false, false, false, false, false, false};
    private Setting mSetting = new Setting(mBooleansBtn, "Medium", "White");


    /**
     * This method is used to crete ui for activity.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            Log.d(TAG, "savedInstanceState: " + savedInstanceState);
            mQuestionBank = (Question[]) savedInstanceState.getSerializable(BUNDLE_KEY_QUESTION_BANK);
            mCurrentIndex = savedInstanceState.getInt(BUNDLE_KEY_CURRENT_INDEX, 0);
            mScoreValue = savedInstanceState.getString(BUNDLE_KEY_SCORE_NUMBER);
            mSetting = (Setting) savedInstanceState.getSerializable(BUNDLE_KEY_SETTING);
            flag = savedInstanceState.getBoolean(FLAG);
        } else
            Log.d(TAG, "savedInstanceState is NULL!!");

        //        System.out.println("I'm in onCreate"); //this is wrong
        Log.d(TAG, "onCreate: " + mCurrentIndex);

        //this method will create the layout
        //inflate: creating object of xml layout
        setContentView(R.layout.activity_quiz);


        mAppName = getIntent().getStringExtra(LoginActivity.EXTRA_APP_TITLE);
        setTitle(mAppName);
        findViews();
        mScoreNumber.setText(mScoreValue);
        setListeners();
        updateQuestion();
        if (flag)
            updateSetting();
        checkDisableBtn();




        /*LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setGravity(Gravity.CENTER);

        TextView textViewName = new TextView(this);
        textViewName.setText("Hi This Is Maktab");
        textViewName.setTextSize(30);

        rootLayout.addView(textViewName);
        setContentView(rootLayout);*/
    }


    /*@Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart: " + mCurrentIndex);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume: " + mCurrentIndex);
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "onPause: " + mCurrentIndex);
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "onStop: " + mCurrentIndex);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestory: " + mCurrentIndex);
    }*/

    /**
     * it will save bundle before it will be destroyed
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: " + mCurrentIndex);

        outState.putSerializable(BUNDLE_KEY_QUESTION_BANK, mQuestionBank);
        outState.putInt(BUNDLE_KEY_CURRENT_INDEX, mCurrentIndex);
        outState.putString(BUNDLE_KEY_SCORE_NUMBER, mScoreNumber.getText().toString());
        outState.putSerializable(BUNDLE_KEY_SETTING, mSetting);
        outState.putBoolean(FLAG, flag);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK || data == null)
            return;
        if (requestCode == REQUEST_CODE_CHEAT) {
            mQuestionBank[mCurrentIndex].setCheater(data.getBooleanExtra(CheatActivity.EXTRA_IS_CHEAT, false));
            checkDisableBtn();
        } else if (requestCode == REQUEST_CODE_SETTING) {
            Bundle bundle = data.getExtras();
            mSetting = (Setting) bundle.getSerializable(SettingActivity.EXTRA_IS_SAVED_SETTING);
            updateSetting();
            checkDisableBtn();
            flag = true;
        }

    }

    private void findViews() {
        mTextViewQuestion = findViewById(R.id.txtview_question_text);
        mScoreNumber = findViewById(R.id.score_number);
        mScoreNumberGameOver = findViewById(R.id.score_number_gameOver);
        mImageButtonTrue = findViewById(R.id.btn_true);
        mImageButtonFalse = findViewById(R.id.btn_false);
        mImageButtonCheat = findViewById(R.id.btn_cheat);
        mImageButtonLogOut = findViewById(R.id.btnLogOut);
        mImageButtonNext = findViewById(R.id.imageBtn_next);
        mImageButtonPrev = findViewById(R.id.imageBtn_prev);
        mImageButtonFirst = findViewById(R.id.imageBtn_first);
        mImageButtonLast = findViewById(R.id.imageBtn_last);
        mImageButtonReset = findViewById(R.id.imageBtn_reset);
        mImageButtonScore = findViewById(R.id.imageBtn_score);
        mImageButtonSetting = findViewById(R.id.imageBtn_setting);
        mLinearLayoutMain = findViewById(R.id.main_layout);
        mLinearLayoutGameOver = findViewById(R.id.game_over_layout);


    }

    private void setListeners() {
        mImageButtonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBtn(true);
                Question.mNumberOfAnsweredQuestion += 1;
            }
        });
        mImageButtonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBtn(false);
                Question.mNumberOfAnsweredQuestion += 1;
            }
        });
        mImageButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
                updateSetting();
                checkDisableBtn();


            }
        });
        mImageButtonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex - 1 + mQuestionBank.length) % mQuestionBank.length;
                updateQuestion();
                updateSetting();
                checkDisableBtn();


            }
        });
        mImageButtonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = 0;
                updateQuestion();
                updateSetting();
                checkDisableBtn();


            }
        });
        mImageButtonLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = mQuestionBank.length - 1;
                updateQuestion();
                updateSetting();
                checkDisableBtn();

            }
        });
        mImageButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Question.mNumberOfAnsweredQuestion = 0;
                mCurrentIndex = 0;
                mScoreNumber.setText("0");
                mLinearLayoutMain.setVisibility(View.VISIBLE);
                mLinearLayoutGameOver.setVisibility(View.GONE);
                mQuestionBank = setQuestion();
                updateQuestion();
                updateSetting();
                checkDisableBtn();


            }
        });
        mImageButtonCheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
                intent.putExtra(EXTRA_QUESTION_ANSWER, mQuestionBank[mCurrentIndex].isAnswerTrue());
                intent.putExtra(EXTRA_APP_TITLE_FOR_CHEAT, mAppName);
                intent.putExtra(EXTRA_BACKGROUND_COLOR, mColor);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
                updateSetting();
//                startActivity(intent);
            }
        });
        mImageButtonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizActivity.this, SettingActivity.class);
                intent.putExtra(EXTRA_SETTING_STATUS, mSetting);
                intent.putExtra(EXTRA_APP_TITLE_FOR_SETTING, mAppName);
                startActivityForResult(intent, REQUEST_CODE_SETTING);
            }
        });
        mImageButtonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void updateSetting() {
        updateBtn();
        updateSize();
        updateBackgroundColor();
    }

    private void updateBackgroundColor() {
        if (mSetting.getBackgroundColor().equalsIgnoreCase("LIGHT_RED")) {
            setBackgroundColor(R.color.light_red);
            mColor = "LIGHT_RED";
        } else if (mSetting.getBackgroundColor().equalsIgnoreCase("LIGHT_BLUE")) {
            setBackgroundColor(R.color.light_blue);
            mColor = "LIGHT_BLUE";
        } else if (mSetting.getBackgroundColor().equalsIgnoreCase("LIGHT_GREEN")) {
            setBackgroundColor(R.color.light_green);
            mColor = "LIGHT_GREEN";
        } else {
            setBackgroundColor(R.color.white);
            mColor = "WHITE";
        }
    }

    private void setBackgroundColor(int p) {
        mLinearLayoutMain.setBackgroundResource(p);
        mImageButtonSetting.setBackgroundResource(p);
        mImageButtonCheat.setBackgroundResource(p);
        mImageButtonLogOut.setBackgroundResource(p);
        mImageButtonTrue.setBackgroundResource(p);
        mImageButtonFalse.setBackgroundResource(p);
        mImageButtonNext.setBackgroundResource(p);
        mImageButtonPrev.setBackgroundResource(p);
        mImageButtonFirst.setBackgroundResource(p);
        mImageButtonLast.setBackgroundResource(p);
        mImageButtonScore.setBackgroundResource(p);
    }

    private void updateSize() {
        if (mSetting.getTextSize().equalsIgnoreCase("SMALL"))
            mTextViewQuestion.setTextSize(14);
        else if (mSetting.getTextSize().equalsIgnoreCase("MEDIUM"))
            mTextViewQuestion.setTextSize(16);
        else
            mTextViewQuestion.setTextSize(18);
    }

    private void updateBtn() {
        boolean[] btnStatus = mSetting.getHideButtons();
        if (btnStatus[0])
            mImageButtonTrue.setEnabled(false);
        else
            mImageButtonTrue.setEnabled(true);

        if (btnStatus[1])
            mImageButtonFalse.setEnabled(false);
        else
            mImageButtonFalse.setEnabled(true);

        if (btnStatus[2])
            mImageButtonNext.setEnabled(false);
        else
            mImageButtonNext.setEnabled(true);

        if (btnStatus[3])
            mImageButtonPrev.setEnabled(false);
        else
            mImageButtonPrev.setEnabled(true);

        if (btnStatus[4])
            mImageButtonFirst.setEnabled(false);
        else
            mImageButtonFirst.setEnabled(true);

        if (btnStatus[5])
            mImageButtonLast.setEnabled(false);
        else
            mImageButtonLast.setEnabled(true);

        if (btnStatus[6])
            mImageButtonCheat.setEnabled(false);
        else
            mImageButtonCheat.setEnabled(true);
    }

    public Question[] setQuestion() {
        Question[] mQuestionBank = {
                new Question(R.string.question_australia, false),
                new Question(R.string.question_oceans, true),
                new Question(R.string.question_mideast, false),
                new Question(R.string.question_africa, true),
                new Question(R.string.question_americas, false),
                new Question(R.string.question_asia, false)
        };
        return mQuestionBank;
    }

    private void checkBtn(boolean b) {
        if (mQuestionBank[mCurrentIndex].isDisableBtn() == 0) {
            checkAnswer(b);
            mQuestionBank[mCurrentIndex].setDisableBtn(1);
            mImageButtonFalse.setEnabled(false);
            mImageButtonTrue.setEnabled(false);
        }
    }

    private void checkDisableBtn() {
        if (!mSetting.getHideButtons()[0] && !mSetting.getHideButtons()[1]) {
            if (mQuestionBank[mCurrentIndex].isDisableBtn() == 0) {
                mImageButtonFalse.setEnabled(true);
                mImageButtonTrue.setEnabled(true);
            } else {
                mImageButtonFalse.setEnabled(false);
                mImageButtonTrue.setEnabled(false);
            }
        } else if (!mSetting.getHideButtons()[0]) {
            if (mQuestionBank[mCurrentIndex].isDisableBtn() == 0) {
                mImageButtonTrue.setEnabled(true);
            } else {
                mImageButtonFalse.setEnabled(false);
                mImageButtonTrue.setEnabled(false);
            }

        } else if (!mSetting.getHideButtons()[1]) {
            if (mQuestionBank[mCurrentIndex].isDisableBtn() == 0) {
                mImageButtonFalse.setEnabled(true);
            } else {
                mImageButtonFalse.setEnabled(false);
                mImageButtonTrue.setEnabled(false);
            }

        }
    }

    private void updateQuestion() {
        if (Question.mNumberOfAnsweredQuestion == mQuestionBank.length) {
            mScoreNumberGameOver.setText(mScoreNumber.getText());
            mLinearLayoutMain.setVisibility(View.GONE);
            mLinearLayoutGameOver.setVisibility(View.VISIBLE);
            setGameOverColor();
        } else {
            int questionTextResId = mQuestionBank[mCurrentIndex].getQuestionTextResId();
            mTextViewQuestion.setTextColor(Color.BLACK);
            mTextViewQuestion.setText(questionTextResId);
        }

    }

    private void setGameOverColor() {
        switch (mColor) {
            case "LIGHT_RED":
                mLinearLayoutGameOver.setBackgroundResource(R.color.light_red);
                mImageButtonReset.setBackgroundResource(R.color.light_red);
                break;
            case "LIGHT_BLUE":
                mLinearLayoutGameOver.setBackgroundResource(R.color.light_blue);
                mImageButtonReset.setBackgroundResource(R.color.light_blue);
                break;
            case "LIGHT_GREEN":
                mLinearLayoutGameOver.setBackgroundResource(R.color.light_green);
                mImageButtonReset.setBackgroundResource(R.color.light_green);
                break;
            default:
                mLinearLayoutGameOver.setBackgroundResource(R.color.white);
                mImageButtonReset.setBackgroundResource(R.color.white);
                break;
        }
    }

    private void checkAnswer(boolean userPressed) {
        if (mQuestionBank[mCurrentIndex].isCheater()) {
            callToast(R.string.judgment_toast, R.color.red, Gravity.BOTTOM, R.drawable.ic_baseline_cancel, 16).show();
        } else {
            if (mQuestionBank[mCurrentIndex].isAnswerTrue() == userPressed) {
                callToast(R.string.toast_correct, R.color.green, Gravity.BOTTOM, R.drawable.ic_baseline_check_circle, 16).show();
                mTextViewQuestion.setTextColor(getResources().getColor(R.color.green));
                CharSequence scoreChar = mScoreNumber.getText();
                int score = Integer.parseInt((String) scoreChar);
                score += 1;
                mScoreNumber.setText("" + score);
            } else {
                callToast(R.string.toast_incorrect, R.color.red, Gravity.BOTTOM, R.drawable.ic_baseline_cancel, 16).show();
                mTextViewQuestion.setTextColor(getResources().getColor(R.color.red));
            }
        }
    }

    private Toast callToast(int stringRes, int colorRes, int gravity, int iconRes, float size) {
        Toast toast;

        toast = Toast.makeText(QuizActivity.this, stringRes, Toast.LENGTH_SHORT);
        toast.getView().setBackgroundColor(getResources().getColor(colorRes));
        TextView toastMessage = toast.getView().findViewById(android.R.id.message);
        toastMessage.setTextColor(Color.WHITE);
        toastMessage.setTextSize(size);
        toast.setGravity(gravity, 0, 30);
        toastMessage.setCompoundDrawablesWithIntrinsicBounds(iconRes, 0, 0, 0);


        return toast;

    }
}