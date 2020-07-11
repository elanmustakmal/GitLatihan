package com.example.belajarandroid.tab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.belajarandroid.MainActivity;
import com.example.belajarandroid.R;
import com.example.belajarandroid.model.LoginBody;
import com.example.belajarandroid.model.LoginResult;
import com.example.belajarandroid.model.SignUpBody;
import com.example.belajarandroid.model.SignUpResult;
import com.example.belajarandroid.rest.ApiClient;
import com.example.belajarandroid.rest.ApiInterface;
import com.example.belajarandroid.utils.Utility;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.security.auth.login.LoginException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.belajarandroid.R.string.invalid_name;

public class SignUp extends AppCompatActivity {

    private Retrofit retrofit;
    EditText username, password, passwordConf, email, name;
    Button btnsignup, btnlogin;
    Spinner roles;
    RadioButton aktif, nonaktif;
    private boolean rbaktif;

    AwesomeValidation awesomeValidation;

    public boolean harusDiisi(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(Html.fromHtml("<font color='red'>Input tidak boleh kosong</font>"));
            return false;
        }

        return true;
    }


    public Boolean daftar(String username, String password, String email, String name, String roles, String aktif, String nonaktif) {

        return daftar(username, password, email, name, roles, aktif, nonaktif);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Utility.askPermission(this);
        retrofit = ApiClient.initializeRetrofit();

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


        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(SignUp.this, R.id.textusernamesu,
                RegexTemplate.NOT_EMPTY, invalid_name);

        awesomeValidation.addValidation(SignUp.this, R.id.textpasswordsu,
                RegexTemplate.NOT_EMPTY, R.string.password_Kosong);

//        awesomeValidation.addValidation(SignUp.this, R.id.textpasswordsu,
//                "4", R.string.invalid_password);

        awesomeValidation.addValidation(SignUp.this, R.id.textpasswordconf,
                RegexTemplate.NOT_EMPTY, R.string.konfirmasi_Kosong);

        awesomeValidation.addValidation(SignUp.this, R.id.textpasswordconf,
                R.id.textpasswordsu, R.string.invalid_confirm_password);

        awesomeValidation.addValidation(SignUp.this, R.id.textemail,
                RegexTemplate.NOT_EMPTY, R.string.email_Kosong);

        awesomeValidation.addValidation(SignUp.this, R.id.textemail,
                Patterns.EMAIL_ADDRESS, R.string.invalid_email);

        awesomeValidation.addValidation(SignUp.this, R.id.textname,
                RegexTemplate.NOT_EMPTY, R.string.invalid_nama);

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
                        if (checked) {
                            Toast.makeText(getApplicationContext(), "Anda Aktif", Toast.LENGTH_SHORT).show();
                            rbaktif = true;
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
                        if (checked) {
                            Toast.makeText(getApplicationContext(), "Anda Tidak Aktif", Toast.LENGTH_SHORT).show();
                            rbaktif = false;
                        } else {

                        }
                }
            }
        });


        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (awesomeValidation.validate()) {

                    List<String> roles1 = new ArrayList<>();
                    roles1.add(roles.getSelectedItem().toString());

                    SignUpSubmit(username.getText().toString(), password.getText().toString(),
                            email.getText().toString(), name.getText().toString(), roles1, rbaktif);


                } else {
                    Toast.makeText(getApplicationContext(), "Sign Up Gagal", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void SignUpSubmit(String username, String password, String email, String nama, List<String> roles, boolean aktif) {

        SignUpBody signUpBody = new SignUpBody(username, password, email, nama, roles, aktif);

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<SignUpResult> result = apiInterface.getSignUpInfo(signUpBody);

        result.enqueue(new Callback<SignUpResult>() {
            @Override
            public void onResponse(Call<SignUpResult> call, Response<SignUpResult> response) {
                boolean success = response.body().isSuccess();
                Log.e("tag", "onResponse: " + success);
                try {
                    if (response.body().isSuccess()) {
                        Log.e("TAG", "Sign Up Success" + response.body().toString());
                        Intent LoginIntent = new Intent(SignUp.this, Login.class);
                        startActivity(LoginIntent);
                        finish();

                    } else {
                        Toast.makeText(SignUp.this, "Sign Up Gagal : " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SignUpResult> call, Throwable t) {
                t.printStackTrace();

            }
        });


    }
}













































