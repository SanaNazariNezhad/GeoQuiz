package com.example.geoquiz.controller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.geoquiz.R;

public class QuizActivity extends AppCompatActivity {


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

        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            QuizFragment quizFragment = new QuizFragment();
            fragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, quizFragment)
                    .commit();
        }

    }

}