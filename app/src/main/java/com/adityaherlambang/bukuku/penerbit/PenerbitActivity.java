package com.adityaherlambang.bukuku.penerbit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.adityaherlambang.bukuku.R;
import com.adityaherlambang.bukuku.adapter.PenerbitAdapter;
import com.adityaherlambang.bukuku.database.AppDatabase;
import com.adityaherlambang.bukuku.database.AppExecutors;
import com.adityaherlambang.bukuku.databinding.ActivityPenerbitBinding;
import com.adityaherlambang.bukuku.model.Penerbit;

import java.util.List;

public class PenerbitActivity extends AppCompatActivity {

    ActivityPenerbitBinding binding;
    AppDatabase mDb;
    PenerbitAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_penerbit);
        setToolbar();

        binding.addFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PenerbitActivity.this, EditPenerbitActivity.class));
            }
        });


        binding.mainRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new PenerbitAdapter(this);
        binding.mainRecyclerview.setAdapter(mAdapter);
        mDb = AppDatabase.getInstance(getApplicationContext());

    }

    private void setToolbar(){
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("List Penerbit");
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
                final List<Penerbit> data = mDb.penerbitDao().loadAllPenerbits();
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
