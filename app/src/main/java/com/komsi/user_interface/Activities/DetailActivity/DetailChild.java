package com.komsi.user_interface.Activities.DetailActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.komsi.user_interface.R;

public class DetailChild extends AppCompatActivity {
    TextView namaChild, ttlChild, pendidikanChild, jenisKelaminChild, anakkeChild, jumlahSaudara, hubunganChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_child);
        namaChild= (TextView)findViewById(R.id.namaChild);
        namaChild.setText(": "+getIntent().getStringExtra("namaChild"));
        ttlChild= (TextView)findViewById(R.id.ttlChild);
        ttlChild.setText(": "+getIntent().getStringExtra("ttlChild"));
        pendidikanChild= (TextView)findViewById(R.id.pendidikanChild);
        pendidikanChild.setText(": "+getIntent().getStringExtra("pendidikan"));
        jenisKelaminChild= (TextView)findViewById(R.id.jenisKelaminChild);
        jenisKelaminChild.setText(": "+getIntent().getStringExtra("jenisKelaminChild"));
        anakkeChild= (TextView)findViewById(R.id.anakkeChild);
        anakkeChild.setText(": "+ getIntent().getStringExtra("anakke"));
        jumlahSaudara= (TextView)findViewById(R.id.jumlahSaudara);
        jumlahSaudara.setText(": "+getIntent().getStringExtra("dari")+ "  Bersaudara");
        hubunganChild= (TextView)findViewById(R.id.hubunganChild);
        hubunganChild.setText(": "+getIntent().getStringExtra("hubungan"));

        Button close = (Button)findViewById(R.id.close_detail);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailChild.super.onBackPressed();
            }
        });
        TextView back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailChild.super.onBackPressed();
            }
        });

    }
}
