package com.example.belajarandroid.tab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.belajarandroid.R;
import com.example.belajarandroid.database.DatabaseHelper;

public class SignUp extends AppCompatActivity {
    DatabaseHelper db;
    EditText username, password, passwordConf, email, name;
    Button btnsignup, btnlogin;
    Spinner roles;
    RadioButton aktif, nonaktif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        db = new DatabaseHelper(this);

        username = (EditText) findViewById(R.id.textusernamesu);
        password = (EditText) findViewById(R.id.textpasswordsu);
        passwordConf = (EditText) findViewById(R.id.textpasswordconf);
        email = (EditText) findViewById(R.id.textemail);
        name = (EditText) findViewById(R.id.textname);
        btnsignup = (Button) findViewById(R.id.buttonsignupsu);
        btnlogin = (Button) findViewById(R.id.buttonloginsu);
        roles = (Spinner) findViewById(R.id.roles);
        aktif = (RadioButton) findViewById(R.id.aktif);
        nonaktif = (RadioButton) findViewById(R.id.nonaktif);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(SignUp.this, Login.class);
                startActivity(loginIntent);
                finish();
            }
        });


        aktif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              boolean checked = ((RadioButton) view).isChecked();
              switch (view.getId()) {
                  case R.id.aktif:
                      if (checked){
                          Toast.makeText(getApplicationContext(), "Anda Aktif", Toast.LENGTH_SHORT).show();
                      } else {

                      }
              }
            }
        });
        nonaktif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((RadioButton) view).isChecked();
                switch (view.getId()) {
                    case R.id.nonaktif:
                        if (checked){
                            Toast.makeText(getApplicationContext(), "Anda Tidak Aktif", Toast.LENGTH_SHORT).show();
                        } else {

                        }
                }
            }
        });






        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUsername = username.getText().toString();
                String strPassword = password.getText().toString();
                String strPasswordConf = passwordConf.getText().toString();
                String strEmail = email.getText().toString();
                String strName = name.getText().toString();
                String newRoles = String.valueOf(roles.getSelectedItem());
                String newAktif = String.valueOf(aktif);
                String newNonAktif = String.valueOf(nonaktif);
                if (strPassword.equals(strPasswordConf)){
                    Boolean signup = db.insertUser(strUsername, strPassword, strEmail, strName, newRoles, newAktif, newNonAktif);
                    if (signup == true){
                        Toast.makeText(getApplicationContext(),"Sign Up Berhasil", Toast.LENGTH_SHORT).show();
                        Intent loginIntent = new Intent(SignUp.this, Login.class);
                        startActivity(loginIntent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),"Sign Up Gagal", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Password Tidak Cocok", Toast.LENGTH_SHORT).show();
                }
            }
        });

        }
    }






































