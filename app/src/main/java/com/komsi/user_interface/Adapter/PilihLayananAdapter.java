package com.komsi.user_interface.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.komsi.user_interface.Activities.DetailActivity.DetailLayanan;
import com.komsi.user_interface.Activities.DaftarKonsultasi.DaftarKonsul_pilihPsikolog;
import com.komsi.user_interface.R;
import com.komsi.user_interface.models.Model_Layanan;
import com.squareup.picasso.Picasso;

import java.util.List;


public class PilihLayananAdapter extends RecyclerView.Adapter<PilihLayananAdapter.Pilih_LayananViewHolder>{
    private Context mCtx;
    private List<Model_Layanan> layananListPilih;
    Dialog myDialog;

    public PilihLayananAdapter(Context mCtx, List<Model_Layanan> layananListPilih) {
        this.mCtx = mCtx;
        this.layananListPilih = layananListPilih;
    }

    @NonNull
    @Override
    public Pilih_LayananViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.pilihlayanan_listlayout, parent, false);
        return new Pilih_LayananViewHolder(view);
    }

    @Override
    public void onBindViewHolder( final Pilih_LayananViewHolder holder, final int position) {
        final Model_Layanan layanan = layananListPilih.get(position);

        holder.namaLayanan.setText(layanan.getNama());
        holder.deskripsiLayanan.setText(layanan.getDeskripsi());
        holder.hargaLayanan.setText("Rp "+layanan.getHarga()+",-");
        holder.bt_detail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mCtx, DetailLayanan.class);
                intent.putExtra("foto", layanan.getFoto());
                intent.putExtra("namaLayanan", layanan.getNama());
                intent.putExtra("deskripsi", layanan.getDeskripsi());
                intent.putExtra("harga", layanan.getHarga());
                mCtx.startActivity(intent);


            }
        });
        holder.bt_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.bt_batal.setVisibility(View.VISIBLE);
                holder.bt_daftar.setVisibility(View.GONE);
                holder.bt_lanjutDaftar.setVisibility(View.VISIBLE);


            }
        });
        holder.bt_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.bt_batal.setVisibility(View.GONE);
                holder.bt_daftar.setVisibility(View.VISIBLE);
                holder.bt_lanjutDaftar.setVisibility(View.GONE);
            }
        });
        holder.bt_lanjutDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = mCtx.getSharedPreferences("DaftarKonsul", mCtx.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("layanan", layanan.getNama());
                editor.putInt("idLayanan", layanan.getId());
                editor.putString("harga", layanan.getHarga());
                editor.apply();

                    Intent lanjut = new Intent(view.getContext(), DaftarKonsul_pilihPsikolog.class);
                    mCtx.startActivity(lanjut);

            }
        });

        SharedPreferences sharedPref = mCtx.getSharedPreferences("linkFoto", Context.MODE_PRIVATE);
        String link = sharedPref.getString("link", null);
        Picasso.with(mCtx).load(link+layanan.getFoto()).error(R.drawable.konsul)
                .into(holder.check);
    }

    @Override
    public int getItemCount() {
       return layananListPilih.size();
    }

    class Pilih_LayananViewHolder extends RecyclerView.ViewHolder{

        TextView namaLayanan, deskripsiLayanan, hargaLayanan, pilihanLayanan;
        Button bt_detail1, bt_daftar, bt_batal, bt_lanjutDaftar;
        ImageView check;

        public Pilih_LayananViewHolder(View itemView) {
            super(itemView);
            namaLayanan = itemView.findViewById(R.id.namaLayanan);
            pilihanLayanan = itemView.findViewById(R.id.pilihanLayanan);
            deskripsiLayanan = itemView.findViewById(R.id.deskripsiLayanan);
            hargaLayanan = itemView.findViewById(R.id.hargaLayanan);
            bt_detail1 = itemView.findViewById(R.id.bt_detail1);
            bt_daftar = itemView.findViewById(R.id.bt_daftar);
            bt_batal = itemView.findViewById(R.id.bt_batal);
            bt_lanjutDaftar = itemView.findViewById(R.id.bt_lanjutDaftar);
            check = itemView.findViewById(R.id.check);

        }

    }


}
