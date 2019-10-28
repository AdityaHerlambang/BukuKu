package com.adityaherlambang.bukuku.kategori;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.adityaherlambang.bukuku.R;
import com.adityaherlambang.bukuku.database.AppDatabase;
import com.adityaherlambang.bukuku.database.AppExecutors;
import com.adityaherlambang.bukuku.databinding.ActivityKategoriBinding;
import com.adityaherlambang.bukuku.databinding.ActivityKategoriEditBinding;
import com.adityaherlambang.bukuku.model.Kategori;

public class EditKategoriActivity extends AppCompatActivity {

    ActivityKategoriEditBinding binding;

    private AppDatabase mDb;
    Intent intent;
    int mKategoriId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_kategori_edit);
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
                mKategoriId = intent.getIntExtra("update", -1);
            }
            else{
                binding.btnSimpan.setVisibility(View.GONE);
                ab.setTitle("Detail");
                mKategoriId = intent.getIntExtra("detail", -1);
            }


            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    Kategori kategori = mDb.kategoriDao().loadKategoriById(mKategoriId);
                    setUiData(kategori);
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

    private void setUiData(final Kategori kategori){
        if (kategori == null) {
            return;
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.nama.setText(kategori.getNamaKategori());
            }
        });

    }

    private void onSubmit(){
        final Kategori data = new Kategori(
                binding.nama.getText().toString()
        );

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (!intent.hasExtra("update")) {
                    mDb.kategoriDao().insertKategori(data);
                } else {
                    data.setKategoriId(mKategoriId);
                    mDb.kategoriDao().updateKategori(data);
                }
                finish();
            }
        });
    }

}
