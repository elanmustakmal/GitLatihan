package com.example.belajarandroid.rest;

//import com.example.belajarandroid.Fragments.ViewFragment;
import com.example.belajarandroid.model.ApiResponse;
import com.example.belajarandroid.model.Book;
import com.example.belajarandroid.model.BookResult;
import com.example.belajarandroid.model.LoginBody;
import com.example.belajarandroid.model.LoginResult;
import com.example.belajarandroid.model.SignUpBody;
import com.example.belajarandroid.model.SignUpResult;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {


    @POST("login")
    Call<LoginResult> getResultInfo(@Body LoginBody loginBody);

    @POST("api/user")
    Call<SignUpResult> getSignUpInfo(@Body SignUpBody signUpBody);



//    @Headers({
//            "Accept-Encoding: gzip, deflate",
//            "Content-Encoding: gzip",
//    })
    @GET("api/buku")
    Call<List<Book>> getAllBuku(@Header("Authorization") String token);

    @POST("api/buku")
    Call<ApiResponse> insertNewBook(@Header("Authorization") String token, @Body Book book);

    @GET("api/buku/byId/{id}")
    Call<BookResult> getBookById(@Header("Authorization") String token, @Path("id") int id);

    @DELETE("api/buku")
    Call<ApiResponse> deleteBook(@Header("Authorization") String token, @Query("id") int id);

    @PUT("api/buku")
    Call<ApiResponse> updateBook(@Header("Authorization") String token, @Body Book body);

}
