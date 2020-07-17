package com.example.belajarandroid.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.belajarandroid.MainActivity;
import com.example.belajarandroid.R;
import com.example.belajarandroid.tab.Login;


public class SplashScreen extends AppCompatActivity {

    ImageView imgLogo;


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LinearLayout linearLayout = findViewById(R.id.splash);
                AnimationDrawable animationDrawable =(AnimationDrawable) linearLayout.getBackground();
                animationDrawable.setEnterFadeDuration(1000);
                animationDrawable.setExitFadeDuration(2000);
                animationDrawable.start();
                Intent panggil = new Intent(SplashScreen.this, Login.class);
                startActivity(panggil);
                finish();

            }
        }, 3000);
    }
}