package com.adityaherlambang.bukuku.buku;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.adityaherlambang.bukuku.R;
import com.adityaherlambang.bukuku.database.AppDatabase;
import com.adityaherlambang.bukuku.database.AppExecutors;
import com.adityaherlambang.bukuku.databinding.ActivityEditBukuBinding;
import com.adityaherlambang.bukuku.model.Buku;
import com.adityaherlambang.bukuku.model.BukuWithRelations;
import com.adityaherlambang.bukuku.model.Kategori;
import com.adityaherlambang.bukuku.model.Penerbit;

import java.util.ArrayList;
import java.util.List;

public class EditBukuActivity extends AppCompatActivity {

    ActivityEditBukuBinding binding;

    private AppDatabase mDb;
    Intent intent;
    int mBukuId;

    // you need to have a list of data that you want the spinner to display
    List<String> kategoriDisplay;
    List<Integer> kategoriValue;

    List<String> penerbitDisplay;
    List<Integer> penerbitValue;

    ArrayAdapter<String> adapterKategori;
    ArrayAdapter<String> adapterPenerbit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_buku);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mDb = AppDatabase.getInstance(getApplicationContext());

        setSpinners();

        setSupportActionBar(binding.toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        intent = getIntent();
        if ((intent != null && intent.hasExtra("update")) || (intent != null && intent.hasExtra("detail"))) {
            if(intent.hasExtra("update")){
                binding.btnSimpan.setText("Update");
                ab.setTitle("Update");
                mBukuId = intent.getIntExtra("update", -1);
            }
            else{
                binding.btnSimpan.setVisibility(View.GONE);
                ab.setTitle("Detail");
                mBukuId = intent.getIntExtra("detail", -1);
            }


            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    BukuWithRelations buku = mDb.bukuDao().loadBukuById(mBukuId);
                    setUiData(buku);
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

    private void setSpinners(){
        kategoriDisplay =  new ArrayList<String>();
        kategoriValue =  new ArrayList<Integer>();

        penerbitDisplay =  new ArrayList<String>();
        penerbitValue =  new ArrayList<Integer>();

        adapterKategori = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, kategoriDisplay);

        adapterKategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.kategori.setAdapter(adapterKategori);

        adapterPenerbit = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, penerbitDisplay);

        adapterPenerbit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.penerbit.setAdapter(adapterPenerbit);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Kategori> kategoriList = mDb.kategoriDao().loadAllKategoris();
                List<Penerbit> penerbitList = mDb.penerbitDao().loadAllPenerbits();

                for(Kategori kategori : kategoriList){
                    kategoriDisplay.add(kategori.namaKategori);
                    kategoriValue.add(kategori.kategoriId);
                }

                for(Penerbit penerbit : penerbitList){
                    penerbitDisplay.add(penerbit.namaPenerbit);
                    penerbitValue.add(penerbit.penerbitId);
                }

                adapterKategori.notifyDataSetChanged();
                adapterPenerbit.notifyDataSetChanged();

            }
        });
    }

    private void setUiData(final BukuWithRelations bukuWithRelations){
        if (bukuWithRelations == null) {
            return;
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.nama.setText(bukuWithRelations.buku.getNamaBuku());
                binding.halaman.setText(bukuWithRelations.buku.getHalaman());
                binding.berat.setText(bukuWithRelations.buku.getBerat());
                binding.isbn.setText(bukuWithRelations.buku.getIsbn());
                binding.kategori.setSelection(penerbitValue.indexOf(bukuWithRelations.kategoris.get(0).kategoriId));
                binding.penerbit.setSelection(penerbitValue.indexOf(bukuWithRelations.penerbits.get(0).penerbitId));
            }
        });

    }

    private void onSubmit(){
        final Buku data = new Buku(
                penerbitValue.get(binding.penerbit.getSelectedItemPosition()),
                kategoriValue.get(binding.kategori.getSelectedItemPosition()),
                binding.nama.getText().toString(),
                binding.halaman.getText().toString(),
                binding.isbn.getText().toString(),
                binding.berat.getText().toString()
        );

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (!intent.hasExtra("update")) {
                    mDb.bukuDao().insertBuku(data);
                } else {
                    data.setBukuId(mBukuId);
                    mDb.bukuDao().updateBuku(data);
                }
                finish();
            }
        });
    }

}
