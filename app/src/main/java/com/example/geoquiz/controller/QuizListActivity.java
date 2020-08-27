package com.example.geoquiz.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.geoquiz.R;

public class QuizListActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return new QuizListFragment();
    }
}