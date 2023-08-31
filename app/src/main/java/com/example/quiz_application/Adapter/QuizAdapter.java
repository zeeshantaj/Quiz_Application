package com.example.quiz_application.Adapter;

import static com.example.quiz_application.R.*;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quiz_application.Api_Service.QuizApiResponse;
import com.example.quiz_application.Api_Service.QuizQuestion;
import com.example.quiz_application.Model.Question_and_Options;
import com.example.quiz_application.R;
import com.example.quiz_application.ViewModel.SharedViewModel;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import retrofit2.Callback;

//public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.ViewHolder> {
//
//    public boolean radioButtonsEnabled = true;
//    private Context context;
//
//    List<Question_and_Options> quizQuestions;
//    private onOptionSelectedListener onOptionSelectedListener;
//    private HashMap<Integer, String> selectedAnswers = new HashMap<>();
//
//
//    public QuizAdapter(Context context,List<Question_and_Options> quizQuestions, QuizAdapter.onOptionSelectedListener onOptionSelectedListener) {
//        this.context = context;
//        this.quizQuestions = quizQuestions;
//        this.onOptionSelectedListener = onOptionSelectedListener;
//    }
//
//
//    @NonNull
//    @Override
//    public QuizAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//      //  View view = LayoutInflater.from(parent.getContext()).inflate(layout.quiz_questions_layout, parent, false);
//
//
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull QuizAdapter.ViewHolder holder, int position) {
//
//        Question_and_Options quizQuestion = quizQuestions.get(position);
//
//        holder.bind(quizQuestion);
//
////        holder.question.setText(quizQuestion.getQuestion());
////
////        List<String> options = quizQuestion.getOptions();
////
////        Collections.shuffle(options);
////        int optionsCount = options.size();
////        RadioGroup radioGroup = holder.radioGroup;
////        RadioButton radioButton ;
////        for (int i = 0; i < optionsCount; i++) {
////            radioButton = (RadioButton) radioGroup.getChildAt(i);
////            radioButton.setText(options.get(i));
////            radioButton.setVisibility(View.VISIBLE);
////
////            radioButton.setEnabled(radioButtonsEnabled);
////
////            // Set the checked state based on selected option
////            radioButton.setChecked(options.get(i).equals(quizQuestion.getSelectedOptions()));
////
////        }
////
////        holder.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
////            RadioButton selectedRadioButton = group.findViewById(checkedId);
////            if (selectedRadioButton != null) {
////
////                String selectedOption = selectedRadioButton.getText().toString();
////                selectedAnswers.put(position, selectedOption);
////
////
////                quizQuestion.setSelectedOptions(selectedOption); // Update the selected option
////
////                if (onOptionSelectedListener != null) {
////                    onOptionSelectedListener.onOptionSelected(selectedOption);
////                }
////            }
////        });
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public int getItemCount() {
//        return quizQuestions.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        private TextView question;
//        public RadioGroup radioGroup;
//        private Question_and_Options quizQuestion; // Add this field
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
////            question = itemView.findViewById(id.quizQuestion);
////            radioGroup = itemView.findViewById(id.optionsRadioGroup);
//
//            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
//                RadioButton selectedRadioButton = group.findViewById(checkedId);
//                if (selectedRadioButton != null) {
//                    String selectedOption = selectedRadioButton.getText().toString();
//                    quizQuestion.setSelectedOptions(selectedOption); // Update the selected option
//
//                    if (onOptionSelectedListener != null) {
//                        onOptionSelectedListener.onOptionSelected(selectedOption);
//                    }
//                   // notifyDataSetChanged(); // Refresh the views
//                }
//            });
//        }
//        public void bind(Question_and_Options quizQuestion) {
//            this.quizQuestion = quizQuestion;
//            question.setText(quizQuestion.getQuestion());
//
//            List<String> options = quizQuestion.getOptions();
//
//            Collections.shuffle(options);
//            int optionsCount = options.size();
//            RadioButton radioButton;
//            for (int i = 0; i < optionsCount; i++) {
//                radioButton = (RadioButton) radioGroup.getChildAt(i);
//                radioButton.setText(options.get(i));
//                radioButton.setVisibility(View.VISIBLE);
//                radioButton.setEnabled(radioButtonsEnabled);
//                radioButton.setChecked(options.get(i).equals(quizQuestion.getSelectedOptions()));
//
//            }
//        }
//    }
//    public interface onOptionSelectedListener{
//        void onOptionSelected(String selectedOption);
//
//    }
//    public boolean areAllQuestionsAnswered() {
//        return selectedAnswers.size() == quizQuestions.size();
//    }
//
//    public void setRadioButtonsEnabled(boolean enabled) {
//        radioButtonsEnabled = enabled;
//        notifyDataSetChanged();
//    }
//}
