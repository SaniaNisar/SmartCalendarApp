package com.app.smartcalendarapp;

import android.app.Activity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Apply animation to the FrameLayout (logo + background circle)
        FrameLayout logoContainer = findViewById(R.id.logoContainer);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.logo_animation);
        logoContainer.startAnimation(animation);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }, 5000);
    }
}
