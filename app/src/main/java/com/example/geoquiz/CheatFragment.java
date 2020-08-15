package com.example.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.geoquiz.controller.QuizFragment;


public class CheatFragment extends Fragment {

    public static final String EXTRA_IS_CHEAT = "isCheat";
    public static final String BUNDLE_KEY_IS_CHEATER = "clickCheatButton";
    public static final String BUNDLE_KEY_ANSWER = "CheatAnswer";
    public static final String BUNDLE_KEY_ANSWER_DISABLE = "CheatAnswerDisable";

    private ImageButton mImageButtonBack, mImageButtonShowAnswer;
    private LinearLayout mLinearLayoutCheat;
    private TextView mTextViewAnswer;
    private boolean mButtonShowAnswerDisable = false;

    private boolean mCheck = false;
    private String mAnswerTxt = "";
    private String mBackgroundColor;
    private boolean mIsAnswerTrue;


    public CheatFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCheck = savedInstanceState.getBoolean(BUNDLE_KEY_IS_CHEATER);
            mAnswerTxt = savedInstanceState.getString(BUNDLE_KEY_ANSWER);
            mButtonShowAnswerDisable = savedInstanceState.getBoolean(BUNDLE_KEY_ANSWER_DISABLE);

        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cheat, container, false);
        mBackgroundColor = getActivity().getIntent().getStringExtra(QuizFragment.EXTRA_BACKGROUND_COLOR);
        mIsAnswerTrue = getActivity().getIntent().getBooleanExtra(QuizFragment.EXTRA_QUESTION_ANSWER, false);
        getActivity().setTitle(R.string.help);


        if (mCheck)
            setShownAnswerResult(mCheck);

        findViews(view);
        setBackColor();
        setDisable(mButtonShowAnswerDisable);
        mTextViewAnswer.setText(mAnswerTxt);
        setListeners();

        return view;
    }

    private void setBackColor() {
        if (mBackgroundColor.equals("LIGHT_RED")) {
            setColor(R.color.light_red);
        } else if (mBackgroundColor.equals("LIGHT_BLUE")) {
            setColor(R.color.light_blue);
        } else if (mBackgroundColor.equals("LIGHT_GREEN")) {
            setColor(R.color.light_green);
        } else {
            setColor(R.color.white);
        }
    }

    private void setColor(int p) {
        mLinearLayoutCheat.setBackgroundResource(p);
        mImageButtonBack.setBackgroundResource(p);
        mImageButtonShowAnswer.setBackgroundResource(p);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(BUNDLE_KEY_IS_CHEATER, mCheck);
        outState.putBoolean(BUNDLE_KEY_ANSWER_DISABLE, mButtonShowAnswerDisable);
        outState.putString(BUNDLE_KEY_ANSWER, mTextViewAnswer.getText().toString());
    }

    private void setListeners() {
        mImageButtonShowAnswer.setOnClickListener(new View.OnClickListener() {
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
        mImageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setShownAnswerResult(mCheck);
                getActivity().finish();
            }
        });
    }

    private void setDisable(boolean b) {
        if (b)
            mImageButtonShowAnswer.setEnabled(false);
        else
            mImageButtonShowAnswer.setEnabled(true);
    }

    private void findViews(View view) {
        mImageButtonShowAnswer = view.findViewById(R.id.btn_show_answer);
        mTextViewAnswer = view.findViewById(R.id.txtview_answer);
        mImageButtonBack = view.findViewById(R.id.btn_back);
        mLinearLayoutCheat = view.findViewById(R.id.layoutCheat);

    }

    private void setShownAnswerResult(boolean isCheat) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_IS_CHEAT, isCheat);
        getActivity().setResult(Activity.RESULT_OK, intent);
    }
}