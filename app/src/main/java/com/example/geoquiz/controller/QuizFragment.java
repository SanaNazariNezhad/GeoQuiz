package com.example.geoquiz.controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geoquiz.CheatActivity;
import com.example.geoquiz.CheatFragment;
import com.example.geoquiz.LoginActivity;
import com.example.geoquiz.R;
import com.example.geoquiz.SettingActivity;
import com.example.geoquiz.controller.QuizActivity;
import com.example.geoquiz.model.Question;
import com.example.geoquiz.model.Setting;
import com.example.geoquiz.repository.QuizRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class QuizFragment extends Fragment {

    private static final String TAG = "QuizActivity";
    private static final String BUNDLE_KEY_CURRENT_INDEX = "currentIndex";
    private static final String BUNDLE_KEY_SCORE_NUMBER = "scoreNumber";
    private static final String BUNDLE_KEY_QUESTION_BANK = "questionBank";
    private static final String BUNDLE_KEY_SETTING = "setting";
    public static final String EXTRA_QUESTION_ANSWER = "com.example.geoquiz.questionAnswer";
    public static final String EXTRA_SETTING_STATUS = "Setting";
    public static final int REQUEST_CODE_CHEAT = 0;
    public static final int REQUEST_CODE_SETTING = 1;
    public static final String FLAG = "flag";
    public static final String EXTRA_BACKGROUND_COLOR = "backgroundColor";
    private boolean flag;
    private String mAppName;
    private String mColor = "WHITE";

    private ImageButton mImageButtonNext, mImageButtonPrev, mImageButtonFirst, mImageButtonLast,
            mImageButtonReset, mImageButtonSetting, mImageButtonLogOut, mImageButtonCheat,
            mImageButtonTrue, mImageButtonFalse, mImageButtonScore;
    private TextView mTextViewQuestion, mScoreNumber, mScoreNumberGameOver;
    private String mScoreValue = "0";
    private FrameLayout mLinearLayoutMain;
    private FrameLayout mLinearLayoutGameOver;

    private int mCurrentIndex = 0;
    private QuizRepository mQuizRepository = QuizRepository.getInstance();
    private List<Question> mQuestionBank = mQuizRepository.getQuestions();

//    private Question[] mQuestionBank;
    boolean[] mBooleansBtn = {false, false, false, false, false, false, false};
    private Setting mSetting;

    public QuizFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuestionBank = setQuestion();
        mSetting = new Setting(mBooleansBtn, "Medium", "White");
    }

    /**
     * 1. Inflate the layout (or create layout in code)
     * 2. find all views
     * 3. logic for all views (like setListeners)
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Log.d(TAG, "savedInstanceState: " + savedInstanceState);
            mQuestionBank = (List<Question>) savedInstanceState.getSerializable(BUNDLE_KEY_QUESTION_BANK);
            mCurrentIndex = savedInstanceState.getInt(BUNDLE_KEY_CURRENT_INDEX, 0);
            mScoreValue = savedInstanceState.getString(BUNDLE_KEY_SCORE_NUMBER);
            mSetting = (Setting) savedInstanceState.getSerializable(BUNDLE_KEY_SETTING);
            flag = savedInstanceState.getBoolean(FLAG);
        } else
            Log.d(TAG, "savedInstanceState is NULL!!");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        mAppName = getActivity().getIntent().getStringExtra(LoginActivity.EXTRA_APP_TITLE);
        getActivity().setTitle(mAppName);
        findViews(view);
        mCurrentIndex = getActivity().getIntent().getIntExtra(QuizListFragment.EXTRA_QUESTION_ID,0);
        mScoreNumber.setText(mScoreValue);
        setListeners();
        updateQuestion();
        if (flag)
            updateSetting();
        checkDisableBtn();

        return view;
    }

    /**
     * it will save bundle before it will be destroyed
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: " + mCurrentIndex);

        outState.putSerializable(BUNDLE_KEY_QUESTION_BANK, (Serializable) mQuestionBank);
        outState.putInt(BUNDLE_KEY_CURRENT_INDEX, mCurrentIndex);
        outState.putString(BUNDLE_KEY_SCORE_NUMBER, mScoreNumber.getText().toString());
        outState.putSerializable(BUNDLE_KEY_SETTING, mSetting);
        outState.putBoolean(FLAG, flag);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK || data == null)
            return;
        if (requestCode == REQUEST_CODE_CHEAT) {
            mQuestionBank.get(mCurrentIndex).setCheater(data.getBooleanExtra(CheatFragment.EXTRA_IS_CHEAT, false));
            checkDisableBtn();
        } else if (requestCode == REQUEST_CODE_SETTING) {
            Bundle bundle = data.getExtras();
            mSetting = (Setting) bundle.getSerializable(SettingActivity.EXTRA_IS_SAVED_SETTING);
            updateSetting();
            checkDisableBtn();
            flag = true;
        }

    }


    private void findViews(View view) {
        mTextViewQuestion = view.findViewById(R.id.txtview_question_text);
        mScoreNumber = view.findViewById(R.id.score_number);
        mScoreNumberGameOver = view.findViewById(R.id.score_number_gameOver);
        mImageButtonTrue = view.findViewById(R.id.btn_true);
        mImageButtonFalse = view.findViewById(R.id.btn_false);
        mImageButtonCheat = view.findViewById(R.id.btn_cheat);
        mImageButtonLogOut = view.findViewById(R.id.btnLogOut);
        mImageButtonNext = view.findViewById(R.id.imageBtn_next);
        mImageButtonPrev = view.findViewById(R.id.imageBtn_prev);
        mImageButtonFirst = view.findViewById(R.id.imageBtn_first);
        mImageButtonLast = view.findViewById(R.id.imageBtn_last);
        mImageButtonReset = view.findViewById(R.id.imageBtn_reset);
        mImageButtonScore = view.findViewById(R.id.imageBtn_score);
        mImageButtonSetting = view.findViewById(R.id.imageBtn_setting);
        mLinearLayoutMain = view.findViewById(R.id.main_layout);
        mLinearLayoutGameOver = view.findViewById(R.id.game_over_layout);


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
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size();
                updateQuestion();
                updateSetting();
                checkDisableBtn();


            }
        });
        mImageButtonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex - 1 + mQuestionBank.size()) % mQuestionBank.size();
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
                mCurrentIndex = mQuestionBank.size() - 1;
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
                Intent intent = new Intent(getActivity(), CheatActivity.class);
                intent.putExtra(EXTRA_QUESTION_ANSWER, mQuestionBank.get(mCurrentIndex).isAnswerTrue());
                intent.putExtra(EXTRA_BACKGROUND_COLOR, mColor);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
                updateSetting();
//                startActivity(intent);
            }
        });
        mImageButtonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                intent.putExtra(EXTRA_SETTING_STATUS, mSetting);
                intent.putExtra(EXTRA_BACKGROUND_COLOR, mColor);
                startActivityForResult(intent, REQUEST_CODE_SETTING);
            }
        });
        mImageButtonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
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

    public List<Question> setQuestion() {
        mQuestionBank = mQuizRepository.getQuestions();
        return mQuestionBank;
    }

    private void checkBtn(boolean b) {
        if (mQuestionBank.get(mCurrentIndex).isDisableBtn() == 0) {
            checkAnswer(b);
            mQuestionBank.get(mCurrentIndex).setDisableBtn(1);
            mImageButtonFalse.setEnabled(false);
            mImageButtonTrue.setEnabled(false);
        }
    }

    private void checkDisableBtn() {
        if (!mSetting.getHideButtons()[0] && !mSetting.getHideButtons()[1]) {
            if (mQuestionBank.get(mCurrentIndex).isDisableBtn() == 0) {
                mImageButtonFalse.setEnabled(true);
                mImageButtonTrue.setEnabled(true);
            } else {
                mImageButtonFalse.setEnabled(false);
                mImageButtonTrue.setEnabled(false);
            }
        } else if (!mSetting.getHideButtons()[0]) {
            if (mQuestionBank.get(mCurrentIndex).isDisableBtn() == 0) {
                mImageButtonTrue.setEnabled(true);
            } else {
                mImageButtonFalse.setEnabled(false);
                mImageButtonTrue.setEnabled(false);
            }

        } else if (!mSetting.getHideButtons()[1]) {
            if (mQuestionBank.get(mCurrentIndex).isDisableBtn() == 0) {
                mImageButtonFalse.setEnabled(true);
            } else {
                mImageButtonFalse.setEnabled(false);
                mImageButtonTrue.setEnabled(false);
            }

        }
    }

    private void updateQuestion() {
        if (Question.mNumberOfAnsweredQuestion == mQuestionBank.size()) {
            mScoreNumberGameOver.setText(mScoreNumber.getText());
            mLinearLayoutMain.setVisibility(View.GONE);
            mLinearLayoutGameOver.setVisibility(View.VISIBLE);
            setGameOverColor();
        } else {
            int questionTextResId = mQuestionBank.get(mCurrentIndex).getQuestionTextResId();
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
        if (mQuestionBank.get(mCurrentIndex).isCheater()) {
            callToast(R.string.judgment_toast, R.color.red, Gravity.BOTTOM, R.drawable.ic_baseline_cancel, 16).show();
        } else {
            if (mQuestionBank.get(mCurrentIndex).isAnswerTrue() == userPressed) {
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

        toast = Toast.makeText(getActivity(), stringRes, Toast.LENGTH_SHORT);
        toast.getView().setBackgroundColor(getResources().getColor(colorRes));
        TextView toastMessage = toast.getView().findViewById(android.R.id.message);
        toastMessage.setTextColor(Color.WHITE);
        toastMessage.setTextSize(size);
        toast.setGravity(gravity, 0, 30);
        toastMessage.setCompoundDrawablesWithIntrinsicBounds(iconRes, 0, 0, 0);


        return toast;

    }
}