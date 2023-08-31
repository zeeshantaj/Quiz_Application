package com.example.quiz_application.Api_Service;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuizApiResponse {
    @SerializedName("results")
    private List<QuizQuestion> questions;

    public List<QuizQuestion> getQuestions() {
        return questions;
    }
}

