package com.komsi.user_interface.Activities.DaftarKonsultasi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.komsi.user_interface.Activities.JadwalChild.KonfirmasiChild;
import com.komsi.user_interface.Activities.MainActivity;
import com.komsi.user_interface.Activities.Menu.KonfirmasiJadwal;
import com.komsi.user_interface.R;

public class DaftarKonsul_Selesai extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_konsul__selesai);
        SharedPreferences sharedPrefDaftarLain = getSharedPreferences("DaftarKonsul_lain", Context.MODE_PRIVATE);
        final String namaKlienLain =  sharedPrefDaftarLain.getString("namaKlien", null);
        TextView back = (TextView) findViewById(R.id.back);
        final int idKlien = getIntent().getIntExtra("idKlien", 0);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idKlien == 1) {
                    Intent selesai = new Intent(DaftarKonsul_Selesai.this, KonfirmasiJadwal.class);
                    Toast.makeText(getApplicationContext(), "Pengajuan Konsultasi Anda Akan Kami Proses", Toast.LENGTH_LONG)
                            .show();
                    selesai.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(selesai);
                }
                else{
                    Intent selesai = new Intent(DaftarKonsul_Selesai.this, KonfirmasiChild.class);
                    Toast.makeText(getApplicationContext(), "Pengajuan Konsultasi Anda Akan Kami Proses", Toast.LENGTH_LONG)
                            .show();
                    selesai.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(selesai);
                }
            }
        });

        Button home = (Button) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idKlien == 1) {
                    Intent home = new Intent(DaftarKonsul_Selesai.this, KonfirmasiJadwal.class);
                    Toast.makeText(getApplicationContext(), "Pengajuan Konsultasi Anda Akan Kami Proses", Toast.LENGTH_LONG)
                            .show();
                    home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(home);
                }
                else{
                    Intent home = new Intent(DaftarKonsul_Selesai.this, KonfirmasiChild.class);
                    Toast.makeText(getApplicationContext(), "Pengajuan Konsultasi Anda Akan Kami Proses", Toast.LENGTH_LONG)
                            .show();
                    home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(home);
                }
            }
        });
    }
}
