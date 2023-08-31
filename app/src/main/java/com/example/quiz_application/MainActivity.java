package com.example.quiz_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.quiz_application.Api_Service.QuizApiResponse;
import com.example.quiz_application.Api_Service.QuizApiService;
import com.example.quiz_application.Api_Service.QuizQuestion;
import com.example.quiz_application.FragmentUtils.FragmentUtils;
import com.example.quiz_application.Fragments.Start_Quiz_Fragment;
import com.example.quiz_application.Network.NetworkUtils;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        CheckInternetAccess();
    }

    private void CheckInternetAccess() {
        if (NetworkUtils.isNetworkAvailable(this)){
            NetworkUtils.hasInternetAccess(new NetworkUtils.InternetAccessCallback() {
                @Override
                public void onInternetAccessResult(boolean hasInternetAccess) {
                    if (hasInternetAccess){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fragmentManager = getSupportFragmentManager();
                                FragmentUtils.setFragment(fragmentManager,R.id.fragmentContainer,new Start_Quiz_Fragment());
                                Toast.makeText(MainActivity.this, "You Are Online", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                    else {
                        // no service
                       // setContentView(R.layout.no_internet_connection);
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "You have no internet connection\nyou are offline", Snackbar.LENGTH_INDEFINITE);
                        snackbar.setActionTextColor(getResources().getColor(R.color.red));
                        snackbar.setAction("Check Again", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CheckInternetAccess();
                            }
                        });
                        snackbar.show();
                    }
                }
            });
        }
        else {
            // no internet connected
            //setContentView(R.layout.not_connected_to_internet);
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Make sure you are connected to the internet\nyou are offline", Snackbar.LENGTH_INDEFINITE);
            snackbar.setActionTextColor(getResources().getColor(R.color.red));
            snackbar.setAction("DISMISS", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackbar.dismiss();
                }
            });
            snackbar.show();
        }
    }
}