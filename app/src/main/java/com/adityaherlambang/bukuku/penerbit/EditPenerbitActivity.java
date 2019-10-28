package com.adityaherlambang.bukuku.penerbit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.adityaherlambang.bukuku.R;
import com.adityaherlambang.bukuku.database.AppDatabase;
import com.adityaherlambang.bukuku.database.AppExecutors;
import com.adityaherlambang.bukuku.databinding.ActivityEditPenerbitBinding;
import com.adityaherlambang.bukuku.databinding.ActivityKategoriEditBinding;
import com.adityaherlambang.bukuku.model.Kategori;
import com.adityaherlambang.bukuku.model.Penerbit;

public class EditPenerbitActivity extends AppCompatActivity {

    ActivityEditPenerbitBinding binding;

    private AppDatabase mDb;
    Intent intent;
    int mPenerbitId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_penerbit);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setSupportActionBar(binding.toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        mDb = AppDatabase.getInstance(getApplicationContext());

        intent = getIntent();
        if ((intent != null && intent.hasExtra("update")) || (intent != null && intent.hasExtra("detail"))) {
            if(intent.hasExtra("update")){
                binding.btnSimpan.setText("Update");
                ab.setTitle("Update");
                mPenerbitId = intent.getIntExtra("update", -1);
            }
            else{
                binding.btnSimpan.setVisibility(View.GONE);
                ab.setTitle("Detail");
                mPenerbitId = intent.getIntExtra("detail", -1);
            }


            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    Penerbit penerbit = mDb.penerbitDao().loadPenerbitById(mPenerbitId);
                    setUiData(penerbit);
                }
            });
        }

        binding.btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });

    }

    private void setUiData(final Penerbit penerbit){
        if (penerbit == null) {
            return;
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.nama.setText(penerbit.getNamaPenerbit());
            }
        });

    }

    private void onSubmit(){
        final Penerbit data = new Penerbit(
                binding.nama.getText().toString()
        );

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (!intent.hasExtra("update")) {
                    mDb.penerbitDao().insertPenerbit(data);
                } else {
                    data.setPenerbitId(mPenerbitId);
                    mDb.penerbitDao().updatePenerbit(data);
                }
                finish();
            }
        });
    }
}
