package com.komsi.user_interface.Activities.bin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.komsi.user_interface.Activities.DetailActivity.DetailKonsultasi;
import com.komsi.user_interface.R;

public class RiwayatKonsul extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_konsul);

        TextView back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RiwayatKonsul.super.onBackPressed();
            }
        });

        RelativeLayout detail1 = (RelativeLayout) findViewById(R.id.detail_jadwal1);
        detail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detail1 = new  Intent (RiwayatKonsul.this, DetailKonsultasi.class);
                startActivity(detail1);
            }
        });
        RelativeLayout detail2 = (RelativeLayout) findViewById(R.id.detail_jadwal2);
        detail2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detail2 = new  Intent (RiwayatKonsul.this, DetailKonsultasi.class);
                startActivity(detail2);
            }
        });
        RelativeLayout detail3 = (RelativeLayout) findViewById(R.id.detail_jadwal3);
        detail3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detail3 = new  Intent (RiwayatKonsul.this, DetailKonsultasi.class);
                startActivity(detail3);
            }
        });
    }
}
