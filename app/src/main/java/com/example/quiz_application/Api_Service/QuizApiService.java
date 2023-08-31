package com.example.quiz_application.Api_Service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface QuizApiService {

    @GET("api.php")
    Call<QuizApiResponse> getQuizQuestion(
            @Query("amount") int amount
    );
    @GET("api.php")
    Call<QuizApiResponse> getSelectedQuizQuestions(
            @Query("amount") int amount,
            @Query("category") int category,
            @Query("difficulty") String difficulty,
            @Query("type") String type
    );
}
