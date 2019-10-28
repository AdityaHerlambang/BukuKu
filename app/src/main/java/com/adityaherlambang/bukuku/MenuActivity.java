package com.adityaherlambang.bukuku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.adityaherlambang.bukuku.buku.BukuActivity;
import com.adityaherlambang.bukuku.databinding.ActivityMenuBinding;
import com.adityaherlambang.bukuku.kategori.KategoriActivity;
import com.adityaherlambang.bukuku.model.BukuWithRelations;
import com.adityaherlambang.bukuku.penerbit.PenerbitActivity;

public class MenuActivity extends AppCompatActivity {

    ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu);

        binding.toolbar.setTitle("BukuKu");

        binding.btnMenuKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, KategoriActivity.class);
                startActivity(intent);
            }
        });

        binding.btnMenuPenerbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, PenerbitActivity.class);
                startActivity(intent);
            }
        });

        binding.btnMenuBuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, BukuActivity.class);
                startActivity(intent);
            }
        });

    }
}
