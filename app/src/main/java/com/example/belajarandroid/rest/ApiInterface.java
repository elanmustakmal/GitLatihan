package com.example.belajarandroid.rest;

import com.example.belajarandroid.model.Book;
import com.example.belajarandroid.model.LoginBody;
import com.example.belajarandroid.model.LoginResult;
import com.example.belajarandroid.model.SignUpBody;
import com.example.belajarandroid.model.SignUpResult;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("login")
    Call<LoginResult> getResultInfo(@Body LoginBody loginBody);

    @POST("api/user")
    Call<SignUpResult> getSignUpInfo(@Body SignUpBody signUpBody);

    @GET("api/buku")
    Call<List<Book>> getAllBuku(@Header("Authorization") String token);

    @POST("api/buku")
    Call<ResponseBody> getResultInput(@Body Book book);

}
