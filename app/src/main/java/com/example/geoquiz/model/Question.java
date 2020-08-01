package com.example.geoquiz.model;

public class Question {
    private int mQuestionTextResId;
    private boolean mIsAnswerTrue;
    private int mDisableBtn = 0;
    public static int mNumberOfAnsweredQuestion = 0;

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
