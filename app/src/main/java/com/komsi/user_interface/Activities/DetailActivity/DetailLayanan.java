package com.komsi.user_interface.Activities.DetailActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.komsi.user_interface.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailLayanan extends AppCompatActivity {
  TextView namaLayanan, deskripsiLayanan, hargaLayanan;
  Button tutup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_layanan);
        CircleImageView avatar = findViewById(R.id.avatar);
        Picasso.with(this).load(getIntent().getStringExtra("foto"))
                .into(avatar);

        namaLayanan = (TextView)findViewById(R.id.namaLayanan);
        deskripsiLayanan = (TextView)findViewById(R.id.deskripsiLayanan);
        hargaLayanan = (TextView)findViewById(R.id.hargaLayanan);
        namaLayanan.setText(getIntent().getStringExtra("namaLayanan"));
        deskripsiLayanan.setText(getIntent().getStringExtra("deskripsi"));
        hargaLayanan.setText(getIntent().getStringExtra("harga"));
        tutup = (Button)findViewById(R.id.tutup);
        tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailLayanan.super.onBackPressed();
            }
        });

    }
}
