package com.example.quiz_application.Fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quiz_application.FragmentUtils.FragmentUtils;
import com.example.quiz_application.R;
import com.example.quiz_application.ViewModel.SharedViewModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Start_Quiz_Fragment extends Fragment {


    public Start_Quiz_Fragment() {
        // Required empty public constructor
    }

    private TextView howToPlay;
    private View view;
    private Spinner typeSpinner,difficultySpinner, categorySpinner;
    private Button startQuizBtn, startRandomQuizBtn;
    private FragmentManager fragmentManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_start__quiz_, container, false);
        howToPlay = view.findViewById(R.id.howToPlayText);
        typeSpinner = view.findViewById(R.id.selectTypeSpinner);
        difficultySpinner = view.findViewById(R.id.selectDifficultiesSpinner);
        categorySpinner = view.findViewById(R.id.selectCategorySpinner);
        startQuizBtn = view.findViewById(R.id.startQuizBtn);
        startRandomQuizBtn = view.findViewById(R.id.startRandomQuiz);
        fragmentManager = getActivity().getSupportFragmentManager();
        howToPlay.setOnClickListener((view1 -> {
            showInstructionDialog();
        }));

        startQuizBtn.setOnClickListener((view1 -> {
            String type = typeSpinner.getSelectedItem().toString();
            String difficulty = difficultySpinner.getSelectedItem().toString();
            String categories = categorySpinner.getSelectedItem().toString();

            if (type.equals("Select Type")){
                Toast.makeText(getActivity(), "please select type", Toast.LENGTH_SHORT).show();
                return;
            }
            if (difficulty.equals("Select Difficulties")){
                Toast.makeText(getActivity(), "please select Difficulty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (categories.equals("Select Category")){
                Toast.makeText(getActivity(), "please select Category", Toast.LENGTH_SHORT).show();
                return;
            }

            if (type.equals("True/False")){
                type = "boolean";
            }
            if (type.equals("multiple choice questions")){
                type = "multiple";
            }

            int categoryIndex = categorySpinner.getSelectedItemPosition();
            int category = categoryIndex + 7;



            SharedViewModel sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
            sharedViewModel.setSelectedCategoryIndex(category);
            sharedViewModel.setSelectedType(type);
            sharedViewModel.setSelectedDifficulty(difficulty);

            FragmentUtils.setFragment(fragmentManager,R.id.fragmentContainer,new Quiz_Select_Fragment());

        }));


        startRandomQuizBtn.setOnClickListener((view1 -> {

            FragmentUtils.setFragment(fragmentManager,R.id.fragmentContainer,new Quiz_Random_Fragment());
            //FragmentUtils.setFragment(fragmentManager,R.id.fragmentContainer,new Random_Quiz_Fragment());
        }));


        return view;
    }

    private void showInstructionDialog() {

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.how_to_layout);

        Button closeButton = dialog.findViewById(R.id.closeBtn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the dialog
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}