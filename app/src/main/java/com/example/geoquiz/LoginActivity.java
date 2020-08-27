package com.example.geoquiz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.geoquiz.controller.QuizActivity;
import com.example.geoquiz.controller.QuizListActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    public static final String EXTRA_USERNAME = "extraUsername";
    public static final String EXTRA_APP_TITLE = "extraUsername";
    public static final String EXTRA_PASSWORD = "EXTRA_password";
    public static final String BUNDLE_KEY_USERNAME = "UserBundle";
    public static final String BUNDLE_KEY_PASSWORD = "passBundle";
    private Button mButtonLogin,mButtonSignUp;
    public static final int REQUEST_CODE_SIGN_UP = 0;
    private String user, pass;
    private ViewGroup mViewGroupRootLayout;

    private TextInputLayout mUsernameForm;
    private TextInputLayout mPasswordForm;
    private TextInputEditText mUsername;
    private TextInputEditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){
            user = savedInstanceState.getString(BUNDLE_KEY_USERNAME);
            pass = savedInstanceState.getString(BUNDLE_KEY_PASSWORD);
        }

        setContentView(R.layout.activity_login);
        findViews();
        listeners();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BUNDLE_KEY_USERNAME,user);
        outState.putString(BUNDLE_KEY_PASSWORD,pass);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK || data == null)
            return;
        if (requestCode == REQUEST_CODE_SIGN_UP) {
            mUsername.setText(data.getStringExtra(SignUpActivity.EXTRA_USERNAME_SIGN_UP));
            mPassword.setText(data.getStringExtra(SignUpActivity.EXTRA_PASSWORD_SIGN_UP));
            user = data.getStringExtra(SignUpActivity.EXTRA_USERNAME_SIGN_UP);
            pass = data.getStringExtra(SignUpActivity.EXTRA_PASSWORD_SIGN_UP);
            mUsernameForm.setErrorEnabled(false);
            mPasswordForm.setErrorEnabled(false);
        }
    }

    private void listeners() {
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
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
                }
                else if (!mUsername.getText().toString().equals(user) ||
                        !mPassword.getText().toString().equals(pass)){
                    callToast(R.string.toast_login);
                }else {
                    Intent intent = new Intent(LoginActivity.this, QuizListActivity.class);
                    intent.putExtra(EXTRA_APP_TITLE,mUsername.getText().toString());
                    startActivity(intent);
                }

            }
        });
        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                if (!mPassword.getText().toString().equals("") || !mUsername.getText().toString().equals("")) {
                    intent.putExtra(EXTRA_USERNAME,mUsername.getText().toString());
                    intent.putExtra(EXTRA_PASSWORD,mPassword.getText().toString());
                }
                startActivityForResult(intent,REQUEST_CODE_SIGN_UP);
            }
        });
    }

    private void callToast(int p) {
        Toast toast = Toast.makeText(LoginActivity.this, p, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void findViews() {
        mButtonLogin = findViewById(R.id.btnLogin_Login);
        mButtonSignUp = findViewById(R.id.btnSignUp_Login);
        mUsernameForm = findViewById(R.id.username_form_login);
        mPasswordForm = findViewById(R.id.password_form_login);
        mUsername = findViewById(R.id.username_login);
        mPassword = findViewById(R.id.password_login);
        mViewGroupRootLayout = findViewById(R.id.rootLayout);
    }
}