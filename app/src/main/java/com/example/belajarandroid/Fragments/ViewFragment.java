package com.example.belajarandroid.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.belajarandroid.R;
import com.example.belajarandroid.adapter.BookAdapter;
import com.example.belajarandroid.adapter.MemberListAdapter;
import com.example.belajarandroid.model.ApiResponse;
import com.example.belajarandroid.model.Book;
import com.example.belajarandroid.model.Book;
import com.example.belajarandroid.model.BookResult;
import com.example.belajarandroid.rest.ApiClient;
import com.example.belajarandroid.rest.ApiInterface;
import com.example.belajarandroid.utils.AppService;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ViewFragment extends DialogFragment implements View.OnClickListener{

    private CardView item_buku;
    private TextView judul, penerbit, penulis, tahun, harga;
    Button  edit, delete;

    private String TAG = "viewfragment";
    private MemberListAdapter memberListAdapter;

    Uri uri;

    private View view;
    private Retrofit retrofit;
    private String base64Image = "";
    private int id;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_view, container, false);

        judul = view.findViewById(R.id.dialogjudul);
        penerbit = view.findViewById(R.id.dialogpenerbit);
        penulis = view.findViewById(R.id.dialogketerangan);
        tahun = view.findViewById(R.id.dialogtahun);
        harga = view.findViewById(R.id.dialogharga);

        edit = view.findViewById(R.id.dialogedit);
        delete = view.findViewById(R.id.dialogdelete);
        edit.setOnClickListener(this);
        delete.setOnClickListener(this);

        initRetrofit();
        return view;

    }

    private void initRetrofit() {

        retrofit = ApiClient.initializeRetrofit();
    }

//    private void addData(List<Book> data) {
//        List<BookAdapter> bookAdapterList = new ArrayList<>();
//        BookAdapter bookAdapter;
//
//
//        for (Book books : data) {
//            Log.e(TAG, "addData: "+ books.getJudul() );
//            bookAdapter = new BookAdapter();
//            bookAdapter.setId(books.getId());
//            bookAdapter.setJudul(books.getJudul());
//            bookAdapter.setPenulis(books.getPenulis());
//            bookAdapter.setPenerbit(books.getPenerbit());
//            bookAdapter.setHarga(String.valueOf(books.getHarga()));
//            bookAdapter.setTahun(books.getTahun());
//            bookAdapter.setThumb(books.getThumb());
//            bookAdapterList.add(bookAdapter);
//        }
//
//        memberListAdapter.addAll(bookAdapterList);
//    }
//
//    private void deleteBookData() {
//        ApiInterface apiservice = retrofit.create(ApiInterface.class);
//        Call<ApiResponse> result = apiservice.deleteBook(AppService.getToken(), id);
//        result.enqueue(new Callback<ApiResponse>() {
//            @Override
//            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
//                addData(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//    }


    private void hapusData(String judul, String penulis, String penerbit, String tahun, String harga) {
        Book book = new Book();
        book.setHarga(Integer.valueOf(harga));
        book.setJudul(judul);
        book.setPenulis(penulis);
        book.setPenerbit(penerbit);
        book.setTahun(tahun);
        book.setThumb(base64Image);
        book.getId();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<ApiResponse> result = apiInterface.deleteBook(AppService.getToken(), id);
        result.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                if (response.body().isSuccess()) {
                    Log.e("TAG", "Delete Success");
                } else {
                    Log.e("TAG", "Delete Gagal" + response.body().toString());
                }

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dialogdelete :
                if (true) {
//                    hapusData(judul.().getText().toString(), penulis.getEditText().getText().toString(), penerbit.getEditText().getText().toString(),tahun.getEditText().getText().toString(),
//                            harga.getEditText().getText().toString());
                    Toast.makeText(getActivity(), "Input Success", Toast.LENGTH_SHORT).show();
                    return;
                }

//                String input = "judul: " + judul.getEditText().getText().toString();
//                input += "\n";
//                input += "Penulis: " + penulis.getEditText().getText().toString();
//                input += "\n";
//                input += "Penerbit: " + penerbit.getEditText().getText().toString();
//                input += "\n";
//                input += "tahun: " + tahun.getEditText().getText().toString();
//                input += "\n";
//                input += "harga: " + harga.getEditText().getText().toString();
//                input += "\n";

//                Toast.makeText(getContext(), input, Toast.LENGTH_SHORT).show();

                break;


        }

        switch (view.getId()){
            case R.id.dialogedit :
                if (true) {
//                    setAddimage();

                    Toast.makeText(getActivity(), "Input Success", Toast.LENGTH_SHORT).show();
                    return;
                }


                break;
        }

    }
}