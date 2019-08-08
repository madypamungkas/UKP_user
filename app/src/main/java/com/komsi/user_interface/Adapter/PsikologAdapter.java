package com.komsi.user_interface.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.komsi.user_interface.Activities.DaftarKonsultasi.DaftarKonsul_keluhan;
import com.komsi.user_interface.Activities.DaftarKonsultasi.DaftarKonsul_pilihPsikolog;
import com.komsi.user_interface.Activities.DetailActivity.DetailPsikolog;
import com.komsi.user_interface.R;
import com.komsi.user_interface.models.PsikologDetail_list;
import com.komsi.user_interface.models.PsikologModel;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;


public class PsikologAdapter extends RecyclerView.Adapter<PsikologAdapter.PsikologViewHolder> {
    private Context mCtx;
    private int mSelectedItem = -1;
    private List<PsikologModel> psikologModelList;

    public PsikologAdapter(Context mCtx, List<PsikologModel> psikologModelList) {
        this.mCtx = mCtx;
        this.psikologModelList = psikologModelList;
    }

    @NonNull
    @Override
    public PsikologAdapter.PsikologViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.psikolog_list_layout, parent, false);
        return new PsikologViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PsikologAdapter.PsikologViewHolder holder, final int position) {
        final PsikologModel psikologModel = psikologModelList.get(position);

        holder.namaPsikolog.setText(psikologModel.getName() + " " + psikologModel.getPsikolog().getGelar());
        holder.jkPsikolog.setText(psikologModel.getJenis_kelamin());
      //  holder.keahlianPsikolog.setText(psikologModel.getPsikolog().getNo_sipp());
        holder.keahlianPsikolog.setText(psikologModel.getPsikolog().getId()+" ");

        SharedPreferences sharedPrefFCM = mCtx.getSharedPreferences("linkFoto", Context.MODE_PRIVATE);
        String link = sharedPrefFCM.getString("link", null);
        final String url = link + psikologModel.getFoto();
        Picasso.with(mCtx).load(url).error(R.drawable.menu_icon_user).into(holder.avatarPsikolog);

        holder.listLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.buttonLayout.setVisibility(View.VISIBLE);
            }
        });

        holder.pilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mCtx, DetailPsikolog.class);
                intent.putExtra("namaPsikolog", psikologModel.getName()
                        + " " + psikologModel.getPsikolog().getGelar());
                intent.putExtra("noSipp", psikologModel.getPsikolog().getNo_sipp());
                intent.putExtra("foto", psikologModel.getFoto());
                intent.putExtra("jenisKelaminPsikolog", psikologModel.getJenis_kelamin());
                intent.putExtra("idLayanan", holder.layanan);
                intent.putExtra("idPsikolog", psikologModel.getPsikolog().getId());
                int yearOB, age, kategori_id = 0;

                String[] yob = psikologModel.getTanggal_lahir().split(" ");
                yearOB = Integer.parseInt(yob[2]);
                Calendar today = Calendar.getInstance();
                int yearNow = today.get(Calendar.YEAR);

                if (yearOB == today.get(Calendar.YEAR)) {
                    age = 1;
                } else {
                    age = yearNow - yearOB;
                }
                intent.putExtra("umur",age +"");

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
                editor.putString("psikolog", psikologModel.getName());
                editor.putString("gelar", psikologModel.getPsikolog().getGelar());
                editor.putInt("idPsikolog", psikologModel.getPsikolog().getId());

                // editor.putString("keahlian", psikologModel.get());
                editor.apply();


                Intent lanjut = new Intent(view.getContext(), DaftarKonsul_keluhan.class);
                mCtx.startActivity(lanjut);

            }
        });


    }

    @Override
    public int getItemCount() {
        return psikologModelList.size();
    }

    public class PsikologViewHolder extends RecyclerView.ViewHolder {
        TextView namaPsikolog, jkPsikolog, keahlianPsikolog;
        String layanan;
        CardView listLayout;
        LinearLayout buttonLayout;
        ImageView avatarPsikolog;
        Button pilih, bt_daftar, bt_batal, bt_lanjutDaftar;
        ;

        public PsikologViewHolder(View itemView) {
            super(itemView);
            namaPsikolog = itemView.findViewById(R.id.namaPsikolog);
            jkPsikolog = itemView.findViewById(R.id.jkPsikolog);
            keahlianPsikolog = itemView.findViewById(R.id.keahlianPsikolog);
            pilih = itemView.findViewById(R.id.pilih);
            bt_daftar = itemView.findViewById(R.id.bt_daftar);
            bt_batal = itemView.findViewById(R.id.bt_batal);
            bt_lanjutDaftar = itemView.findViewById(R.id.bt_lanjutDaftar);
            avatarPsikolog = itemView.findViewById(R.id.avatarPsikolog);
            listLayout = itemView.findViewById(R.id.listLayout);
            buttonLayout = itemView.findViewById(R.id.buttonLayout);


        }

    }
}
