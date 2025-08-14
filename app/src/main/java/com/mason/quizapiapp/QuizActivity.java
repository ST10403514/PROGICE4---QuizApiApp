package com.mason.quizapiapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;
import android.os.Handler;
import android.text.Html;
import android.view.View;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QuizActivity extends AppCompatActivity {

    private TextView questionText, scoreText;
    private Button[] answerButtons = new Button[4];
    private List<Question> questions;
    private int currentIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionText = findViewById(R.id.questionText);
        scoreText = findViewById(R.id.scoreText);
        answerButtons[0] = findViewById(R.id.answer1);
        answerButtons[1] = findViewById(R.id.answer2);
        answerButtons[2] = findViewById(R.id.answer3);
        answerButtons[3] = findViewById(R.id.answer4);

        fetchQuestions();
    }

    private void fetchQuestions() {
        TriviaApi api = RetrofitInstance.getApi();
        Call<TriviaResponse> call = api.getTriviaQuestions(10, 9, "medium", "multiple");

        call.enqueue(new Callback<TriviaResponse>() {
            @Override
            public void onResponse(Call<TriviaResponse> call, Response<TriviaResponse> response) {
                if(response.isSuccessful() && response.body() != null) {
                    questions = response.body().getResults();
                    showQuestion();
                }
            }

            @Override
            public void onFailure(Call<TriviaResponse> call, Throwable t) {
                Toast.makeText(QuizActivity.this, "Failed to load questions", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showQuestion() {
        if(currentIndex >= questions.size()) {
            Toast.makeText(this, "Quiz Finished! Score: " + score, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Question q = questions.get(currentIndex);
        questionText.setText(Html.fromHtml(q.getQuestion()));

        List<String> answers = new ArrayList<>(q.getIncorrect_answers());
        answers.add(q.getCorrect_answer());
        Collections.shuffle(answers);

        for(int i=0; i<4; i++){
            answerButtons[i].setText(Html.fromHtml(answers.get(i)));
            answerButtons[i].setBackgroundColor(Color.parseColor("#6200EE")); // default button color
            answerButtons[i].setOnClickListener(v -> {
                if(((Button)v).getText().toString().equals(q.getCorrect_answer())){
                    score++;
                    v.setBackgroundColor(Color.GREEN);
                } else {
                    v.setBackgroundColor(Color.RED);
                }
                scoreText.setText("Score: " + score);

                new Handler().postDelayed(() -> {
                    currentIndex++;
                    showQuestion();
                }, 700);
            });
        }
    }
}
