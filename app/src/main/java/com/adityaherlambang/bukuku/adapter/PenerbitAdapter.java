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
import com.adityaherlambang.bukuku.penerbit.EditPenerbitActivity;
import com.adityaherlambang.bukuku.penerbit.PenerbitActivity;
import com.adityaherlambang.bukuku.model.Penerbit;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import mehdi.sakout.fancybuttons.FancyButton;

public class PenerbitAdapter extends RecyclerView.Adapter<PenerbitAdapter.MyViewHolder> {
    private Context context;
    private List<Penerbit> mPenerbitList;

    public PenerbitAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_penerbit, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PenerbitAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.mNama.setText(mPenerbitList.get(i).getNamaPenerbit());
    }

    @Override
    public int getItemCount() {
        if (mPenerbitList == null) {
            return 0;
        }
        return mPenerbitList.size();

    }

    public void setTasks(List<Penerbit> penerbitList) {
        mPenerbitList = penerbitList;
        notifyDataSetChanged();
    }

    public List<Penerbit> getTasks() {

        return mPenerbitList;
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
                    int penerbitId = mPenerbitList.get(getAdapterPosition()).getPenerbitId();
                    Intent i = new Intent(context, EditPenerbitActivity.class);
                    i.putExtra("detail", penerbitId);
                    context.startActivity(i);
                }
            });


            mEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int penerbitId = mPenerbitList.get(getAdapterPosition()).getPenerbitId();
                    Intent i = new Intent(context, EditPenerbitActivity.class);
                    i.putExtra("update", penerbitId);
                    context.startActivity(i);
                }
            });

            mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final SweetAlertDialog sDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                    sDialog.setTitle("Hapus Data");
                    sDialog.setContentText("Ingin menghapus data "+mPenerbitList.get(getAdapterPosition()).getNamaPenerbit()+" ?");
                    sDialog.setConfirmButton("Ya", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            final Penerbit penerbit = mPenerbitList.get(getAdapterPosition());

                            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    mDb.penerbitDao().delete(penerbit);
                                }
                            });
                            ((PenerbitActivity) context).retrieveData();
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
