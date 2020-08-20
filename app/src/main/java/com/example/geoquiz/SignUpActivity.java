package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity {

    public static final String EXTRA_USERNAME_SIGN_UP = "extraUsername";
    public static final String EXTRA_PASSWORD_SIGN_UP = "EXTRA_password";
    private Button mBtnSignUp;
    private TextInputLayout mUsernameForm;
    private TextInputLayout mPasswordForm;
    private TextInputEditText mUsername;
    private TextInputEditText mPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        findViews();
        mUsername.setText(getIntent().getStringExtra(LoginActivity.EXTRA_USERNAME));
        mPassword.setText(getIntent().getStringExtra(LoginActivity.EXTRA_PASSWORD));
        listener();
    }
    private void listener() {
        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUsernameForm.setErrorEnabled(false);
                mPasswordForm.setErrorEnabled(false);
                if (mUsername.getText().toString().trim().isEmpty() && mPassword.getText().toString().trim().isEmpty()) {
                    mUsernameForm.setErrorEnabled(true);
                    mUsernameForm.setError("Field cannot be empty!");
                    mPasswordForm.setErrorEnabled(true);
                    mPasswordForm.setError("Field cannot be empty!");
                }else if (mUsername.getText().toString().trim().isEmpty()){
                    mUsernameForm.setErrorEnabled(true);
                    mUsernameForm.setError("Field cannot be empty!");
                }else if (mPassword.getText().toString().trim().isEmpty()){
                    mPasswordForm.setErrorEnabled(true);
                    mPasswordForm.setError("Field cannot be empty!");
                } else {
                    setUserPassResult();
                    finish();
                }

            }
        });
    }

    private void setUserPassResult() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_USERNAME_SIGN_UP, mUsername.getText().toString());
        intent.putExtra(EXTRA_PASSWORD_SIGN_UP, mPassword.getText().toString());
        setResult(RESULT_OK,intent);
    }

    private void findViews() {
        mBtnSignUp = findViewById(R.id.btnSignUp_SignUP);
        mUsernameForm = findViewById(R.id.username_form_signUp);
        mUsername = findViewById(R.id.username_signUp);
        mPasswordForm = findViewById(R.id.password_form_signUp);
        mPassword = findViewById(R.id.password_signUp);
    }
}