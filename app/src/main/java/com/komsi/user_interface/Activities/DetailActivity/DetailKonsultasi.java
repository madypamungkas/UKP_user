package com.komsi.user_interface.Activities.DetailActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.komsi.user_interface.R;

public class DetailKonsultasi extends AppCompatActivity {
    TextView detailNama, detailLayanan, detailHubungan, detailPsikolog, detailTangal, detailSesi, detailRuangan, detailStatus, keluhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_konsultasi);
        Button close = (Button)findViewById(R.id.close_detail);
       close.setOnClickListener(new View.OnClickListener() {
          @Override
            public void onClick(View view) {
              DetailKonsultasi.super.onBackPressed();
          }
        });
        TextView back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailKonsultasi.super.onBackPressed();
            }
        });
        /*Button edit = (Button)findViewById(R.id.edit_konsul);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent edit = new Intent(DetailKonsultasi.this, DaftarKonsul_pilihLayanan2.class);
                startActivity(edit);
            }
        });*/
        detailNama= (TextView)findViewById(R.id.namaKlien);
        detailNama.setText(": "+getIntent().getStringExtra("namaKlien"));
        detailLayanan= (TextView)findViewById(R.id.layanan);
        detailLayanan.setText(": "+getIntent().getStringExtra("layanan"));
        detailHubungan= (TextView)findViewById(R.id.detailHubungan);
       // detailHubungan.setText(getIntent().getStringExtra("Hubungan"));
        detailPsikolog= (TextView)findViewById(R.id.detailPsikolog);
        detailPsikolog.setText(": "+getIntent().getStringExtra("psikolog"));
        detailTangal= (TextView)findViewById(R.id.detailTanggal);
        detailTangal.setText(": "+getIntent().getStringExtra("tanggal"));
        detailSesi= (TextView)findViewById(R.id.detailSesi);
        detailSesi.setText(": "+getIntent().getStringExtra("sesi")+" ("+getIntent().getStringExtra("jam")+")");
        detailRuangan= (TextView)findViewById(R.id.detailRuangan);
        if (getIntent().getStringExtra("ruangan") == null){
            detailRuangan.setText("Menunggu Konfirmasi Admin");
        }
        else {
            detailRuangan.setText(": "+getIntent().getStringExtra("ruangan"));
        }

        detailStatus= (TextView)findViewById(R.id.detailStatus);
        detailStatus.setText(": "+getIntent().getStringExtra("status"));
        keluhan= (TextView)findViewById(R.id.keluhan);
        keluhan.setText(": "+getIntent().getStringExtra("keluhan"));

    }

}
