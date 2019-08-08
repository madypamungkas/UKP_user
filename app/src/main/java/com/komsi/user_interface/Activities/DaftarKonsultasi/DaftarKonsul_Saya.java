package com.komsi.user_interface.Activities.DaftarKonsultasi;

import android.app.Dialog;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.komsi.user_interface.Activities.EditActivity.EditBiodataUser;
import com.komsi.user_interface.Activities.Menu.DaftarKonsul;
import com.komsi.user_interface.Api.RetrofitClient;
import com.komsi.user_interface.R;
import com.komsi.user_interface.Storage.SharedPrefManager;
import com.komsi.user_interface.models.Details;
import com.komsi.user_interface.models.Response_DetailKlien;
import com.komsi.user_interface.models.Klien;
import com.komsi.user_interface.models.User;
import com.squareup.picasso.Picasso;

public class DaftarKonsul_Saya extends AppCompatActivity {
    Dialog myDialog;
    TextView nama,nik,ttl,jk,anak,pendidikan,alamat,nohp, dari;
    ImageView avatar;
    Context mCtx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_konsul__saya);
        TextView back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DaftarKonsul_Saya.super.onBackPressed();
            }
        });

        final Details details = SharedPrefManager.getInstance(this).getDetails();
        final Klien klien = SharedPrefManager.getInstance(this).getKlienModel();
        SharedPreferences sharedPrefFCM = getSharedPreferences("linkFoto", Context.MODE_PRIVATE);
        String link = sharedPrefFCM.getString("link", null);
        final String url = link + details.getFoto(); avatar = (ImageView)findViewById(R.id.avatarDaftarsaya);
        Picasso.with(this).load(url).error(R.drawable.menu_icon_user).into(avatar);

        nama = findViewById(R.id.bioName);
        nama.setText(" "+ details.getNama());
        nik = findViewById(R.id.bioNik);
        nik.setText(" "+ details.getNik());
        jk = findViewById(R.id.bioJk);
        jk.setText(" "+ details.getJenis_kelamin());
        ttl = findViewById(R.id.bioTTL);
        ttl.setText(" "+ details.getTanggal_lahir());
        alamat = findViewById(R.id.bioAlamat);
        alamat.setText(" "+ details.getAlamat());
        nohp = findViewById(R.id.bioNohp);
        nohp.setText(" "+ details.getNo_telepon());
        anak= findViewById(R.id.bioAnakke);
        anak.setText("  "+klien.getAnak_ke());
        dari= findViewById(R.id.bioJumlahsaudara);
        dari.setText("  "+klien.getJumlah_saudara()+"  Bersaudara");
        pendidikan= findViewById(R.id.bioPendidikan);
        pendidikan.setText("  "+klien.getPendidikan_terakhir());
        final TextView detail = (TextView) findViewById(R.id.detail);
        final TextView close = (TextView) findViewById(R.id.close);
        final LinearLayout nik = (LinearLayout) findViewById(R.id.lay_nik);
        final LinearLayout alamat = (LinearLayout) findViewById(R.id.lay_alamat);
        final LinearLayout anak = (LinearLayout) findViewById(R.id.lay_anak);
        final LinearLayout pendidikan = (LinearLayout) findViewById(R.id.lay_pendidikan);
        final LinearLayout nohp = (LinearLayout) findViewById(R.id.lay_nohp);
        Button bt_edit = (Button) findViewById(R.id.bt_editBiodata);
        bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent edit = new Intent(DaftarKonsul_Saya.this, EditBiodataUser.class);startActivity(edit); }
        });
        Button bt_daftar = (Button) findViewById(R.id.bt_daftar);
        bt_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent daftar = new Intent(DaftarKonsul_Saya.this, DaftarKonsul_pilihLayanan2.class);
                startActivity(daftar);
            }
        });
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detail.setVisibility(view.GONE);
                close.setVisibility(view.VISIBLE);
                nik.setVisibility(view.VISIBLE);
                alamat.setVisibility(view.VISIBLE);
                anak.setVisibility(view.VISIBLE);
                pendidikan.setVisibility(view.VISIBLE);
                nohp.setVisibility(view.VISIBLE);

            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detail.setVisibility(view.VISIBLE);
                close.setVisibility(view.GONE);
                nik.setVisibility(view.GONE);
                alamat.setVisibility(view.GONE);
                anak.setVisibility(view.GONE);
                pendidikan.setVisibility(view.GONE);
                nohp.setVisibility(view.GONE);

            }
        });


        myDialog = new Dialog(this);
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
                        Intent keluar = new Intent(DaftarKonsul_Saya.this, DaftarKonsul.class);
                        myDialog.dismiss();
                        startActivity(keluar);
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });

    }
    User user  = SharedPrefManager.getInstance(this).getUser();
    private String token;


    @Override
    protected void onStart() {
        super.onStart();
        token = "Bearer "+ user.getToken();
        retrofit2.Call<Response_DetailKlien> call = RetrofitClient.getInstance().getApi().getDetails(token);
        call.enqueue(new retrofit2.Callback<Response_DetailKlien>() {
            @Override
            public void onResponse(retrofit2.Call<Response_DetailKlien> call, retrofit2.Response<Response_DetailKlien> response) {
                if(response.isSuccessful()) {
                    SharedPrefManager.getInstance(DaftarKonsul_Saya.this).saveDetails(response.body().getDetails());

                }else{
                    Toast.makeText(DaftarKonsul_Saya.this, "Error, Periksa Kembali Username dan Password Anda", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Response_DetailKlien> call, Throwable t) {
                Toast.makeText(DaftarKonsul_Saya.this, "Error, Periksa Kembali Username dan Password Anda", Toast.LENGTH_LONG).show();
            }
        });
    }
}
