package com.mason.quizapiapp;
import java.util.List;

public class Question {
    private String question;
    private String correct_answer;
    private List<String> incorrect_answers;

    public String getQuestion() {
        return question;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public List<String> getIncorrect_answers() {
        return incorrect_answers;
    }
}
