package com.adityaherlambang.bukuku.buku;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.adityaherlambang.bukuku.R;
import com.adityaherlambang.bukuku.adapter.BukuAdapter;
import com.adityaherlambang.bukuku.adapter.KategoriAdapter;
import com.adityaherlambang.bukuku.database.AppDatabase;
import com.adityaherlambang.bukuku.database.AppExecutors;
import com.adityaherlambang.bukuku.databinding.ActivityBukuBinding;
import com.adityaherlambang.bukuku.databinding.ActivityKategoriBinding;
import com.adityaherlambang.bukuku.kategori.EditKategoriActivity;
import com.adityaherlambang.bukuku.kategori.KategoriActivity;
import com.adityaherlambang.bukuku.model.Buku;
import com.adityaherlambang.bukuku.model.BukuWithRelations;
import com.adityaherlambang.bukuku.model.Kategori;

import java.util.List;

public class BukuActivity extends AppCompatActivity {

    ActivityBukuBinding binding;
    AppDatabase mDb;
    BukuAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_buku);
        setToolbar();

        binding.addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BukuActivity.this, EditBukuActivity.class));
            }
        });

        binding.mainRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new BukuAdapter(this);
        binding.mainRecyclerview.setAdapter(mAdapter);
        mDb = AppDatabase.getInstance(getApplicationContext());

    }

    private void setToolbar(){
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("List Buku");
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveData();
    }

    public void retrieveData() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<BukuWithRelations> data = mDb.bukuDao().loadAllBukus();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setTasks(data);
                    }
                });
            }
        });
    }
}
