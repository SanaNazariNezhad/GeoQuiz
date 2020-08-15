package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;


public class CheatActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cheat);


        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container_cheat);


        if (fragment == null) {
            CheatFragment cheatFragment = new CheatFragment();
            fragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container_cheat, cheatFragment)
                    .commit();
        }

    }
}