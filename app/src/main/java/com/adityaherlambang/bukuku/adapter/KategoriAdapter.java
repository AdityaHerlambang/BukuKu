package com.adityaherlambang.bukuku.adapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.adityaherlambang.bukuku.R;
import com.adityaherlambang.bukuku.database.AppDatabase;
import com.adityaherlambang.bukuku.database.AppExecutors;
import com.adityaherlambang.bukuku.kategori.EditKategoriActivity;
import com.adityaherlambang.bukuku.kategori.KategoriActivity;
import com.adityaherlambang.bukuku.model.Kategori;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import mehdi.sakout.fancybuttons.FancyButton;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.MyViewHolder> {
    private Context context;
    private List<Kategori> mKategoriList;

    public KategoriAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_kategori, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KategoriAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.mNama.setText(mKategoriList.get(i).getNamaKategori());
    }

    @Override
    public int getItemCount() {
        if (mKategoriList == null) {
            return 0;
        }
        return mKategoriList.size();

    }

    public void setTasks(List<Kategori> kategoriList) {
        mKategoriList = kategoriList;
        notifyDataSetChanged();
    }

    public List<Kategori> getTasks() {

        return mKategoriList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mNama;
        FancyButton mEdit, mDelete;
        AppDatabase mDb;
        CardView mCard;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            mDb = AppDatabase.getInstance(context);
            mNama = itemView.findViewById(R.id.item_nama);
            mEdit = itemView.findViewById(R.id.item_btn_edit);
            mDelete = itemView.findViewById(R.id.item_btn_hapus);

            mCard = itemView.findViewById(R.id.item_card);

            mCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int kategoriId = mKategoriList.get(getAdapterPosition()).getKategoriId();
                    Intent i = new Intent(context, EditKategoriActivity.class);
                    i.putExtra("detail", kategoriId);
                    context.startActivity(i);
                }
            });


            mEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int kategoriId = mKategoriList.get(getAdapterPosition()).getKategoriId();
                    Intent i = new Intent(context, EditKategoriActivity.class);
                    i.putExtra("update", kategoriId);
                    context.startActivity(i);
                }
            });

            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final SweetAlertDialog sDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                    sDialog.setTitle("Hapus Data");
                    sDialog.setContentText("Ingin menghapus data "+mKategoriList.get(getAdapterPosition()).getNamaKategori()+" ?");
                    sDialog.setConfirmButton("Ya", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            final Kategori kategori = mKategoriList.get(getAdapterPosition());

                            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    mDb.kategoriDao().delete(kategori);
                                }
                            });
                            ((KategoriActivity) context).retrieveData();
                            sDialog.dismissWithAnimation();
                        }
                    });
                    sDialog.setCancelButton("Tidak", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    });
                    sDialog.show();
                }
            });
        }
    }
}
