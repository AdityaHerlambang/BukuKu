package com.adityaherlambang.bukuku.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class BukuWithRelations {
    @Embedded
    public Buku buku;

    @Relation(parentColumn = "penerbit_id", entityColumn = "penerbit_id", entity = Penerbit.class)
    public List<Penerbit> penerbits;

    @Relation(parentColumn = "kategori_id", entityColumn = "kategori_id", entity = Kategori.class)
    public List<Kategori> kategoris;


}
