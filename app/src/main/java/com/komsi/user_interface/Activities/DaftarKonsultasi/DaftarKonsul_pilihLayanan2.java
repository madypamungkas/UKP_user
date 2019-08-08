package com.komsi.user_interface.Activities.DaftarKonsultasi;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.komsi.user_interface.Adapter.PilihLayananAdapter;
import com.komsi.user_interface.Api.RetrofitClient;
import com.komsi.user_interface.R;
import com.komsi.user_interface.Storage.SharedPrefManager;
import com.komsi.user_interface.models.Model_Layanan;
import com.komsi.user_interface.models.Response_Layanan;
import com.komsi.user_interface.models.User;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class DaftarKonsul_pilihLayanan2 extends AppCompatActivity {
    Dialog myDialog;
    private RecyclerView recyclerViewPilihLayanan;
    private PilihLayananAdapter adapter;
    private List<Model_Layanan> modelLayananList;
    private String token;
    TextView pilihanLayanan, back_dafLayanan;
    android.support.v7.widget.CardView head_biodataklien;
    ProgressDialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_konsul_pilih_layanan2);
        loading = ProgressDialog.show(DaftarKonsul_pilihLayanan2.this,
                null, "Sedang Memuat...", true, false);


        head_biodataklien = (android.support.v7.widget.CardView)findViewById(R.id.head_biodataklien);
        head_biodataklien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DaftarKonsul_pilihLayanan2.super.onBackPressed();

            }
        });
        back_dafLayanan = (TextView)findViewById(R.id.back);
        back_dafLayanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DaftarKonsul_pilihLayanan2.super.onBackPressed();
            }
        });

        recyclerViewPilihLayanan = findViewById(R.id.recyclerViewPilihLayanan);
        recyclerViewPilihLayanan.setLayoutManager(new LinearLayoutManager(this));
        pilihanLayanan = (TextView) findViewById(R.id.pilihanLayanan);
        SharedPreferences sharedPref = getSharedPreferences("DaftarKonsul", Context.MODE_PRIVATE);
        String layanan =  sharedPref.getString("layanan", null);
        pilihanLayanan.setText(layanan);

    }
    @Override
    protected void onStart() {
        super.onStart();
        User user  = SharedPrefManager.getInstance(this).getUser();
        token = "Bearer "+ user.getToken();
        retrofit2.Call<Response_Layanan> call = RetrofitClient.getInstance().getApi().getLayanan(token);
        call.enqueue(new Callback<Response_Layanan>() {
            @Override
            public void onResponse(retrofit2.Call<Response_Layanan> call, Response<Response_Layanan> response) {
                loading.dismiss();
               modelLayananList = response.body().getLayanan();
                adapter = new PilihLayananAdapter(DaftarKonsul_pilihLayanan2.this, modelLayananList);
                recyclerViewPilihLayanan.setAdapter(adapter);

            }

            @Override
            public void onFailure(retrofit2.Call<Response_Layanan> call, Throwable t) {
                Toast.makeText(DaftarKonsul_pilihLayanan2.this, "Gagal ",Toast.LENGTH_LONG).show();
                loading.dismiss();
            }
        });

    }

}
