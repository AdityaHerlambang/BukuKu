package com.adityaherlambang.bukuku.model;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.List;

@Entity(tableName = "buku")
public class Buku {
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name="buku_id")
    public int bukuId;
    @ColumnInfo(name="penerbit_id")
    public int penerbitId;
    @ColumnInfo(name="kategori_id")
    public int kategoriId;
    @ColumnInfo(name="nama_buku")
    public String namaBuku;
    @ColumnInfo(name="halaman")
    public String halaman;
    @ColumnInfo(name="isbn")
    public String isbn;
    @ColumnInfo(name="berat")
    public String berat;

    @Ignore
    public Buku(int penerbitId, int kategoriId, String namaBuku, String halaman, String isbn, String berat) {
        this.penerbitId = penerbitId;
        this.kategoriId = kategoriId;
        this.namaBuku = namaBuku;
        this.halaman = halaman;
        this.isbn = isbn;
        this.berat = berat;
    }

    public Buku(int bukuId, int penerbitId, int kategoriId, String namaBuku, String halaman, String isbn, String berat) {
        this.bukuId = bukuId;
        this.penerbitId = penerbitId;
        this.kategoriId = kategoriId;
        this.namaBuku = namaBuku;
        this.halaman = halaman;
        this.isbn = isbn;
        this.berat = berat;
    }

    public int getBukuId() {
        return bukuId;
    }

    public void setBukuId(int bukuId) {
        this.bukuId = bukuId;
    }

    public int getPenerbitId() {
        return penerbitId;
    }

    public void setPenerbitId(int penerbitId) {
        this.penerbitId = penerbitId;
    }

    public int getKategoriId() {
        return kategoriId;
    }

    public void setKategoriId(int kategoriId) {
        this.kategoriId = kategoriId;
    }

    public String getNamaBuku() {
        return namaBuku;
    }

    public void setNamaBuku(String namaBuku) {
        this.namaBuku = namaBuku;
    }

    public String getHalaman() {
        return halaman;
    }

    public void setHalaman(String halaman) {
        this.halaman = halaman;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getBerat() {
        return berat;
    }

    public void setBerat(String berat) {
        this.berat = berat;
    }
}
