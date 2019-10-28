package com.adityaherlambang.bukuku.model;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "kategori")
public class Kategori {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="kategori_id")
    public int kategoriId;

    @ColumnInfo(name="nama_kategori")
    public String namaKategori;


    @Ignore
    public Kategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }

    public Kategori(int kategoriId, String namaKategori) {
        this.kategoriId = kategoriId;
        this.namaKategori = namaKategori;
    }

    public int getKategoriId() {
        return kategoriId;
    }

    public void setKategoriId(int kategoriId) {
        this.kategoriId = kategoriId;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }
}
