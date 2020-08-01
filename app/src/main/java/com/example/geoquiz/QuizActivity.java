package com.example.geoquiz;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    private Button mButtonTrue;
    private Button mButtonFalse;
    private TextView mTextView;

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

        findViews();

        setListeners();
        /*LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setGravity(Gravity.CENTER);

        TextView textViewName = new TextView(this);
        textViewName.setText("Hi This Is Maktab");
        textViewName.setTextSize(30);

        rootLayout.addView(textViewName);
        setContentView(rootLayout);*/
    }

    private void findViews() {
        mButtonTrue = findViewById(R.id.btn_true);
        mButtonFalse = findViewById(R.id.btn_false);
        mTextView = findViewById(R.id.txtview_question_text);
    }

    private void setListeners() {
        mButtonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callToast(R.string.toast_correct, R.color.green, Gravity.BOTTOM,R.drawable.ic_baseline_check_circle,18).show();
                mTextView.setTextColor(getResources().getColor(R.color.green));
            }
        });
        mButtonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = callToast(R.string.toast_incorrect, R.color.red, Gravity.TOP,R.drawable.ic_baseline_cancel,18);
                toast.show();
                mTextView.setTextColor(getResources().getColor(R.color.red));
            }
        });
    }

    private Toast callToast(int stringRes, int colorRes, int gravity, int iconRes, float size) {
        Toast toast;

        toast = Toast.makeText(QuizActivity.this, stringRes, Toast.LENGTH_SHORT);
        toast.getView().setBackgroundColor(getResources().getColor(colorRes));
        TextView toastMessage = toast.getView().findViewById(android.R.id.message);
        toastMessage.setTextColor(Color.WHITE);
        toastMessage.setTextSize(size);
        toast.setGravity(gravity, 0, 150);
        toastMessage.setCompoundDrawablesWithIntrinsicBounds(iconRes, 0, 0, 0);


        return toast;

    }
}