package com.example.belajarandroid.tab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.belajarandroid.MainActivity;
import com.example.belajarandroid.R;
import com.example.belajarandroid.model.LoginBody;
import com.example.belajarandroid.model.LoginResult;
import com.example.belajarandroid.rest.ApiClient;
import com.example.belajarandroid.rest.ApiInterface;
import com.example.belajarandroid.utils.AppService;
import com.example.belajarandroid.utils.Utility;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.belajarandroid.R.string.invalid_name;


public class Login extends AppCompatActivity {

    private Retrofit retrofit;
    EditText username, password;
    Button btnlogin;
    TextView btnsignup;

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Utility.askPermission(this);
        retrofit = ApiClient.initializeRetrofit();

        username = (EditText) findViewById(R.id.textusername);
        password = (EditText) findViewById(R.id.textpassword);
        btnlogin = (Button) findViewById(R.id.buttonlogin);
        btnsignup =  findViewById(R.id.buttonsignup);


        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signupIntent = new Intent(Login.this, SignUp.class);
                startActivity(signupIntent);
                finish();
            }
        });


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
//
                if (username.getText().toString().length() == 0) {
                    username.setError("username tidak boleh kosong");
                } else if (password.getText().toString().length() == 0) {
                    password.setError("Password tidak boleh kosong");
                } else {
                    Toast.makeText(getApplicationContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();
                    loginSubmit(username.getText().toString(), password.getText().toString());

                }
            }


        });
    }


    private void loginSubmit(String userName, String password) {

        LoginBody loginBody = new LoginBody(userName, password);

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<LoginResult> result = apiInterface.getResultInfo(loginBody);

        result.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                try {
                    if (response.body().isSuccess()) {
                        Log.e("TAG", "Login Success" + response.body().toString());
                        AppService.setToken("Bearer " + response.body().getToken());
                        Intent mainIntent = new Intent(Login.this, RecyclerViewBuku.class);
                        startActivity(mainIntent);
                        finish();

                    } else {
                        Log.e("TAG", "Login Gagal" + response.body().toString());
                        Toast.makeText(Login.this, "Loginmu Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                t.printStackTrace();

            }
        });


    }


}



