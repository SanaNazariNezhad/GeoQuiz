package com.example.geoquiz.controller;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.geoquiz.LoginActivity;
import com.example.geoquiz.R;
import com.example.geoquiz.model.Question;
import com.example.geoquiz.repository.QuizRepository;

import java.util.List;


public class QuizListFragment extends Fragment {

    public static final String EXTRA_QUESTION_ID = "question_id";
    public static final String EXTRA_TITLE_APP = "titleApp";
    private String mAppName;

    private RecyclerView mRecyclerView;

    private QuizRepository mRepository;

    public QuizListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRepository = QuizRepository.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quiz_list, container, false);

        mAppName = getActivity().getIntent().getStringExtra(LoginActivity.EXTRA_APP_TITLE);
        getActivity().setTitle(mAppName);

        findViews(view);
        initViews();

        return view;
    }

    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_quiz_list);
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<Question> questions = mRepository.getQuestions();
        QuizAdapter quizAdapter = new QuizAdapter(questions);

        mRecyclerView.setAdapter(quizAdapter);
    }

    private class QuizHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewTitle;
        private CheckBox mCheckBox;
        private Question mQuestion;

        public QuizHolder(@NonNull View itemView) {
            super(itemView);

            mTextViewTitle = itemView.findViewById(R.id.txtview_quizList_text);
            mCheckBox = itemView.findViewById(R.id.checkBox_quizList);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), QuizActivity.class);
                    intent.putExtra(EXTRA_QUESTION_ID, mQuestion.getId());
                    intent.putExtra(EXTRA_TITLE_APP,mAppName);
                    startActivity(intent);
                }
            });
        }

        public void bindQuestion(Question question) {
            mQuestion = question;
            mTextViewTitle.setText(question.getQuestionTextResId());
            mCheckBox.setChecked(question.isAnswerTrue() ? true : false);
        }
    }

    public class QuizAdapter extends RecyclerView.Adapter<QuizHolder>{

        private List<Question> mQuestions;

        public List<Question> getQuestions() {
            return mQuestions;
        }

        public void setQuestions(List<Question> questions) {
            mQuestions = questions;
        }

        public QuizAdapter (List<Question> questions) {
            mQuestions = questions;
        }

        @NonNull
        @Override
        public QuizHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.quiz_row_list, parent, false);
            QuizHolder quizHolder = new QuizHolder(view);
            return quizHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull QuizHolder holder, int position) {
            Question question = mQuestions.get(position);
            holder.bindQuestion(question);

        }

        @Override
        public int getItemCount() {
            return mQuestions.size();
        }
    }
}