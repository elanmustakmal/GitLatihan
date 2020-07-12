package com.example.belajarandroid.tab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.belajarandroid.R;

public class RecyclerViewBuku extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_buku);
        openFragmentBuku();
    }

    private void openFragmentBuku() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FragmentBuku strCode = new FragmentBuku();
        fragmentTransaction.replace(R.id.content, strCode, "fragment buku");
        fragmentTransaction.commit();
    }

}