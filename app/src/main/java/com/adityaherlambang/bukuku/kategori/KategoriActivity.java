package com.adityaherlambang.bukuku.kategori;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.adityaherlambang.bukuku.R;
import com.adityaherlambang.bukuku.adapter.KategoriAdapter;
import com.adityaherlambang.bukuku.database.AppDatabase;
import com.adityaherlambang.bukuku.database.AppExecutors;
import com.adityaherlambang.bukuku.databinding.ActivityKategoriBinding;
import com.adityaherlambang.bukuku.model.Kategori;

import java.util.List;

public class KategoriActivity extends AppCompatActivity {

    ActivityKategoriBinding binding;
    AppDatabase mDb;
    KategoriAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_kategori);
        setToolbar();

        binding.addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KategoriActivity.this, EditKategoriActivity.class));
            }
        });


        binding.mainRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new KategoriAdapter(this);
        binding.mainRecyclerview.setAdapter(mAdapter);
        mDb = AppDatabase.getInstance(getApplicationContext());

    }

    private void setToolbar(){
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("List Kategori");
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
                final List<Kategori> data = mDb.kategoriDao().loadAllKategoris();
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
