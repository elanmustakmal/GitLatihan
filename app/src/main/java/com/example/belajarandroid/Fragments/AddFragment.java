package com.example.belajarandroid.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.belajarandroid.R;
import com.example.belajarandroid.model.ApiResponse;
import com.example.belajarandroid.model.Book;
import com.example.belajarandroid.rest.ApiClient;
import com.example.belajarandroid.rest.ApiInterface;
import com.example.belajarandroid.utils.AppService;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class AddFragment extends Fragment implements View.OnClickListener {

    public static final int PICK_IMAGE = 1;
    private String base64Image = "";

    private TextInputLayout judul, penerbit, penulis, tahun, harga;
    Button addimage, addbook;
    ImageView imageView;
    Uri uri;

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
//        chart = v.findViewById(R.id.chart);

        judul = view.findViewById(R.id.textjudulbuku);
        penerbit = view.findViewById(R.id.textpenerbit);
        penulis = view.findViewById(R.id.textpenulis);
        tahun = view.findViewById(R.id.texttahun);
        harga = view.findViewById(R.id.harga);
        addimage = view.findViewById(R.id.btnaddimage);
        imageView = view.findViewById(R.id.gambarbuku);
        addbook = view.findViewById(R.id.btnaddbook);
        addbook.setOnClickListener(this);
        addimage.setOnClickListener(this);

        return view;

    }

    private void initRetrofit() {
        retrofit = ApiClient.initializeRetrofit();
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


    private void setAddimage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 60, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            Uri uri = data.getData();
            InputStream imageStream;
            String encodedImage = "";

            try {
                imageStream = getContext().getContentResolver().openInputStream(uri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                encodedImage = encodeImage(selectedImage);
                imageView.setImageURI(uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            base64Image = encodedImage;
        }
    }

    private void sendData(String judul, String penulis, String penerbit, String tahun, String harga) {
        Book book = new Book();
        book.setHarga(Integer.valueOf(harga));
        book.setJudul(judul);
        book.setPenulis(penulis);
        book.setPenerbit(penerbit);
        book.setTahun(tahun);
        book.setThumb(base64Image);

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<ApiResponse> result = apiInterface.insertNewBook(AppService.getToken(), book);
        result.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                    if (response.body().isSuccess()) {
                        Log.e("TAG", "Input Success");
                    } else {
                        Log.e("TAG", "Input Gagal" + response.body().toString());
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
            case R.id.btnaddbook :
                if (!validatejudul() | !validatepenulis() | validatepenerbit() | validatetahun() | validateharga()) {
                    sendData(judul.getEditText().getText().toString(), penulis.getEditText().getText().toString(), penerbit.getEditText().getText().toString(),tahun.getEditText().getText().toString(),
                            harga.getEditText().getText().toString());
                      Toast.makeText(getActivity(), "Input Success", Toast.LENGTH_SHORT).show();
                    return;
                }

                String input = "judul: " + judul.getEditText().getText().toString();
                input += "\n";
                input += "Penulis: " + penulis.getEditText().getText().toString();
                input += "\n";
                input += "Penerbit: " + penerbit.getEditText().getText().toString();
                input += "\n";
                input += "tahun: " + tahun.getEditText().getText().toString();
                input += "\n";
                input += "harga: " + harga.getEditText().getText().toString();
                input += "\n";

                Toast.makeText(getContext(), input, Toast.LENGTH_SHORT).show();

                break;


        }

        switch (view.getId()){
            case R.id.btnaddimage :
                if (!validatejudul() | !validatepenulis() | validatepenerbit() | validatetahun() | validateharga()) {
                    setAddimage();

                    Toast.makeText(getActivity(), "Input Success", Toast.LENGTH_SHORT).show();
                    return;
                }
//

               break;
        }


    }

}