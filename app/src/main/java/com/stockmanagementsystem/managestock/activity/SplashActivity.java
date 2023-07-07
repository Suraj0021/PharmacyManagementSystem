package com.stockmanagementsystem.managestock.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.stockmanagementsystem.managestock.R;

public class SplashActivity extends AppCompatActivity {
    public ImageView imageView;
    public TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageView = findViewById(R.id.logoImage);
        textView = findViewById(R.id.logoTextView);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.enter_from_left);
        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.enter_from_right);
        imageView.startAnimation(animation);
        textView.startAnimation(animation2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent iHome = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(iHome);
                finish();

            }
        }, 3000);



    }
}