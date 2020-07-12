package com.example.belajarandroid.tab;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.belajarandroid.R;
import com.example.belajarandroid.adapter.BookAdapter;
import com.example.belajarandroid.adapter.MemberListAdapter;
import com.example.belajarandroid.model.Book;
import com.example.belajarandroid.rest.ApiClient;
import com.example.belajarandroid.rest.ApiInterface;
import com.example.belajarandroid.utils.AppService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FragmentBuku extends Fragment {

    private View view;
    private Retrofit retrofit;

    private String TAG = "fragmentbuku";

    private RecyclerView listMember;
    private LinearLayoutManager linearLayoutManager;
    private MemberListAdapter memberListAdapter;
    protected Context context;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_buku,container,false);

        initRetrofit();
        getAllBookData();
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerView();
    }

    private void initRecyclerView() {
        listMember = view.findViewById(R.id.listMember);
        linearLayoutManager = new LinearLayoutManager(context);
        memberListAdapter = new MemberListAdapter();
        listMember.setLayoutManager(linearLayoutManager);
        listMember.setAdapter(memberListAdapter);
    }

    private void initRetrofit() {
        retrofit = ApiClient.initializeRetrofit();
    }

    private void getAllBookData() {
        ApiInterface apiservice = retrofit.create(ApiInterface.class);
        Call<List<com.example.belajarandroid.model.Book>> result = apiservice.getAllBuku(AppService.getToken());
        result.enqueue(new Callback<List<com.example.belajarandroid.model.Book>>() {
            @Override
            public void onResponse(Call<List<com.example.belajarandroid.model.Book>> call, Response<List<com.example.belajarandroid.model.Book>> response) {
                addData(response.body());
            }

            @Override
            public void onFailure(Call<List<com.example.belajarandroid.model.Book>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void addData(List<Book> data) {
        List<BookAdapter> bookAdapterList = new ArrayList<>();
        BookAdapter bookAdapter;


        for (Book books : data) {
            Log.e(TAG, "addData: "+ books.getJudul() );
            bookAdapter = new BookAdapter();
            bookAdapter.setId(books.getId());
            bookAdapter.setJudul(books.getJudul());
            bookAdapter.setPenulis(books.getPenulis());
            bookAdapter.setThumb(books.getThumb());
            bookAdapterList.add(bookAdapter);
        }

        memberListAdapter.addAll(bookAdapterList);
    }


}