package com.example.belajarandroid.tab;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.belajarandroid.R;
import com.example.belajarandroid.model.Book;
import com.example.belajarandroid.rest.ApiClient;
import com.example.belajarandroid.rest.ApiInterface;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class AddFragment extends Fragment implements View.OnClickListener {

    private TextInputLayout judul, penerbit, penulis, tahun, harga;
    Button addimage, addbook;

    private View view;
    private Retrofit retrofit;


    private String TAG = "addfragment";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add, container, false);
        initRetrofit();
        tambahData();

//        chart = v.findViewById(R.id.chart);

        judul = view.findViewById(R.id.textjudulbuku);
        penerbit = view.findViewById(R.id.textpenerbit);
        penulis = view.findViewById(R.id.textpenulis);
        tahun = view.findViewById(R.id.texttahun);
        harga = view.findViewById(R.id.harga);
        addimage = view.findViewById(R.id.btnimage);
        Button addbook = view.findViewById(R.id.btnaddbook);
        addbook.setOnClickListener(this);
        return view;

    }

    private void initRetrofit() {
        retrofit = ApiClient.initializeRetrofit();
    }

    private void tambahData() {


    }


    private boolean validatejudul() {

        String judulinput = judul.getEditText().getText().toString().trim();

        if (judulinput.isEmpty()) {
            judul.setError("Field can't be empty");
            return false;
        } else {
            judul.setError(null);
            return true;
        }


    }

    private boolean validatepenerbit() {

        String penerbitinput = penerbit.getEditText().getText().toString().trim();

        if (penerbitinput.isEmpty()) {
            penerbit.setError("Field can't be empty");
            return false;
        } else {
            penerbit.setError(null);
            return true;
        }


    }

    private boolean validatepenulis() {

        String penulisinput = penulis.getEditText().getText().toString().trim();

        if (penulisinput.isEmpty()) {
            penulis.setError("Field can't be empty");
            return false;
        } else {
            penulis.setError(null);
            return true;
        }


    }

    private boolean validatetahun() {

        String tahuninput = tahun.getEditText().getText().toString().trim();

        if (tahuninput.isEmpty()) {
            tahun.setError("Field can't be empty");
            return false;
        }  else {
            tahun.setError(null);
            return true;
        }


    }

    private boolean validateharga() {

        String hargainput = harga.getEditText().getText().toString().trim();

        if (hargainput.isEmpty()) {
            harga.setError("Field can't be empty");
            return false;
        } else {
            harga.setError(null);
            return true;
        }


    }

//    public void confirmTambahBuku (View v) {
//
//        if (!validatejudul() | !validatepenulis() | validatepenerbit() | validatetahun() | validateharga()) {
//            return;
//        }
//
//        String input = "name: " + judul.getEditText().getText().toString();
//        input += "\n";
//        input += "Password: " + penulis.getEditText().getText().toString();
//        input += "\n";
//        input += "Password: " + penerbit.getEditText().getText().toString();
//        input += "\n";
//        input += "Password: " + tahun.getEditText().getText().toString();
//        input += "\n";
//        input += "Password: " + harga.getEditText().getText().toString();
//        input += "\n";
//
//        Toast.makeText(getContext(), input, Toast.LENGTH_SHORT).show();
//
//
//    }

    private void bukuSubmit(String judul, String penerbit, String penulis, int tahun, int harga) {

        final Book book = new Book(judul, penerbit, penulis, tahun, harga);

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<ResponseBody> result = apiInterface.getResultInput(book);

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {Map<String, Object> retMap = new Gson().fromJson(response.body().string(),
                        new TypeToken<HashMap<String, Object>>(){

                        }.getType());
                    boolean success = (boolean) retMap.get("Success");
                    if (success) {
                        Log.e("TAG", "Login Success" + response.body().toString());
                        Intent mainIntent = new Intent(getActivity(), RecyclerViewBuku.class);
                        startActivity(mainIntent);
//                        finish();

                    } else {
                        Log.e("TAG", "Input Gagal" + response.body().toString());
                        Toast.makeText(getActivity(), "Input Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();

            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnaddbook :
                if (!validatejudul() | !validatepenulis() | validatepenerbit() | validatetahun() | validateharga()) {
                    Toast.makeText(getActivity(), "Input Success", Toast.LENGTH_SHORT).show();
                    return;
                }

                String input = "name: " + judul.getEditText().getText().toString();
                input += "\n";
                input += "Password: " + penulis.getEditText().getText().toString();
                input += "\n";
                input += "Password: " + penerbit.getEditText().getText().toString();
                input += "\n";
                input += "Password: " + tahun.getEditText().getText().toString();
                input += "\n";
                input += "Password: " + harga.getEditText().getText().toString();
                input += "\n";

                Toast.makeText(getContext(), input, Toast.LENGTH_SHORT).show();

                break;


        }

    }
}