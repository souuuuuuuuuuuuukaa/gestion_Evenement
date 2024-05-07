package com.example.evenement_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class SplashScreen extends AppCompatActivity {

    ImageView imagelogo ;
    ProgressBar bar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imagelogo=findViewById(R.id.logo);
        bar=findViewById(R.id.progressBar);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Create an Intent to navigate to the other activity
                Intent intent = new Intent(SplashScreen.this,Login.class);

                // Start the new activity
                startActivity(intent);

                // Finish the current activity if needed
                finish();
            }
        }, 6000);
    }
}