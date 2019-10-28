package com.adityaherlambang.bukuku.model;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "penerbit")
public class Penerbit {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="penerbit_id")
    public int penerbitId;

    @ColumnInfo(name="nama_penerbit")
    public String namaPenerbit;

    @Ignore
    public Penerbit(String namaPenerbit) {
        this.namaPenerbit = namaPenerbit;
    }

    public Penerbit(int penerbitId, String namaPenerbit) {
        this.penerbitId = penerbitId;
        this.namaPenerbit = namaPenerbit;
    }

    public int getPenerbitId() {
        return penerbitId;
    }

    public void setPenerbitId(int penerbitId) {
        this.penerbitId = penerbitId;
    }

    public String getNamaPenerbit() {
        return namaPenerbit;
    }

    public void setNamaPenerbit(String namaPenerbit) {
        this.namaPenerbit = namaPenerbit;
    }
}
