package com.adityaherlambang.bukuku.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.adityaherlambang.bukuku.model.Penerbit;

import java.util.List;

@Dao
public interface PenerbitDao {

    @Query("SELECT * FROM penerbit ORDER BY penerbit_id")
    List<Penerbit> loadAllPenerbits();

    @Insert
    void insertPenerbit(Penerbit penerbit);

    @Update
    void updatePenerbit(Penerbit penerbit);

    @Delete
    void delete(Penerbit penerbit);

    @Query("SELECT * FROM penerbit WHERE penerbit_id = :penerbit_id")
    Penerbit loadPenerbitById(int penerbit_id);

}
