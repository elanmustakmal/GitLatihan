package com.example.belajarandroid.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.belajarandroid.R;

import java.util.ArrayList;
import java.util.List;

public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.MemberViewHolder> {

    private List<BookAdapter> bookAdapterList;

    public MemberListAdapter() {
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
    public MemberViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_buku, parent,false);
        MemberViewHolder memberViewHolder = new MemberViewHolder(view);
        return memberViewHolder;
    }

    @Override
    public void onBindViewHolder(MemberViewHolder holder, final int position) {
        final BookAdapter bookAdapter = bookAdapterList.get(position);

        holder.bookThumb.setImageResource(bookAdapter.getThumb());
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


}
