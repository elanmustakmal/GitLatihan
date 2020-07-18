package com.example.belajarandroid.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.belajarandroid.Fragments.FragmentBuku;
import com.example.belajarandroid.R;
import com.example.belajarandroid.utils.AppService;

import java.util.ArrayList;
import java.util.List;

public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.MemberViewHolder> {

    private List<BookAdapter> bookAdapterList;
    private Context mcontext;

    private FragmentBuku fragment;


    public MemberListAdapter(Context mcontext, FragmentBuku fragment) {
        bookAdapterList = new ArrayList<>();
        this.mcontext = mcontext;
        this.fragment = fragment;
    }

    private void add(BookAdapter item) {
        bookAdapterList.add(item);
        notifyItemInserted(bookAdapterList.size() - 1);
    }

    public void addAll(List<BookAdapter> bookAdapterList) {
        for (BookAdapter bookAdapter : bookAdapterList) {
            add(bookAdapter);
        }
    }

    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_buku, parent, false);
        MemberViewHolder memberViewHolder = new MemberViewHolder(view);
        return memberViewHolder;
    }

    @Override
    public void onBindViewHolder(MemberViewHolder holder, final int position) {
        final BookAdapter bookAdapter = bookAdapterList.get(position);

        Bitmap bitmap = getBitmap(bookAdapter.getThumb());

        holder.bookThumb.setImageBitmap(bitmap);
        holder.judul.setText(bookAdapter.getJudul());
        holder.penulis.setText(bookAdapter.getPenulis());

        int bukuId = bookAdapterList.get(position).getId();

        holder.bookThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAG", "onBindViewHolder: " + bookAdapterList.get(position).getId());
                int IdBuku = bookAdapterList.get(position).getId();
                fragment.openFragmentView(IdBuku);
                AppService.setIdBuku(IdBuku);
            }
        });

        holder.judul.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.e("TAG", "long clik listener: ");
                return true;
            }
        });

        holder.bookThumb.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.e("TAG", "long click listener: ");
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookAdapterList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    static class MemberViewHolder extends RecyclerView.ViewHolder {
        ImageView bookThumb;
        TextView judul;
        TextView penulis;
        int id;

        public MemberViewHolder(View itemView) {
            super(itemView);

            bookThumb = itemView.findViewById(R.id.thumb);
            judul = itemView.findViewById(R.id.judul);
            penulis = itemView.findViewById(R.id.penulis);
        }
    }

    private Bitmap getBitmap(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return decodedByte;
    }
}



//
//import android.app.Dialog;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.util.Base64;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.cardview.widget.CardView;
//import androidx.fragment.app.DialogFragment;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.belajarandroid.Fragments.FragmentBuku;
//import com.example.belajarandroid.Fragments.ViewFragment;
//import com.example.belajarandroid.R;
//import com.example.belajarandroid.model.ApiResponse;
//import com.example.belajarandroid.model.Book;
//import com.example.belajarandroid.rest.ApiClient;
//import com.example.belajarandroid.rest.ApiInterface;
//import com.example.belajarandroid.utils.AppService;
//
//import org.w3c.dom.Text;
//
//import java.io.ByteArrayOutputStream;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.zip.Inflater;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//
//public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.MemberViewHolder> {
//
////    Dialog myDialog;
////    Retrofit retrofit;
//
////    private Context context;
////    FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
//
//    private List<BookAdapter> bookAdapterList;
////    private OnItemClickListener mListener;
//
////    dari mas dwi :
//    private FragmentBuku fragment;
//    private Context mcontext;
//
////    public interface OnItemClickListener {
////        void onItemClick(int position);
////        void onDeleteClick(int position);
////    }
//
////    public void setOnItemClickListener(OnItemClickListener listener){
////        mListener = listener;
////    }
//
//
//    public MemberListAdapter(Context mcontext, FragmentBuku fragment) {
//        bookAdapterList = new ArrayList<>();
//        this.mcontext = mcontext;
//        this.fragment = fragment;
//    }
//
//    private void add(BookAdapter item){
//        bookAdapterList.add(item);
//        notifyItemInserted(bookAdapterList.size() - 1);
//    }
//
//    public void addAll(List<BookAdapter> bookAdapterList) {
//        for (BookAdapter bookAdapter : bookAdapterList) {
//            add(bookAdapter);
//        }
//    }
//
////    private void initRetrofit() {
////        retrofit = ApiClient.initializeRetrofit();
////    }
//
//    @Override
//    public MemberViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_buku, parent,false);
//        MemberViewHolder memberViewHolder = new MemberViewHolder(view);
//
//        return memberViewHolder;
//    }
//
//
//
//    @Override
//    public void onBindViewHolder(final MemberViewHolder holder, final int position) {
//
//        final BookAdapter bookAdapter = bookAdapterList.get(position);
//
//        Bitmap bitmap = getBitmap(bookAdapter.getThumb());
//
//        holder.bookThumb.setImageBitmap(bitmap);
//        holder.judul.setText(bookAdapter.getJudul());
//        holder.penulis.setText(bookAdapter.getPenulis());
//
////        dari mas dwi
//        final int bukuId = bookAdapterList.get(position).getId();
//
//        holder.bookThumb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.e("TAG", "onBindViewHolder: " + bookAdapterList.get(position).getId());
//                fragment.openFragmentView(bukuId);
//                AppService.setIdBuku(bukuId);
//
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return bookAdapterList.size();
//    }
//
//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//    }
//
//    @Override
//    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
//        super.onDetachedFromRecyclerView(recyclerView);
//    }
//
//    public static class MemberViewHolder extends RecyclerView.ViewHolder {
//        CardView listbuku;
//        ImageView bookThumb;
//        TextView judul;
//        TextView penulis;
//        public ImageButton deletelist;
//
//
//        public MemberViewHolder(View itemView) {
//            super(itemView);
//            listbuku = itemView.findViewById(R.id.list_buku);
//            deletelist = itemView.findViewById(R.id.listdelete);
//            bookThumb = itemView.findViewById(R.id.thumb);
//            judul = itemView.findViewById(R.id.judul);
//            penulis = itemView.findViewById(R.id.penulis);
//
//        }
//    }
//
//    private Bitmap getBitmap(String base64String) {
//        byte[] decodedString = Base64.decode(base64String,Base64.DEFAULT);
//        Bitmap decodeByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//        return decodeByte;
//    }
//
//}
