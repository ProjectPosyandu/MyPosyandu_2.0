package com.example.myposyandu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.myposyandu.R;
import com.example.myposyandu.SharedPrefManager;

public class SplashActivity extends AppCompatActivity {
    private int waktu_loading=4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent login =new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(login);
                finish();
            }
        },waktu_loading);
    }

}
