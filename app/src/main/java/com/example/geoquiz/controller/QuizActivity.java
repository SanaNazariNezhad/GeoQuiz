package com.example.geoquiz.controller;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.geoquiz.model.Question;
import com.example.geoquiz.R;

public class QuizActivity extends AppCompatActivity {

    private Button mButtonTrue;
    private Button mButtonFalse;
    private ImageButton mImageButtonNext;
    private ImageButton mImageButtonPrev;
    private ImageButton mImageButtonFirst;
    private ImageButton mImageButtonLast;
    private ImageButton mImageButtonReset;
    private TextView mTextViewQuestion;
    private TextView mScoreNumber;
    private TextView mScoreNumberGameOver;
    private LinearLayout mLinearLayoutMain;
    private LinearLayout mLinearLayoutGameOver;
    private Activity mActivity;

    private int mCurrentIndex = 0;
    private Question[] mQuestionBank = {
            new Question(R.string.question_australia, false),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, true),
            new Question(R.string.question_americas, false),
            new Question(R.string.question_asia, false)
    };

    /**
     * This method is used to crete ui for activity.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //this method will create the layout
        //inflate: creating object of xml layout
        setContentView(R.layout.activity_quiz);

        mActivity = QuizActivity.this;

        findViews();
        setListeners();
        updateQuestion();




        /*LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setGravity(Gravity.CENTER);

        TextView textViewName = new TextView(this);
        textViewName.setText("Hi This Is Maktab");
        textViewName.setTextSize(30);

        rootLayout.addView(textViewName);
        setContentView(rootLayout);*/
    }

    private void findViews() {
        mTextViewQuestion = findViewById(R.id.txtview_question_text);
        mScoreNumber = findViewById(R.id.score_number);
        mScoreNumberGameOver = findViewById(R.id.score_number_gameOver);
        mButtonTrue = findViewById(R.id.btn_true);
        mButtonFalse = findViewById(R.id.btn_false);
        mImageButtonNext = findViewById(R.id.imageBtn_next);
        mImageButtonPrev = findViewById(R.id.imageBtn_prev);
        mImageButtonFirst = findViewById(R.id.imageBtn_first);
        mImageButtonLast = findViewById(R.id.imageBtn_last);
        mImageButtonReset = findViewById(R.id.imageBtn_reset);
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
                mActivity.recreate();

            }
        });
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
        if (mQuestionBank[mCurrentIndex].isAnswerTrue() == userPressed) {
            callToast(R.string.toast_correct, R.color.green, Gravity.BOTTOM, R.drawable.ic_baseline_check_circle, 18).show();
            mTextViewQuestion.setTextColor(getResources().getColor(R.color.green));
            CharSequence scoreChar = mScoreNumber.getText();
            int score = Integer.parseInt((String) scoreChar);
            score += 1;
            mScoreNumber.setText("" + score);
        } else {
            callToast(R.string.toast_incorrect, R.color.red, Gravity.BOTTOM, R.drawable.ic_baseline_cancel, 18).show();
            mTextViewQuestion.setTextColor(getResources().getColor(R.color.red));
        }
    }

    private Toast callToast(int stringRes, int colorRes, int gravity, int iconRes, float size) {
        Toast toast;

        toast = Toast.makeText(QuizActivity.this, stringRes, Toast.LENGTH_SHORT);
        toast.getView().setBackgroundColor(getResources().getColor(colorRes));
        TextView toastMessage = toast.getView().findViewById(android.R.id.message);
        toastMessage.setTextColor(Color.WHITE);
        toastMessage.setTextSize(size);
        toast.setGravity(gravity, 0, 100);
        toastMessage.setCompoundDrawablesWithIntrinsicBounds(iconRes, 0, 0, 0);


        return toast;

    }
}