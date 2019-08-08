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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.komsi.user_interface.Activities.Menu.DaftarKonsul;
import com.komsi.user_interface.R;

public class DaftarKonsul_keluhan extends AppCompatActivity {
    Dialog myDialog;
    EditText keluhan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_konsul_keluhan);
        myDialog = new Dialog(this);
        TextView exit = (TextView) findViewById(R.id.back);
        final TextView pilihanLayanan = (TextView) findViewById(R.id.pilihanLayanan);
        android.support.v7.widget.CardView layanan = (android.support.v7.widget.CardView) findViewById(R.id.head_pilihLayanan);
        layanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent psikolog = new Intent(DaftarKonsul_keluhan.this, DaftarKonsul_pilihPsikolog.class);
                startActivity(psikolog);
            }
        });
        Button keluhan_bt = (Button) findViewById(R.id.bt_keluhan);
        keluhan_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keluhan = (EditText)findViewById(R.id.keluhan);
                String keluhaanTxt = keluhan.getText().toString().trim();
                if(keluhaanTxt.matches("")){
                    Toast.makeText(DaftarKonsul_keluhan.this,
                            "Keluhan Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                }
                else if( keluhaanTxt.length() < 20){
                    Toast.makeText(DaftarKonsul_keluhan.this,
                            "Keluhan Harus Berisi Lebih Dari 20 Karakter", Toast.LENGTH_LONG).show();
                }
                else {
                    SharedPreferences sharedPref = getSharedPreferences("DaftarKonsul", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("keluhan", keluhaanTxt);
                    editor.apply();
                    Intent keluhan = new Intent(DaftarKonsul_keluhan.this, DaftarKonsul_pilihJadwal.class);
                    startActivity(keluhan);
                }
            }
        });

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
                        Intent keluar = new Intent(DaftarKonsul_keluhan.this, DaftarKonsul.class);
                        myDialog.dismiss();
                        startActivity(keluar);
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });


    }
}
