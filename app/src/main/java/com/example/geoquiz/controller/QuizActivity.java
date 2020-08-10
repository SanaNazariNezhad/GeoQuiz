package com.example.geoquiz.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.geoquiz.CheatActivity;
import com.example.geoquiz.model.Question;
import com.example.geoquiz.R;

import java.io.Serializable;
import java.time.Instant;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String BUNDLE_KEY_CURRENT_INDEX = "currentIndex";
    private static final String BUNDLE_KEY_SCORE_NUMBER = "scoreNumber";
    private static final String BUNDLE_KEY_QUESTION_BANK = "questionBank";
    public static final String EXTRA_QUESTION_ANSWER = "com.example.geoquiz.questionAnswer";
    public static final int REQUEST_CODE_CHEAT = 0;

    private Button mButtonTrue, mButtonFalse, mButtonCheat;
    private ImageButton mImageButtonNext, mImageButtonPrev, mImageButtonFirst, mImageButtonLast, mImageButtonReset, mImageButtonSetting;
    private TextView mTextViewQuestion, mScoreNumber, mScoreNumberGameOver;
    private String mScoreValue = "0";
    private LinearLayout mLinearLayoutMain;
    private LinearLayout mLinearLayoutGameOver;

    private int mCurrentIndex = 0;
    private Question[] mQuestionBank = setQuestion();


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
        } else
            Log.d(TAG, "savedInstanceState is NULL!!");

        //        System.out.println("I'm in onCreate"); //this is wrong
        Log.d(TAG, "onCreate: " + mCurrentIndex);

        //this method will create the layout
        //inflate: creating object of xml layout
        setContentView(R.layout.activity_quiz);


        findViews();
        mScoreNumber.setText(mScoreValue);
        setListeners();
        updateQuestion();
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK || data == null)
            return;
        if (requestCode == REQUEST_CODE_CHEAT) {
            mQuestionBank[mCurrentIndex].setCheater(data.getBooleanExtra(CheatActivity.EXTRA_IS_CHEAT, false));
        }

    }

    private void findViews() {
        mTextViewQuestion = findViewById(R.id.txtview_question_text);
        mScoreNumber = findViewById(R.id.score_number);
        mScoreNumberGameOver = findViewById(R.id.score_number_gameOver);
        mButtonTrue = findViewById(R.id.btn_true);
        mButtonFalse = findViewById(R.id.btn_false);
        mButtonCheat = findViewById(R.id.btn_cheat);
        mImageButtonNext = findViewById(R.id.imageBtn_next);
        mImageButtonPrev = findViewById(R.id.imageBtn_prev);
        mImageButtonFirst = findViewById(R.id.imageBtn_first);
        mImageButtonLast = findViewById(R.id.imageBtn_last);
        mImageButtonReset = findViewById(R.id.imageBtn_reset);
        mImageButtonSetting = findViewById(R.id.imageBtn_setting);
        mLinearLayoutMain = findViewById(R.id.main_layout);
        mLinearLayoutGameOver = findViewById(R.id.game_over_layout);


    }

    private void setListeners() {
        mButtonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBtn(true);
                Question.mNumberOfAnsweredQuestion += 1;
            }
        });
        mButtonFalse.setOnClickListener(new View.OnClickListener() {
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
                checkDisableBtn();

            }
        });
        mImageButtonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex - 1 + mQuestionBank.length) % mQuestionBank.length;
                updateQuestion();
                checkDisableBtn();

            }
        });
        mImageButtonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = 0;
                updateQuestion();
                checkDisableBtn();

            }
        });
        mImageButtonLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = mQuestionBank.length - 1;
                updateQuestion();
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
                checkDisableBtn();

            }
        });
        mButtonCheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
                intent.putExtra(EXTRA_QUESTION_ANSWER, mQuestionBank[mCurrentIndex].isAnswerTrue());
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
//                startActivity(intent);
            }
        });
        mImageButtonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
            mButtonFalse.setEnabled(false);
            mButtonTrue.setEnabled(false);
        }
    }

    private void checkDisableBtn() {
        if (mQuestionBank[mCurrentIndex].isDisableBtn() == 0) {
            mButtonFalse.setEnabled(true);
            mButtonTrue.setEnabled(true);
        } else {
            mButtonFalse.setEnabled(false);
            mButtonTrue.setEnabled(false);
        }
    }

    private void updateQuestion() {
        if (Question.mNumberOfAnsweredQuestion == mQuestionBank.length) {
            mScoreNumberGameOver.setText(mScoreNumber.getText());
            mLinearLayoutMain.setVisibility(View.GONE);
            mLinearLayoutGameOver.setVisibility(View.VISIBLE);
        } else {
            int questionTextResId = mQuestionBank[mCurrentIndex].getQuestionTextResId();
            mTextViewQuestion.setTextColor(Color.BLACK);
            mTextViewQuestion.setText(questionTextResId);
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