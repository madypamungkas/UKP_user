package com.komsi.user_interface.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.komsi.user_interface.Activities.Menu.JadwalKonsul;
import com.komsi.user_interface.R;

public class Notifikasi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi);

        TextView back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notifikasi.super.onBackPressed();
            }
        });
        RelativeLayout notif_jadwal = (RelativeLayout) findViewById(R.id.notif_jadwal);
        notif_jadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent notif = new Intent( Notifikasi.this, JadwalKonsul.class);
                startActivity(notif);
            }
        });

    }
}
