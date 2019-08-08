package com.komsi.user_interface.Activities.DetailActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.komsi.user_interface.Adapter.LayananPsikologAdapter;
import com.komsi.user_interface.Api.RetrofitClient;
import com.komsi.user_interface.R;
import com.komsi.user_interface.Storage.SharedPrefManager;
import com.komsi.user_interface.models.Model_Layanan;
import com.komsi.user_interface.models.Response_LayananPsikolog;
import com.komsi.user_interface.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPsikolog extends AppCompatActivity {
    TextView namaPsikolog, umurPsikolog, nomorSipp, jenisKelamin, keahlianPsikolog;
    private RecyclerView recyclerViewlayanan;
    private LayananPsikologAdapter adapter;
    private List<Model_Layanan> layananList;
    User user  = SharedPrefManager.getInstance(this).getUser();
    String token = "Bearer "+ user.getToken();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_psikolog);
        CircleImageView avatarPsikolog = findViewById(R.id.avatarPsikolog);
        SharedPreferences sharedPrefFCM = getSharedPreferences("linkFoto", Context.MODE_PRIVATE);
        String link = sharedPrefFCM.getString("link", null);
        final String url= link + getIntent().getStringExtra("foto");
        Picasso.with(this).load(url).error(R.drawable.menu_icon_user).into(avatarPsikolog);

        namaPsikolog= (TextView)findViewById(R.id.namaPsikolog);
        namaPsikolog.setText(": "+getIntent().getStringExtra("namaPsikolog"));
        umurPsikolog= (TextView)findViewById(R.id.umurPsikolog);
        umurPsikolog.setText(": "+getIntent().getStringExtra("umur"));
        nomorSipp= (TextView)findViewById(R.id.noSipp);
        nomorSipp.setText(": "+getIntent().getStringExtra("noSipp"));
        jenisKelamin= (TextView)findViewById(R.id.jenisKelamin);
        jenisKelamin.setText(": "+getIntent().getStringExtra("jenisKelaminPsikolog"));
        keahlianPsikolog= (TextView)findViewById(R.id.keahlianPsikolog);
        keahlianPsikolog.setText(": "+getIntent().getStringExtra("keahlianPsikolog"));
        recyclerViewlayanan = findViewById(R.id.recyclerViewlayanan);
        recyclerViewlayanan.setLayoutManager(new LinearLayoutManager(this));
        getListLayanan();
        Button close = (Button)findViewById(R.id.close_detail);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailPsikolog.super.onBackPressed();
            }
        });
        TextView back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailPsikolog.super.onBackPressed();
            }
        });
    }
    public void getListLayanan(){

        int idPsikolog = getIntent().getIntExtra("idPsikolog", 0);
        retrofit2.Call<Response_LayananPsikolog> call = RetrofitClient.getInstance().getApi().getLayananPsikolog(token, idPsikolog);
        call.enqueue(new Callback<Response_LayananPsikolog>() {
            @Override
            public void onResponse(retrofit2.Call<Response_LayananPsikolog> call, Response<Response_LayananPsikolog> response) {
                layananList = response.body().getLayanan();
                adapter = new LayananPsikologAdapter( DetailPsikolog.this, layananList);
                recyclerViewlayanan.setAdapter(adapter);
            }

            @Override
            public void onFailure(retrofit2.Call<Response_LayananPsikolog> call, Throwable t) {
                Toast.makeText(DetailPsikolog.this, "Gagal", Toast.LENGTH_LONG).show();
            }
        });

    }
}
