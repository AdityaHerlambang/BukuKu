package com.adityaherlambang.bukuku.adapter;


import android.content.Context;
import android.content.Intent;
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
import com.adityaherlambang.bukuku.buku.EditBukuActivity;
import com.adityaherlambang.bukuku.buku.BukuActivity;
import com.adityaherlambang.bukuku.model.Buku;
import com.adityaherlambang.bukuku.model.BukuWithRelations;
import com.adityaherlambang.bukuku.model.Kategori;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import mehdi.sakout.fancybuttons.FancyButton;

public class BukuAdapter extends RecyclerView.Adapter<BukuAdapter.MyViewHolder> {
    private Context context;
    private List<BukuWithRelations> mBukuList;

    public BukuAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_buku, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BukuAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.mNama.setText(mBukuList.get(i).buku.getNamaBuku());
        myViewHolder.mHalaman.setText(mBukuList.get(i).buku.getHalaman());
        myViewHolder.mIsbn.setText(mBukuList.get(i).buku.getIsbn());
        myViewHolder.mBerat.setText(mBukuList.get(i).buku.getBerat());
        myViewHolder.mKategori.setText(mBukuList.get(i).kategoris.get(0).getNamaKategori());
        myViewHolder.mPenerbit.setText(mBukuList.get(i).penerbits.get(0).getNamaPenerbit());
    }

    @Override
    public int getItemCount() {
        if (mBukuList == null) {
            return 0;
        }
        return mBukuList.size();

    }

    public void setTasks(List<BukuWithRelations> bukuList) {
        mBukuList = bukuList;
        notifyDataSetChanged();
    }

    public List<BukuWithRelations> getTasks() {

        return mBukuList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mNama, mKategori, mPenerbit, mHalaman, mIsbn, mBerat;
        FancyButton mEdit, mDelete;
        AppDatabase mDb;
        CardView mCard;

        MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            mDb = AppDatabase.getInstance(context);
            mNama = itemView.findViewById(R.id.item_nama);
            mKategori = itemView.findViewById(R.id.item_kategori);
            mPenerbit = itemView.findViewById(R.id.item_penerbit);
            mHalaman = itemView.findViewById(R.id.item_halaman);
            mIsbn = itemView.findViewById(R.id.item_isbn);
            mBerat = itemView.findViewById(R.id.item_berat);
            mEdit = itemView.findViewById(R.id.item_btn_edit);
            mDelete = itemView.findViewById(R.id.item_btn_hapus);

            mCard = itemView.findViewById(R.id.item_card);

            mCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int bukuId = mBukuList.get(getAdapterPosition()).buku.getBukuId();
                    Intent i = new Intent(context, EditBukuActivity.class);
                    i.putExtra("detail", bukuId);
                    context.startActivity(i);
                }
            });


            mEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int bukuId = mBukuList.get(getAdapterPosition()).buku.getBukuId();
                    Intent i = new Intent(context, EditBukuActivity.class);
                    i.putExtra("update", bukuId);
                    context.startActivity(i);
                }
            });

            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final SweetAlertDialog sDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                    sDialog.setTitle("Hapus Data");
                    sDialog.setContentText("Ingin menghapus data "+mBukuList.get(getAdapterPosition()).buku.getNamaBuku()+" ?");
                    sDialog.setConfirmButton("Ya", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            final Buku buku = mBukuList.get(getAdapterPosition()).buku;

                            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    mDb.bukuDao().delete(buku);
                                }
                            });

                            ((BukuActivity) context).retrieveData();
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
