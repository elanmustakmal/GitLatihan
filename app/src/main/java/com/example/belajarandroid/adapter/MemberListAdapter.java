package com.example.belajarandroid.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.belajarandroid.Fragments.ViewFragment;
import com.example.belajarandroid.R;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.MemberViewHolder> {

    Dialog myDialog;

//    private Context context;
//    FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();

    private List<BookAdapter> bookAdapterList;




    public MemberListAdapter() {
//
        bookAdapterList = new ArrayList<>();
    }

    private void add(BookAdapter item){
        bookAdapterList.add(item);
        notifyItemInserted(bookAdapterList.size() - 1);
    }

    public void addAll(List<BookAdapter> bookAdapterList) {
        for (BookAdapter bookAdapter : bookAdapterList) {
            add(bookAdapter);
        }
    }

    @Override
    public MemberViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_buku, parent,false);
        final MemberViewHolder memberViewHolder = new MemberViewHolder(view);

        myDialog = new Dialog(parent.getContext());
        myDialog.setContentView(R.layout.fragment_view);

        memberViewHolder.listbuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                TextView dialog_judul_tv = myDialog.findViewById(R.id.dialogjudul);
                TextView dialog_keterangan_tv = myDialog.findViewById(R.id.dialogketerangan);
                TextView dialog_penerbit_tv = myDialog.findViewById(R.id.dialogpenerbit);
                TextView dialog_tahun_tv = myDialog.findViewById(R.id.dialogtahun);
                TextView dialog_harga_tv = myDialog.findViewById(R.id.dialogharga);
                ImageView dialog_thumb_img = myDialog.findViewById(R.id.dialogthumb);

                dialog_judul_tv.setText(bookAdapterList.get(memberViewHolder.getAdapterPosition()).getJudul());
                dialog_keterangan_tv.setText(bookAdapterList.get(memberViewHolder.getAdapterPosition()).getPenulis());
                dialog_penerbit_tv.setText(bookAdapterList.get(memberViewHolder.getAdapterPosition()).getPenerbit());
                dialog_tahun_tv.setText(bookAdapterList.get(memberViewHolder.getAdapterPosition()).getTahun());
                dialog_harga_tv.setText(bookAdapterList.get(memberViewHolder.getAdapterPosition()).getHarga());
                dialog_thumb_img.setImageBitmap(getBitmap(bookAdapterList.get(memberViewHolder.getAdapterPosition()).getThumb()));

                myDialog.show();
            }
        });

        return memberViewHolder;
    }

    @Override
    public void onBindViewHolder(final MemberViewHolder holder, final int position) {

        final BookAdapter bookAdapter = bookAdapterList.get(position);

        Bitmap bitmap = getBitmap(bookAdapter.getThumb());

        holder.bookThumb.setImageBitmap(bitmap);
        holder.judul.setText(bookAdapter.getJudul());
        holder.penulis.setText(bookAdapter.getPenulis());

        holder.bookThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAG", "onBindViewHolder: " + bookAdapterList.get(position).getId());

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
        CardView listbuku;
        ImageView bookThumb;
        TextView judul;
        TextView penulis;


        public MemberViewHolder(View itemView) {
            super(itemView);
            listbuku = itemView.findViewById(R.id.list_buku);
            bookThumb = itemView.findViewById(R.id.thumb);
            judul = itemView.findViewById(R.id.judul);
            penulis = itemView.findViewById(R.id.penulis);

        }
    }

    private Bitmap getBitmap(String base64String) {
        byte[] decodedString = Base64.decode(base64String,Base64.DEFAULT);
        Bitmap decodeByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodeByte;
    }

}
