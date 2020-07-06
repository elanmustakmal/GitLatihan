package com.example.belajarandroid.tab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.belajarandroid.R;

public class Login extends AppCompatActivity {

    LinearLayout rellay1;
    LinearLayout rellay2;
    LinearLayout rellay3;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
            rellay3.setVisibility(View.VISIBLE);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        rellay1 = (LinearLayout) findViewById(R.id.rellay1);
        rellay2 = (LinearLayout) findViewById(R.id.rellay2);
        rellay3 = (LinearLayout) findViewById(R.id.rellay3);

        handler.postDelayed(runnable, 5000);

    }
}