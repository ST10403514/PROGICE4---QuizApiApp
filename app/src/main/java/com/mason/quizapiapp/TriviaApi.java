package com.mason.quizapiapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface TriviaApi {
    @GET("api.php")
    Call<TriviaResponse> getTriviaQuestions(
            @Query("amount") int amount,
            @Query("category") int category,
            @Query("difficulty") String difficulty,
            @Query("type") String type // "multiple"
    );
}
