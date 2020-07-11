package com.example.belajarandroid.rest;

import com.example.belajarandroid.model.LoginBody;
import com.example.belajarandroid.model.LoginResult;
import com.example.belajarandroid.model.SignUpBody;
import com.example.belajarandroid.model.SignUpResult;
import com.example.belajarandroid.tab.Login;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiInterface {
    @POST("login")
    Call<LoginResult> getResultInfo(@Body LoginBody loginBody);

    @POST("api/user")
    Call<SignUpResult> getSignUpInfo(@Body SignUpBody signUpBody);

}
