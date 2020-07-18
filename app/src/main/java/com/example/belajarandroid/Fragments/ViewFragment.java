package com.example.belajarandroid.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.belajarandroid.R;
import com.example.belajarandroid.model.ApiResponse;
import com.example.belajarandroid.model.Book;
import com.example.belajarandroid.model.BookResult;
import com.example.belajarandroid.rest.ApiClient;
import com.example.belajarandroid.rest.ApiInterface;
import com.example.belajarandroid.tab.RecyclerViewBuku;
import com.example.belajarandroid.utils.AppService;
import com.example.belajarandroid.utils.DialogUtility;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ViewFragment extends Fragment {

    private View view;
    private Retrofit retrofit;
    private String TAG = "viewfragment";

    private ImageView imageThumb;
    private TextInputEditText inputJudul, inputPenerbit, inputTahun, inputPenulis, inputHarga;
    private MaterialButton btnUpdate, btnDelete, btnSave, btnCancel, btnUpload;
    private LottieAnimationView animationView;
    private AlertDialog dlg;
    private LinearLayout layoutAction, layoutUpdate;
    public static final int PICK_IMAGE = 1;
    private String base64Image = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view, container, false);

        getData();
        initView();

        return view;
    }

    private void initView() {
        inputJudul = view.findViewById(R.id.inputJudul);
        inputPenerbit = view.findViewById(R.id.inputPenerbit);
        inputTahun = view.findViewById(R.id.inputTahun);
        inputPenulis = view.findViewById(R.id.inputPenulis);
        inputHarga = view.findViewById(R.id.inputHarga);
        imageThumb = view.findViewById(R.id.imageThumb);
        animationView = view.findViewById(R.id.animation);

        btnDelete = view.findViewById(R.id.btnDelete);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnSave = view.findViewById(R.id.btnSave);
        btnUpload = view.findViewById(R.id.btnUpload);

        layoutAction = view.findViewById(R.id.layoutAction);
        layoutUpdate = view.findViewById(R.id.layoutUpdate);

        animationView.setAnimation(R.raw.books);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFormDisable(true);
                layoutUpdate.setVisibility(View.VISIBLE);
                layoutAction.setVisibility(View.GONE);
                btnUpload.setVisibility(View.VISIBLE);
            }

        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shoConfirmDialog(R.raw.delete, "Delete Data Buku Ini..?", getContext());

            }

        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "save ");
                sendData(inputJudul.getText().toString(), inputPenulis.getText().toString(), inputPenerbit.getText().toString(), inputTahun.getText().toString(), inputHarga.getText().toString());

            }
             });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFormDisable(false);
                layoutUpdate.setVisibility(View.GONE);
                layoutAction.setVisibility(View.VISIBLE);
                btnUpload.setVisibility(View.GONE);
            }

        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }

        });
    }

    private void getData() {

        DialogUtility.showDialog(R.raw.plane, "Loading : Get Data Buku", getContext());
        retrofit = ApiClient.initializeRetrofit();

        ApiInterface apiService = retrofit.create(ApiInterface.class);
        Call<BookResult> result = apiService.getBookById(AppService.getToken(), AppService.getIdBuku());
        result.enqueue(new Callback<BookResult>() {
            @Override
            public void onResponse(Call<BookResult> call, Response<BookResult> response) {

                DialogUtility.closeAllDialog();
                if (response.body().isSuccess()) {

                    String judul = response.body().getRecord().getJudul() != null ? response.body().getRecord().getJudul() : "";
                    String penulis = response.body().getRecord().getPenulis() != null ? response.body().getRecord().getPenulis() : "";
                    String penerbit = response.body().getRecord().getPenerbit() != null ? response.body().getRecord().getPenerbit() : "";
                    String tahun = response.body().getRecord().getTahun() != null ? response.body().getRecord().getTahun() : "";
                    String harga = response.body().getRecord().getHarga() > 0 ? String.valueOf(response.body().getRecord().getHarga()) : "";

                    Log.e(TAG, "judul: " + judul);
                    Log.e(TAG, "penulis: " + penulis);
                    Log.e(TAG, "penerbit: " + penerbit);
                    Log.e(TAG, "tahun: " + tahun);
                    Log.e(TAG, "harga: " + harga);

                    inputJudul.setText(judul);
                    inputPenulis.setText(penulis);
                    inputPenerbit.setText(penerbit);
                    inputTahun.setText(tahun);
                    inputHarga.setText(harga);

                    setImageThumb(response.body().getRecord().getThumb());
                } else {
                    DialogUtility.showCustomDialog(R.raw.error, "Error : " + response.body().getMessage(), getContext());
                }
            }

            @Override
            public void onFailure(Call<BookResult> call, Throwable t) {
                t.printStackTrace();
                DialogUtility.closeAllDialog();
                DialogUtility.showCustomDialog(R.raw.error, "Error : " + t.getMessage(), getContext());
            }
        });
    }

    private void setFormDisable(boolean value) {
        inputJudul.setEnabled(value);
        inputTahun.setEnabled(value);
        inputHarga.setEnabled(value);
        inputPenerbit.setEnabled(value);
        inputPenulis.setEnabled(value);
    }

    private Bitmap setImageThumb(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageThumb.setImageBitmap(decodedByte);
        return decodedByte;
    }

    public void shoConfirmDialog(int id, String message, Context context) {
        LayoutInflater factory = LayoutInflater.from(context);
        final View viewDialog = factory.inflate(R.layout.custom_dialog_confirm, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        dlg = alertDialog.create();

        TextView txtPromptMessage = viewDialog.findViewById(R.id.txtPromptMessage);
        MaterialButton btnPromptOk = viewDialog.findViewById(R.id.btnPromptOk);
        MaterialButton btnCancel = viewDialog.findViewById(R.id.btnCancel);
        LottieAnimationView lottieAnimationView = viewDialog.findViewById(R.id.animation_view);

        lottieAnimationView.setAnimation(id);
        txtPromptMessage.setText(message);

        btnPromptOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAG", "delete");
                deleteBuku();
                dlg.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAG", "cancel: ");
                dlg.dismiss();
            }
        });

        dlg.setView(viewDialog);
        dlg.setCanceledOnTouchOutside(false);
        dlg.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dlg.show();

    }

    private void deleteBuku() {
        ApiInterface apiService = retrofit.create(ApiInterface.class);
        Call<ApiResponse> result = apiService.deleteBook(AppService.getToken(), AppService.getIdBuku());
        result.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Log.e(TAG, "onResponse: " + response.body().toString());
                dlg.dismiss();

                if (response.body().isSuccess()) {
                    showConfirmResult(R.raw.success, "Success Delete Data", getContext());
                    layoutUpdate.setVisibility(View.GONE);
                    layoutAction.setVisibility(View.VISIBLE);
                    btnUpload.setVisibility(View.GONE);
                } else {
                    DialogUtility.showCustomDialog(R.raw.error, "Error :" + response.body().getMessage(), getContext());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                t.printStackTrace();
                DialogUtility.closeAllDialog();
                DialogUtility.showCustomDialog(R.raw.error, "Error : " + t.getMessage(), getContext());
            }
        });
    }

    public void showConfirmResult(int id, String message, Context context) {
        LayoutInflater factory = LayoutInflater.from(context);
        final View viewDialog = factory.inflate(R.layout.dialog_custom, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        dlg = alertDialog.create();

        TextView txtPromptMessage = viewDialog.findViewById(R.id.txtPromptMessage);
        Button btnPromptOk = viewDialog.findViewById(R.id.btnPromptOk);
        LottieAnimationView lottieAnimationView = viewDialog.findViewById(R.id.animation_view1);

        lottieAnimationView.setAnimation(id);
        txtPromptMessage.setText(message);

        btnPromptOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RecyclerViewBuku) getActivity()).openHomeFragment();
                dlg.dismiss();
            }
        });

        dlg.setView(viewDialog);
        dlg.setCanceledOnTouchOutside(false);
        dlg.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dlg.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            Uri uri = data.getData();
            imageThumb.setImageURI(uri);
            InputStream imageStream;
            String encodedImage = "";

            imageThumb.getLayoutParams().height = 400;
            imageThumb.getLayoutParams().width = 300;

            try {
                imageStream = getContext().getContentResolver().openInputStream(uri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                encodedImage = encodedImage + encodeImage(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            base64Image = encodedImage;
        }
    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;
    }

    private void sendData(String judul, String penulis, String penerbit, String tahun, String harga) {

        DialogUtility.showDialog(R.raw.paperplane, "Loading", getContext());

        Book book = new Book();
        book.setHarga(Integer.valueOf(harga));
        book.setId(AppService.getIdBuku());
        book.setJudul(judul);
        book.setPenulis(penulis);
        book.setPenerbit(penerbit);
        book.setTahun(tahun);
        book.setThumb(base64Image);

        Log.e(TAG, "sendData: " + book.toString());

        ApiInterface apiService = retrofit.create(ApiInterface.class);
        Call<ApiResponse> result = apiService.updateBook(AppService.getToken(), book);
        result.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                Log.e(TAG, "onResponse: " + response.body().toString());



                if (response.body().isSuccess()) {
                    DialogUtility.closeAllDialog();
                    showConfirmResult(R.raw.success, "Success Edit Data", getContext());
                } else {
                    DialogUtility.showCustomDialog(R.raw.error, "Error :" + response.body().getMessage(), getContext());
                }

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                t.printStackTrace();
                DialogUtility.closeAllDialog();
                DialogUtility.showCustomDialog(R.raw.error, "Error : " + t.getMessage(), getContext());
            }
        });
    }


}
//
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Bundle;
//
//import androidx.cardview.widget.CardView;
//import androidx.fragment.app.DialogFragment;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentTransaction;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.util.Base64;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.airbnb.lottie.LottieAnimationView;
//import com.example.belajarandroid.R;
//import com.example.belajarandroid.adapter.BookAdapter;
//import com.example.belajarandroid.adapter.MemberListAdapter;
//import com.example.belajarandroid.model.ApiResponse;
//import com.example.belajarandroid.model.Book;
//import com.example.belajarandroid.model.Book;
//import com.example.belajarandroid.model.BookResult;
//import com.example.belajarandroid.rest.ApiClient;
//import com.example.belajarandroid.rest.ApiInterface;
//import com.example.belajarandroid.tab.Login;
//import com.example.belajarandroid.tab.RecyclerViewBuku;
//import com.example.belajarandroid.utils.AppService;
//import com.example.belajarandroid.utils.DialogUtility;
//import com.google.android.material.textfield.TextInputLayout;
//
//import java.io.ByteArrayOutputStream;
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//
//
//public class ViewFragment extends DialogFragment{
//
//    private CardView item_buku;
//    private EditText judulv, penerbitv, penulisv, tahunv, hargav;
//    private LottieAnimationView animationView;
//    private ImageView imageThumb;
//    private Button  edit, delete;
//    private AlertDialog altdialog;
//    public static final int PICK_IMAGE = 1;
//    private String TAG = "viewfragment";
//    private MemberListAdapter memberListAdapter;
//
//    Uri uri;
//
//    private View view;
//    private Retrofit retrofit;
//    private String base64Image = "";
//    private int id;
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//
//        view = inflater.inflate(R.layout.fragment_view, container, false);
//
//
////        edit.setOnClickListener(this);
////        delete.setOnClickListener(this);
//
//        getData();
//        initView();
//        initRetrofit();
//        return view;
//
//    }
//
//    private void initView(){
//        judulv = view.findViewById(R.id.dialogjudul);
//        penerbitv = view.findViewById(R.id.dialogpenerbit);
//        penulisv = view.findViewById(R.id.dialogketerangan);
//        tahunv = view.findViewById(R.id.dialogtahun);
//        hargav = view.findViewById(R.id.dialogharga);
//
//        edit = view.findViewById(R.id.dialogedit);
//        delete = view.findViewById(R.id.dialogdelete);
//
//        edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                showconfirmDialog(R.raw.delete, "anda yakin ingin menghapus data buku?", getContext());
//            }
//        });
//    }
//
//    private void getData() {
//        retrofit = ApiClient.initializeRetrofit();
//
//        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
//        Call<BookResult> result = apiInterface.getBookById(AppService.getToken(), AppService.getIdBuku());
//        result.enqueue(new Callback<BookResult>() {
//            @Override
//            public void onResponse(Call<BookResult> call, Response<BookResult> response) {
////                DialogUtility.closeAllDialog();
//                if (response.body().isSuccess()) {
//
//                    String judul = response.body().getRecord().getJudul() != null ? response.body().getRecord().getJudul() : "";
//                    String penulis = response.body().getRecord().getPenulis() != null ? response.body().getRecord().getPenulis() : "";
//                    String penerbit = response.body().getRecord().getPenerbit() != null ? response.body().getRecord().getPenerbit() : "";
//                    String tahun = response.body().getRecord().getTahun() != null ? response.body().getRecord().getTahun() : "";
//                    String harga = response.body().getRecord().getHarga() > 0 ? String.valueOf(response.body().getRecord().getHarga()) : "";
//
//                    Log.e(TAG, "judul: " + judul);
//                    Log.e(TAG, "penulis: " + penulis);
//                    Log.e(TAG, "penerbit: " + penerbit);
//                    Log.e(TAG, "tahun: " + tahun);
//                    Log.e(TAG, "harga: " + harga);
//
//                    judulv.setText(judul);
//                    penulisv.setText(penulis);
//                    penerbitv.setText(penerbit);
//                    tahunv.setText(tahun);
//                    hargav.setText(harga);
//
//                    setImageThumb(response.body().getRecord().getThumb());
//                } else {
//                    DialogUtility.showCustomDialog(R.raw.error, "Error : " + response.body().getMessage(), getContext());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<BookResult> call, Throwable t) {
//                t.printStackTrace();
//                DialogUtility.closeAllDialog();
////                DialogUtility.showCustomDialog(R.raw.error, "Error : " + t.getMessage(), getContext());
//            }
//        });
//    }
//
//
//    private void initRetrofit() {
//
//        retrofit = ApiClient.initializeRetrofit();
//    }
//
//    private void setFormDisable(boolean value) {
//        judulv.setEnabled(value);
//        tahunv.setEnabled(value);
//        hargav.setEnabled(value);
//        penerbitv.setEnabled(value);
//        penulisv.setEnabled(value);
//    }
//
//    private Bitmap setImageThumb(String base64String) {
//        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//        imageThumb.setImageBitmap(decodedByte);
//        return decodedByte;
//    }
//
//    public void showconfirmDialog(int id, String message, Context context){
//        LayoutInflater factory = LayoutInflater.from(context);
//        final View viewDialog = factory.inflate(R.layout.custom_dialog_confirm, null);
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
//        altdialog = alertDialog.create();
//
//        TextView txtprompmessage = viewDialog.findViewById(R.id.txtprompmessage);
//        Button btnok = viewDialog.findViewById(R.id.btnok);
//        Button btncancel = viewDialog.findViewById(R.id.btncancel);
//
//        txtprompmessage.setText(message);
//
//        btnok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.e(TAG, "onClick: delete" );
//                deleteBuku();
//                altdialog.dismiss();
//            }
//        });
//
//        btncancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.e(TAG, "onClick: cancel");
//                altdialog.dismiss();
//            }
//        });
//        altdialog.setView(viewDialog);
//        altdialog.setCanceledOnTouchOutside(false);
//        altdialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        altdialog.show();
//    }
//
//    private void deleteBuku(){
//        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
//        Call<ApiResponse> result = apiInterface.deleteBook(AppService.getToken(), AppService.getIdBuku());
//        result.enqueue(new Callback<ApiResponse>() {
//            @Override
//            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
//                Log.e(TAG, "onResponse: " + response.body().toString() );
//                altdialog.dismiss();
//
//                if (response.body().isSuccess()) {
//
////                    showconfirmResult(R.raw.success, "Delete data berhasil", getContext());
//                    Log.e("TAG", "Delete Success");
//                    Toast.makeText(getContext(), "Loginmu Error", Toast.LENGTH_SHORT).show();
//                } else {
////                    DialogUtility.showCustomDialog
//                    Log.e("TAG", "Delete Gagal" + response.body().toString());
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse> call, Throwable t) {
//                t.printStackTrace();
//                DialogUtility.closeAllDialog();
////                DialogUtility.showCustomDialog();
//
//            }
//        });
//
//    }
//
//    public void showConfirmResult(int id, String message, Context context){
//        LayoutInflater factory = LayoutInflater.from(context);
//        final View viewDialog = factory.inflate(R.layout.dialog_custom, null);
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
//        altdialog = alertDialog.create();
//
//        TextView txtPromptMessage = viewDialog.findViewById(R.id.txtprompmessage);
//        Button btnPrommptOk = viewDialog.findViewById(R.id.btnPromptOk);
//
//        txtPromptMessage.setText(message);
//
//        btnPrommptOk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ((RecyclerViewBuku) getActivity()).openHomeFragment();
//                altdialog.dismiss();
//            }
//        });
//        altdialog.setView(viewDialog);
//        altdialog.setCanceledOnTouchOutside(false);
//        altdialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        altdialog.show();
//
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == PICK_IMAGE) {
//            Uri uri = data.getData();
//            imageThumb.setImageURI(uri);
//            InputStream imageStream;
//            String encodedImage = "";
//
//            imageThumb.getLayoutParams().height = 400;
//            imageThumb.getLayoutParams().width = 300;
//
//            try {
//                imageStream = getContext().getContentResolver().openInputStream(uri);
//                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                encodedImage = encodedImage + encodeImage(selectedImage);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            base64Image = encodedImage;
//        }
//    }
//
//    private String encodeImage(Bitmap bm) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] b = baos.toByteArray();
//        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
//        return encImage;
//    }
//
//    private void sendData(String judul, String penulis, String penerbit, String tahun, String harga){
//        Book book = new Book();
//        book.setHarga(Integer.valueOf(harga));
//        book.setJudul(judul);
//        book.setPenulis(penulis);
//        book.setPenerbit(penerbit);
//        book.setTahun(tahun);
//        book.setThumb(base64Image);
//
//        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
//        Call<ApiResponse> result = apiInterface.updateBook(AppService.getToken(), book);
//        result.enqueue(new Callback<ApiResponse>() {
//            @Override
//            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
//
//                Log.e(TAG, "onResponse: " + response.body().toString() );
//
//                DialogUtility.closeAllDialog();
//
//                if (response.body().isSuccess()) {
//                    Log.e("TAG", "Input Success");
//                } else {
//                    Log.e("TAG", "Input Gagal" + response.body().toString());
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse> call, Throwable t) {
//                t.printStackTrace();
//                DialogUtility.closeAllDialog();
//
//            }
//        });
//
//    }
//
//}