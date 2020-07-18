package com.example.belajarandroid.tab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.belajarandroid.Fragments.AddFragment;
import com.example.belajarandroid.Fragments.FragmentBuku;
import com.example.belajarandroid.Fragments.ViewFragment;
import com.example.belajarandroid.R;
import com.example.belajarandroid.adapter.MemberListAdapter;
import com.example.belajarandroid.model.Book;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Retrofit;

public class RecyclerViewBuku extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_buku);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);




    }



    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = new FragmentBuku();

                    switch (item.getItemId()){
                        case R.id.nav_home :
                            selectedFragment = new FragmentBuku();

                            break;
                        case R.id.nav_add:
                            selectedFragment = new AddFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.content, selectedFragment).commit();
                    return true;
                }

            };

    public void openHomeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentBuku strcode = new FragmentBuku();
        fragmentTransaction.replace(R.id.content, strcode, "fragment buku");
        fragmentTransaction.commit();
    }

    public void openAddFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AddFragment addFragment = new AddFragment();
        fragmentTransaction.replace(R.id.content, addFragment, "fragment tambah");
        fragmentTransaction.commit();
    }

    public void openViewFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ViewFragment viewFragment = new ViewFragment();
        fragmentTransaction.replace(R.id.content, viewFragment, "fragment view");
        fragmentTransaction.commit();
    }




}