package com.example.belajarandroid.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

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

//        inisialisasi object
        imgLogo = (ImageView) findViewById(R.id.splash_imgLogo);

//        sumber animasi
        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.fadein);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fadeout);

//        implementasi

        imgLogo.startAnimation(animation);
        imgLogo.startAnimation(animation1);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent panggil = new Intent(SplashScreen.this, Login.class);
                startActivity(panggil);
                finish();

            }
        }, 5000);
    }
}