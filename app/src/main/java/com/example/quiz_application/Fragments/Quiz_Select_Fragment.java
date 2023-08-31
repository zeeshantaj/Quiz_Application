package com.example.quiz_application.Fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.quiz_application.Api_Service.QuizApiResponse;
import com.example.quiz_application.Api_Service.QuizApiService;
import com.example.quiz_application.Api_Service.QuizQuestion;
import com.example.quiz_application.Model.Question_and_Options;
import com.example.quiz_application.R;
import com.example.quiz_application.ViewModel.SharedViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Quiz_Select_Fragment extends Fragment {

    public Quiz_Select_Fragment() {
    }

    private View view;
    private TextView questionTxt,questionCountTxt;
    private RadioGroup radioGroup;
    private Button submitBtn;
    private List<Question_and_Options> question_and_optionsList = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private int questionCount = 1;
    private int totalQuestion;
    private HashMap<Integer, String> selectedAnswers = new HashMap<>();
    private int totalPointsCount,rightAnsCount;
    private ProgressBar progressBar;
    private int submitClickedCount = 0;
    private int category;
    private String difficulty, type;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.quiz_layout, container, false);


        SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        category = sharedViewModel.getSelectedCategoryIndex();
        difficulty = sharedViewModel.getSelectedDifficulty();
        type = sharedViewModel.getSelectedType();
        progressBar = view.findViewById(R.id.progressBar);
        questionCountTxt = view.findViewById(R.id.questionCountTxt);
        questionTxt = view.findViewById(R.id.question_quiz);
        radioGroup = view.findViewById(R.id.radioOptionGroup);
        Button nextBtn = view.findViewById(R.id.nextBtn);
        Button previousBtn = view.findViewById(R.id.previousBtn);
        submitBtn = view.findViewById(R.id.submitBtn);
        submitBtn.setEnabled(false);

        nextBtn.setOnClickListener((view1 -> {
            showNextQuestion();
            if (questionCount == 10){
                return;
            }
            questionCount++;
            questionCountTxt.setText(String.valueOf(questionCount));
        }));
        previousBtn.setOnClickListener((view1 -> {

            showPreviousQuestion();
            if (questionCount == 1){
                return;
            }
            questionCount--;
            questionCountTxt.setText(String.valueOf(questionCount));
        }));
        submitBtn.setOnClickListener((view1 -> {
            submitClickedCount++;
            showAnswers();
            showDialogue();
            disableRadioButtons();
        }));


        apiCall();

        return view;

    }
    private void showPreviousQuestion() {
        if (currentQuestionIndex > 0){
            currentQuestionIndex--;
            updateUI();
        }
        if (submitClickedCount>0){
            disableRadioButtons();
            showAnswers();
        }
    }
    private void showNextQuestion() {
        if (currentQuestionIndex < question_and_optionsList.size() - 1){
            currentQuestionIndex++;
            updateUI();
        }
        if (submitClickedCount>0){
            disableRadioButtons();
            showAnswers();
        }
    }
    private void apiCall() {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://opentdb.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        QuizApiService quizApiService = retrofit.create(QuizApiService.class);
        Call<QuizApiResponse> call = quizApiService.getSelectedQuizQuestions(10, category, difficulty, type);
        call.enqueue(new Callback<QuizApiResponse>() {
            @Override
            public void onResponse(Call<QuizApiResponse> call, Response<QuizApiResponse> response) {
                if (response.isSuccessful()){

                    progressBar.setVisibility(View.GONE);

                    // Process response and set quizQuestions list
                    QuizApiResponse quizApiResponse = response.body();
                    List<QuizQuestion> quizQuestions = quizApiResponse.getQuestions();

                    totalQuestion = quizQuestions.size();

                    // Process the list of quiz questions and options
                    for (int i = 0; i < quizQuestions.size(); i++) {
                        QuizQuestion question = quizQuestions.get(i);

                        List<String> options;
                        Set<String> correctOptions = new HashSet<>(Arrays.asList(question.getCorrectAnswer()));

                        if (question.getType().equals("boolean")) {
                            options = Arrays.asList("True", "False");
                        } else {
                            options = new ArrayList<>(question.getIncorrectAnswers());
                            options.add(question.getCorrectAnswer());
                        }

                        for (String option : options) {
                            RadioButton radioButton = new RadioButton(requireContext());
                            radioButton.setText(option);
                            radioGroup.addView(radioButton);

                            // Set an OnClickListener for each RadioButton
                            int finalI = i;
                            radioButton.setOnClickListener(view -> {
                                onOptionSelected(finalI, option);
                            });
                        }


                        //shuffle options
                        Collections.shuffle(options);

                        Question_and_Options questionAndOptions = new Question_and_Options(question.getQuestion(), options, correctOptions);
                        question_and_optionsList.add(questionAndOptions);

                        Log.e("MyApp", "list" +question.getCorrectAnswer());
                    }

                    // Initialize the UI with the first question
                    currentQuestionIndex = 0;
                    updateUI();
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Error "+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<QuizApiResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Error "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void onOptionSelected(int questionIndex, String selectedOption) {
        selectedAnswers.put(questionIndex, selectedOption);

        if (areAllQuestionsAnswered()){
            submitBtn.setBackgroundResource(R.drawable.btn_bg);
            submitBtn.setEnabled(true);
            //disableRadioButtons();
        }
        //updateUI();
    }
    private void updateUI() {
        // Clear radio buttons from radioGroup
        radioGroup.removeAllViews();

        if (currentQuestionIndex >= 0 && currentQuestionIndex < question_and_optionsList.size()) {
            Question_and_Options currentQuestion = question_and_optionsList.get(currentQuestionIndex);

            // Update question text
            questionTxt.setText(currentQuestion.getQuestion());

            // Add options for the current question
            List<String> options = currentQuestion.getOptions();

            //shuffle options
            Collections.shuffle(options);

            for (String option : options) {
                RadioButton radioButton = new RadioButton(requireContext());
                radioButton.setTextSize(18);
                radioButton.setText(option);
                radioGroup.addView(radioButton);
                radioButton.setEnabled(true); // Enable radio buttons for the current question

                // Set an OnClickListener for each RadioButton
                radioButton.setOnClickListener(view -> {
                    onOptionSelected(currentQuestionIndex, option);

                    //radioButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                });

                // Check if this option was previously selected
                String selectedOption = selectedAnswers.get(currentQuestionIndex);
                if (selectedOption != null && selectedOption.equals(option)) {
                    radioButton.setChecked(true);
                }
            }
        }
    }

    private void showAnswers() {


        rightAnsCount = 0;
        totalPointsCount = 0;

        for (int i = 0; i < question_and_optionsList.size(); i++) {
            Question_and_Options questionAndOptions = question_and_optionsList.get(i);
            Set<String> correctOptions = questionAndOptions.getCorrectOptions();

            String selectedOptions = selectedAnswers.get(i);
            boolean isCorrect = correctOptions.contains(selectedOptions);
            if (isCorrect){
                rightAnsCount++;
                totalPointsCount += 10;
            }
        }

        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
            Question_and_Options questionAndOptions = question_and_optionsList.get(currentQuestionIndex);
            String selectedOption = selectedAnswers.get(currentQuestionIndex);
            Set<String> correctOptions = questionAndOptions.getCorrectOptions();

            if (selectedOption != null && selectedOption.equals(radioButton.getText().toString())) {
                if (correctOptions.contains(selectedOption)) {
                    radioButton.setTextColor(Color.GREEN); // Correct answer, set text color to green
                } else {
                    radioButton.setTextColor(Color.RED); // Incorrect answer, set text color to red
                }
            } else if (correctOptions.contains(radioButton.getText().toString())) {
                radioButton.setTextColor(Color.GREEN); // Correct answer, set text color to green
            }

            radioButton.setEnabled(false); // Disable radio buttons after submitting
        }

        disableRadioButtons();

    }
    public boolean areAllQuestionsAnswered() {
        return selectedAnswers.size() == question_and_optionsList.size();
    }
    private void showDialogue() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.result_layout);

        Button button = dialog.findViewById(R.id.closeResultBtn);
        TextView totalQuestionTxt = dialog.findViewById(R.id.totalQuestionsTxt);
        TextView totalPointsTxt = dialog.findViewById(R.id.totalPointsTxt);
        TextView resultScoreTxt = dialog.findViewById(R.id.resultScoreTxt);
        TextView totalRightAnsTxt = dialog.findViewById(R.id.totalRightAnsTxt);

        totalQuestionTxt.setText(String.valueOf(totalQuestion));
        totalRightAnsTxt.setText(String.valueOf(rightAnsCount));
        totalPointsTxt.setText(String.valueOf(totalPointsCount));
        if (totalPointsCount < 50){
            resultScoreTxt.setText("Failed");
        }
        else {
            resultScoreTxt.setText("Passed");
        }

        button.setOnClickListener((view1 -> {
            dialog.dismiss();
        }));

        dialog.show();
    }
    private void disableRadioButtons() {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
            radioButton.setEnabled(false);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        question_and_optionsList.clear();
        selectedAnswers.clear();
    }
}
