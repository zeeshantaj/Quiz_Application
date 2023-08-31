package com.example.quiz_application.ViewModel;

import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Random;

public class SharedViewModel extends ViewModel {

    private int selectedCategoryIndex;
    private final Random random = new Random();
    private String selectedType;
    private String selectedDifficulty;

    private List<String> correctAnswers;

    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(List<String> correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public String getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(String selectedType) {

        this.selectedType = selectedType;
    }

    public String getSelectedDifficulty() {
        return selectedDifficulty;
    }

    public void setSelectedDifficulty(String selectedDifficulty) {
        this.selectedDifficulty = selectedDifficulty;
    }

    public void setSelectedCategoryIndex(int selectedCategoryIndex) {
        if (selectedCategoryIndex == 8){
            selectedCategoryIndex = getRandomNumberInRange(9, 32);
        }
        this.selectedCategoryIndex = selectedCategoryIndex;
    }
    public int getSelectedCategoryIndex(){
        return selectedCategoryIndex;
    }
    private int getRandomNumberInRange(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

}
