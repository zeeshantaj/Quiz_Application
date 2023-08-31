package com.example.quiz_application.FragmentUtils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentUtils {
    public static void setFragment(FragmentManager fragmentManager, int fragmentContainer, Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(fragmentContainer, fragment);
        // Only add to back stack if the fragment being added is not the default fragment
        if (!(fragment instanceof Start_Quiz_Fragment)) {
            fragmentTransaction.addToBackStack(null);
        }
       // fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
