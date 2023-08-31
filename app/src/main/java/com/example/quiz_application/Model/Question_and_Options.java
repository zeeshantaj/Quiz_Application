package com.example.quiz_application.Model;

import java.util.List;
import java.util.Set;

public class Question_and_Options {
    private String question;
    private List<String> options;
    private Set<String> correctOptions;
    private int correctOptionIndex;
    private String SelectedOptions;

    public Question_and_Options(String question, List<String> options,Set<String> correctOptions) {
        this.question = question;
        this.options = options;
        this.correctOptions = correctOptions;
    }


    public String getSelectedOptions() {
        return SelectedOptions;
    }

    public void setSelectedOptions(String selectedOptions) {
        SelectedOptions = selectedOptions;
    }

    public Set<String> getCorrectOptions() {
        return correctOptions;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }

    public void setCorrectOptionIndex(int correctOptionIndex) {
        this.correctOptionIndex = correctOptionIndex;
    }
}
