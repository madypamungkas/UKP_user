package com.komsi.user_interface.Activities.DaftarKonsultasi;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.komsi.user_interface.Activities.Menu.DaftarKonsul;
import com.komsi.user_interface.Api.RetrofitClient;
import com.komsi.user_interface.R;
import com.komsi.user_interface.Storage.SharedPrefManager;
import com.komsi.user_interface.models.Details;
import com.komsi.user_interface.models.User;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarKonsul_konfirm extends AppCompatActivity {
    Dialog myDialog;
    ImageView avatar;
    TextView namakonfirm, namaLayanan, psikolog, tanggal, sesi, hargaLayanan, keluhan;
    Context mCtx;
    private String token;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_konsul_konfirm);
        TextView back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DaftarKonsul_konfirm.super.onBackPressed();
            }
        });
        android.support.v7.widget.CardView jadwal = (android.support.v7.widget.CardView) findViewById(R.id.head_pilihJadwal);

        jadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent jadwal = new Intent(DaftarKonsul_konfirm.this, DaftarKonsul_pilihJadwal.class);
                startActivity(jadwal);
            }
        });
        TextView exit = (TextView) findViewById(R.id.back);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.setContentView(R.layout.pop_up_daftar_konsultasi);
                Button batal = (Button) myDialog.findViewById(R.id.batal);
                batal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialog.dismiss();
                    }
                });
                Button keluar = (Button) myDialog.findViewById(R.id.keluar);
                keluar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent keluar = new Intent(DaftarKonsul_konfirm.this, DaftarKonsul.class);
                        myDialog.dismiss();
                        startActivity(keluar);
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });
        Button editKonsultasi = (Button) findViewById(R.id.bt_editKonsul);
        editKonsultasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editkonsul = new Intent(DaftarKonsul_konfirm.this, DaftarKonsul_pilihLayanan2.class);
                startActivity(editkonsul);
            }
        });

        //logic daftar Klien
        Details details = SharedPrefManager.getInstance(this).getDetails();
        SharedPreferences sharedPrefFCM = getSharedPreferences("linkFoto", Context.MODE_PRIVATE);
        String link = sharedPrefFCM.getString("link", null);



        SharedPreferences sharedPref = getSharedPreferences("DaftarKonsul", Context.MODE_PRIVATE);
        SharedPreferences sharedPrefDaftarLain = getSharedPreferences("DaftarKonsul_lain", Context.MODE_PRIVATE);
        String namaKlienLain = sharedPrefDaftarLain.getString("namaKlien", null);
        namakonfirm = (TextView) findViewById(R.id.namaKonfirm);
        myDialog = new Dialog(this);
        namaLayanan = (TextView) findViewById(R.id.namaLayanan);
        psikolog = (TextView) findViewById(R.id.psikolog);
        tanggal = (TextView) findViewById(R.id.tanggal);
        sesi = (TextView) findViewById(R.id.sesi);
        hargaLayanan = (TextView) findViewById(R.id.hargaLayanan);
        keluhan = (TextView) findViewById(R.id.keluhan);
        final String layanan = sharedPref.getString("layanan", null);
        String harga = sharedPref.getString("harga", null);
        String namaPsikolog = sharedPref.getString("psikolog", null);
        String gelarPsikolog = sharedPref.getString("gelar", null);

        //foto
        String url;
        if (namaKlienLain == null){
            url = link + details.getFoto();
        }
        else {
            url = link;
        }
        avatar = (ImageView) findViewById(R.id.avatar);
        Picasso.with(this).load(url).error(R.drawable.menu_icon_user).into(avatar);

        final String keluhanKlien = sharedPref.getString("keluhan", null);
        final String tanggalJadwal = sharedPref.getString("tanggal", null);
        String tanggalShow[] = tanggalJadwal.split("-");

        String sesiJadwal = sharedPref.getString("sesi", null);
        String jam = sharedPref.getString("jam", null);
        final int sesi_id = sharedPref.getInt("idSesi", 0);
        final int layanan_id = sharedPref.getInt("idLayanan", 0);
        final int tes_id = 0;
        final int ruangan_id = 0;
        final int psikolog_id = sharedPref.getInt("idPsikolog", 0);
        final int status = 6;
        namaLayanan.setText(layanan);
        String bulan, tang;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(tanggalShow[0]));
        cal.set(Calendar.MONTH, Integer.parseInt(tanggalShow[1]));
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(tanggalShow[2]));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM YYYY");
      //  Date newDate = datelimit;
        Date tJadwal = cal.getTime();
        final String dateTime = dateFormat.format(tJadwal);


        psikolog.setText(namaPsikolog + " " + gelarPsikolog);
        if (tanggalShow[1].length() < 2) {
            bulan = "0" + tanggalShow[1];
        } else {
            bulan = tanggalShow[1];
        }
        if (tanggalShow[2].length() < 2) {
            tang = "0" + tanggalShow[2];
        } else {
            tang = tanggalShow[2];
        }
       // tanggal.setText(tang + " - " + bulan + " - " + tanggalShow[0]);
        tanggal.setText(dateTime);

        sesi.setText(sesiJadwal + " (" + jam + ")");
        hargaLayanan.setText("Mulai Dari Rp" + harga + ",-");
        //keluhan.setText(keluhanKlien);
        keluhan.setText(keluhanKlien);


        if (namaKlienLain == null) {
            namakonfirm.setText(SharedPrefManager.getInstance(mCtx).getDetails().getNama());
            Button konnfirmasiKonsultasi = (Button) findViewById(R.id.bt_simpanKonsul);
            konnfirmasiKonsultasi.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    loading = ProgressDialog.show(DaftarKonsul_konfirm.this, null, "Sedang Memuat...", true, false);
                    addJadwalSendiri();
                }
            });


        } else {
            namakonfirm.setText(sharedPrefDaftarLain.getString("namaKlien", null));
            Button konnfirmasiKonsultasi = (Button) findViewById(R.id.bt_simpanKonsul);
            konnfirmasiKonsultasi.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    loading = ProgressDialog.show(DaftarKonsul_konfirm.this, null, "Sedang Memuat...", true, false);
                    addJadwalOrangLain();

                }
            });

        }


    }

    public void addJadwalSendiri() {
        //Data Add Jadwal
        SharedPreferences sharedPref = getSharedPreferences("DaftarKonsul", Context.MODE_PRIVATE);
        final String keluhanKlien = sharedPref.getString("keluhan", null);
        final String tanggalJadwal = sharedPref.getString("tanggal", null);
        final int sesi_id = sharedPref.getInt("idSesi", 0);
        final int layanan_id = sharedPref.getInt("idLayanan", 0);
        final int tes_id = 0;
        final int ruangan_id = 7;
        final int psikolog_id = sharedPref.getInt("idPsikolog", 0);
        final int status = 6;
        final int klien_id = sharedPref.getInt("idKlien", 0);
        User user = SharedPrefManager.getInstance(this).getUser();
        token = "Bearer " + user.getToken();
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .jadwalStore(token,
                        tanggalJadwal,
                        keluhanKlien,
                        sesi_id,
                        layanan_id,
                        tes_id,
                        ruangan_id,
                        psikolog_id,
                        klien_id
                );
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                //   keluhan.setText(keluhanKlien + " "+tanggalJadwal+ " "+sesi_id+ " "+layanan_id+ " "+tes_id+ " "+ruangan_id+ " "+psikolog_id+ " "+klien_id );


                if (response.isSuccessful()) {
                    loading.dismiss();

                    Intent konfirmkonsul = new Intent(DaftarKonsul_konfirm.this, DaftarKonsul_Selesai.class);
                    konfirmkonsul.putExtra("idKlien", 1);
                    konfirmkonsul.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    startActivity(konfirmkonsul);
                } else {
                    loading.dismiss();
                    Toast.makeText(DaftarKonsul_konfirm.this, "Terjadi Kesalahan Sistem respon gagal", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(DaftarKonsul_konfirm.this, "Terjadi Kesalahan Sistem", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void addJadwalOrangLain() {
        SharedPreferences sharedPref = getSharedPreferences("DaftarKonsul", Context.MODE_PRIVATE);
        SharedPreferences sharedPrefDaftarLain = getSharedPreferences("DaftarKonsul_lain", Context.MODE_PRIVATE);

        final String keluhanKlien = sharedPref.getString("keluhan", null);
        final String tanggalJadwal = sharedPref.getString("tanggal", null);
        final int sesi_id = sharedPref.getInt("idSesi", 0);
        final int layanan_id = sharedPref.getInt("idLayanan", 0);
        final int tes_id = 0;
        final int ruangan_id = 7;
        final int psikolog_id = sharedPref.getInt("idPsikolog", 0);
        final int status = 6;
        final int klien_id = sharedPrefDaftarLain.getInt("idKlien", 0);
        User user = SharedPrefManager.getInstance(this).getUser();
        token = "Bearer " + user.getToken();
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .jadwalStore(token,
                        tanggalJadwal, keluhanKlien, sesi_id, layanan_id,
                        tes_id, ruangan_id,
                        psikolog_id, klien_id
                );
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    Intent konfirmkonsul = new Intent(DaftarKonsul_konfirm.this, DaftarKonsul_Selesai.class);
                    konfirmkonsul.putExtra("idKlien", 2);
                    konfirmkonsul.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(konfirmkonsul);
                } else {
                    loading.dismiss();
                    Toast.makeText(DaftarKonsul_konfirm.this,
                            "Terjadi Kesalahan Sistem respon gagal", Toast.LENGTH_LONG).show(); } }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(DaftarKonsul_konfirm.this, "Terjadi Kesalahan Sistem", Toast.LENGTH_LONG).show(); }
        });
    }

}
