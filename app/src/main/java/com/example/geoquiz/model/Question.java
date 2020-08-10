package com.example.geoquiz.model;

import java.io.Serializable;

public class Question implements Serializable {
    private int mQuestionTextResId;
    private boolean mIsAnswerTrue;
    private int mDisableBtn = 0;
    public static int mNumberOfAnsweredQuestion = 0;
    private boolean mIsCheater = false;

    public boolean isCheater() {
        return mIsCheater;
    }

    public void setCheater(boolean cheater) {
        mIsCheater = cheater;
    }

    public int isDisableBtn() {
        return mDisableBtn;
    }

    public void setDisableBtn(int disableBtn) {
        this.mDisableBtn = disableBtn;
    }


    public int getQuestionTextResId() {
        return mQuestionTextResId;
    }

    public void setQuestionTextResId(int questionTextResId) {
        mQuestionTextResId = questionTextResId;
    }

    public boolean isAnswerTrue() {
        return mIsAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mIsAnswerTrue = answerTrue;
    }

    public Question(int questionTextResId, boolean isAnswerTrue) {
        mQuestionTextResId = questionTextResId;
        mIsAnswerTrue = isAnswerTrue;
    }

    public Question() {
    }

}
