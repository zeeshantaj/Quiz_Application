package com.example.quiz_application.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.quiz_application.MainActivity;
import com.example.quiz_application.R;

public class Splash_Screen_Activity extends AppCompatActivity {

    private Handler handler=new Handler();
    private static final int time = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splash_Screen_Activity.this, MainActivity.class));
                finish();
            }
        }, time);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(null);
    }
}