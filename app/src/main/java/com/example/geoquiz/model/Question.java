package com.example.geoquiz.model;

import java.io.Serializable;
import java.util.UUID;

public class Question implements Serializable {
    private int mQuestionTextResId;
    private boolean mIsAnswerTrue;
    private int mDisableBtn = 0;
    public static int mNumberOfAnsweredQuestion = 0;
    private boolean mIsCheater = false;
    private int mId;

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

    public int getId() {
        return mId;
    }

    public boolean isAnswerTrue() {
        return mIsAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mIsAnswerTrue = answerTrue;
    }

    public Question(int questionTextResId, boolean isAnswerTrue, int uuid) {
        mQuestionTextResId = questionTextResId;
        mIsAnswerTrue = isAnswerTrue;
        mId = uuid;
    }

    public Question() {
    }

}
