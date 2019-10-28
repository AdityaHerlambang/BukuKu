package com.adityaherlambang.bukuku.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.adityaherlambang.bukuku.model.Kategori;

import java.util.List;

@Dao
public interface KategoriDao {

    @Query("SELECT * FROM kategori ORDER BY kategori_id")
    List<Kategori> loadAllKategoris();

    @Insert
    void insertKategori(Kategori kategori);

    @Update
    void updateKategori(Kategori kategori);

    @Delete
    void delete(Kategori kategori);

    @Query("SELECT * FROM kategori WHERE kategori_id = :kategori_id")
    Kategori loadKategoriById(int kategori_id);

}
