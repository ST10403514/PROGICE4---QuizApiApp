package com.mason.quizapiapp;
import java.util.List;
public class TriviaResponse {
    private int response_code;
    private List<Question> results;

    public List<Question> getResults() {
        return results;
    }
}
