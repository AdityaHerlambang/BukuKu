package com.adityaherlambang.bukuku.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.adityaherlambang.bukuku.model.Buku;
import com.adityaherlambang.bukuku.model.BukuWithRelations;

import java.util.List;

@Dao
public interface BukuDao {

    @Query("SELECT * FROM buku ORDER BY buku_id")
    List<BukuWithRelations> loadAllBukus();

    @Insert
    void insertBuku(Buku buku);

    @Update
    void updateBuku(Buku buku);

    @Delete
    void delete(Buku buku);

    @Query("SELECT * FROM buku WHERE buku_id = :buku_id")
    BukuWithRelations loadBukuById(int buku_id);

}
