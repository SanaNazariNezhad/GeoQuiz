package com.example.geoquiz.repository;

import com.example.geoquiz.R;
import com.example.geoquiz.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class QuizRepository {

    private static QuizRepository sInstance;

    public static QuizRepository getInstance() {
        if (sInstance == null)
            sInstance = new QuizRepository();

        return sInstance;
    }

    private List<Question> mQuestions;
    private int id = 0;

    private QuizRepository() {
        mQuestions = new ArrayList<>();


        mQuestions.add(new Question(R.string.question_australia, false,id));
        mQuestions.add(new Question(R.string.question_oceans, true,++id));
        mQuestions.add(new Question(R.string.question_mideast, false,++id));
        mQuestions.add(new Question(R.string.question_africa, true,++id));
        mQuestions.add(new Question(R.string.question_americas, false,++id));
        mQuestions.add(new Question(R.string.question_asia, false,++id));
        mQuestions.add(new Question(R.string.question_australia, false,++id));
        mQuestions.add(new Question(R.string.question_oceans, true,++id));
        mQuestions.add(new Question(R.string.question_mideast, false,++id));
        mQuestions.add(new Question(R.string.question_africa, true,++id));
        mQuestions.add(new Question(R.string.question_americas, false,++id));
        mQuestions.add(new Question(R.string.question_asia, false,++id));

    }

    public List<Question> getQuestions() {
        return mQuestions;
    }

    public Question getQuestion(int id) {
        for (Question question: mQuestions) {
            if (question.getId() == id)
                return question;
        }

        return null;
    }

    public void setQuestions(List<Question> questions) {
        mQuestions = questions;
    }


    public void insertQuestion(Question question) {
        mQuestions.add(question);
    }

}
