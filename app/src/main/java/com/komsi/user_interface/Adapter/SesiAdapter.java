package com.komsi.user_interface.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.komsi.user_interface.Activities.DaftarKonsultasi.DaftarKonsul_konfirm;
import com.komsi.user_interface.Activities.DaftarKonsultasi.DaftarKonsul_pilihJadwal;
import com.komsi.user_interface.Activities.Menu.DaftarKonsul;
import com.komsi.user_interface.Api.RetrofitClient;
import com.komsi.user_interface.R;
import com.komsi.user_interface.Storage.SharedPrefManager;
import com.komsi.user_interface.models.DefaultModel;
import com.komsi.user_interface.models.Details;
import com.komsi.user_interface.models.SesiModel;
import com.komsi.user_interface.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SesiAdapter extends RecyclerView.Adapter<SesiAdapter.SesiViewHolder> {
    private Context mCtx;
    private List<SesiModel> sesiModelList;
    User user = SharedPrefManager.getInstance(mCtx).getUser();

    public SesiAdapter(Context mCtx, List<SesiModel> sesiModelList) {
        this.mCtx = mCtx;
        this.sesiModelList = sesiModelList;
    }

    @NonNull
    @Override
    public SesiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.sesi_list_layout, parent, false);
        return new SesiAdapter.SesiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SesiViewHolder holder, int position) {
        final SesiModel sesi = sesiModelList.get(position);
        holder.namaSesi.setText(sesi.getNama()+ " ("+sesi.getJam()+")");
        final String token = "Bearer "+user.getToken();
        SharedPreferences sharedPref = mCtx.getSharedPreferences("DaftarKonsul", Context.MODE_PRIVATE);
        SharedPreferences sharedPrefDaftarLain = mCtx.getSharedPreferences("DaftarKonsul_lain", Context.MODE_PRIVATE);


        final String keluhanKlien =  sharedPref.getString("keluhan", null);
        final String tanggalJadwal =   sharedPref.getString("tanggal", null);
        final int sesi_id = sharedPref.getInt("idSesi", 0);
        final int layanan_id = sharedPref.getInt("idLayanan", 0);
        final int psikolog_id = sharedPref.getInt("idPsikolog", 0);

        String namaKlienLain =  sharedPrefDaftarLain.getString("namaKlien", null);
        if (namaKlienLain == null){
            holder.klien_id = sharedPref.getInt("idKlien", 0);
        }
        else {
            holder.klien_id = sharedPrefDaftarLain.getInt("idKlien", 0);
        }



        Call<DefaultModel> call = RetrofitClient.getInstance().getApi().validasiJadwal(token,
                tanggalJadwal,
                "CEEK CEEK",
                sesi.getId(),
                layanan_id,
                psikolog_id,
                holder.klien_id,
                6);
        call.enqueue(new Callback<DefaultModel>() {
            @Override
            public void onResponse(Call<DefaultModel> call, Response<DefaultModel> response) {
                final String status = response.body().getStatus()+" ";
                final String message = response.body().getMessage()+" ";
                if(status.equals("failed ")){
                    holder.bt_pilih.setVisibility(View.GONE);
                    holder.infor.setVisibility(View.VISIBLE);
                    holder.itemSelected.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(mCtx, " "+message, Toast.LENGTH_LONG).show();
                        }
                    });
 /*                   Toast.makeText(mCtx, " "+response.body().getMessage(), Toast.LENGTH_LONG).show();
 //                   holder.loading.dismiss();
                    Toast.makeText(mCtx, " "+response.body().getMessage(), Toast.LENGTH_LONG).show();
                    Intent reload = new Intent(mCtx, DaftarKonsul_pilihJadwal.class);
                    mCtx.startActivity(reload);
                    SharedPreferences sharedPref = mCtx.getSharedPreferences("DaftarKonsul", mCtx.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("idSesi",-1  );
                    editor.putString("tanggal", null );
*/                }
                else{
                    holder.bt_pilih.setVisibility(View.VISIBLE);
                    holder.infor.setVisibility(View.GONE);
/*

//                    holder.loading.dismiss();
                    Toast.makeText(mCtx, " "+response.body().getMessage(), Toast.LENGTH_LONG).show();
                    Intent konfirmasi = new Intent(mCtx, DaftarKonsul_konfirm.class);
                    mCtx.startActivity(konfirmasi);
*/
                }
            }

            @Override
            public void onFailure(Call<DefaultModel> call, Throwable t) {
                holder.loading.dismiss();
                Toast.makeText(mCtx, "Periksa Kembali Koneksi Anda", Toast.LENGTH_LONG).show();
            }
        });

        String namaKlienLain2 =  sharedPrefDaftarLain.getString("namaKlien", null);
        if (namaKlienLain2 == null){
            holder.klien_id = sharedPref.getInt("idKlien", 0);
        }
        else {
            holder.klien_id = sharedPrefDaftarLain.getInt("idKlien", 0);
        }

        holder.bt_pilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.itemSelected.setBackgroundColor(Color.parseColor("#0066CC"));
                holder.namaSesi.setTextColor(Color.parseColor("#FFFFFF"));
                SharedPreferences sharedPref = mCtx.getSharedPreferences("DaftarKonsul", mCtx.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("sesi", sesi.getNama() );
                editor.putInt("idSesi", sesi.getId());
                editor.putString("jam", sesi.getJam());
                // editor.putString("keahlian", psikologModel.get());
                editor.apply();
                holder.loading = ProgressDialog.show(mCtx, null, "Sedang Memuat...", true, false);


                Call<DefaultModel> call = RetrofitClient.getInstance().getApi().validasiJadwal(token,
                        tanggalJadwal,
                        keluhanKlien,
                        sesi.getId(),
                        layanan_id,
                        psikolog_id,
                        holder.klien_id,
                        6);
                call.enqueue(new Callback<DefaultModel>() {
                    @Override
                    public void onResponse(Call<DefaultModel> call, Response<DefaultModel> response) {
                        String status = response.body().getStatus()+" ";
                        String message = response.body().getMessage()+" ";
                        if(status.equals("failed ")){
                            Toast.makeText(mCtx, " "+response.body().getMessage(), Toast.LENGTH_LONG).show();
                            holder.loading.dismiss();
                            Toast.makeText(mCtx, " "+response.body().getMessage(), Toast.LENGTH_LONG).show();
                            Intent reload = new Intent(mCtx, DaftarKonsul_pilihJadwal.class);
                            mCtx.startActivity(reload);
                            SharedPreferences sharedPref = mCtx.getSharedPreferences("DaftarKonsul", mCtx.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putInt("idSesi",-1  );
                            editor.putString("tanggal", null );
                        }
                        else{
                            holder.loading.dismiss();
                            Toast.makeText(mCtx, " "+response.body().getMessage(), Toast.LENGTH_LONG).show();
                            Intent konfirmasi = new Intent(mCtx, DaftarKonsul_konfirm.class);
                            mCtx.startActivity(konfirmasi);
                        }
                    }

                    @Override
                    public void onFailure(Call<DefaultModel> call, Throwable t) {
                        holder.loading.dismiss();
                        Toast.makeText(mCtx, "Periksa Kembali Koneksi Anda", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
      return sesiModelList.size();
    }

    public class SesiViewHolder extends RecyclerView.ViewHolder{
        TextView namaSesi, infor;
        LinearLayout itemSelected;
        Button bt_pilih;
        int klien_id;
        ProgressDialog loading;

        public SesiViewHolder(View itemView) {
            super(itemView);
            namaSesi= itemView.findViewById(R.id.namaSesi);
            infor= itemView.findViewById(R.id.infor);
            itemSelected = itemView.findViewById(R.id.itemSelected);
            bt_pilih = itemView.findViewById(R.id.bt_pilih);

        }
    }
}
