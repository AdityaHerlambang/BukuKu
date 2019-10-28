package com.adityaherlambang.bukuku.database;


import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.adityaherlambang.bukuku.model.Buku;
import com.adityaherlambang.bukuku.model.Kategori;
import com.adityaherlambang.bukuku.model.Penerbit;


@Database(entities = {Buku.class, Kategori.class, Penerbit.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "db_bukuku";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Menginstansiasi database");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Mengambil database instance");
        return sInstance;
    }

    public abstract BukuDao bukuDao();
    public abstract KategoriDao kategoriDao();
    public abstract PenerbitDao penerbitDao();
}
