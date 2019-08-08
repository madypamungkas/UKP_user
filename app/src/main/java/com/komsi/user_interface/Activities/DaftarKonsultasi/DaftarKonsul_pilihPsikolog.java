package com.komsi.user_interface.Activities.DaftarKonsultasi;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.komsi.user_interface.Activities.Menu.DaftarKonsul;
import com.komsi.user_interface.Adapter.PsikologAdapter;
import com.komsi.user_interface.Api.RetrofitClient;
import com.komsi.user_interface.R;
import com.komsi.user_interface.Storage.SharedPrefManager;
import com.komsi.user_interface.models.PsikologDetail_list;
import com.komsi.user_interface.models.PsikologModel;
import com.komsi.user_interface.models.Response_Psikolog;
import com.komsi.user_interface.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarKonsul_pilihPsikolog extends AppCompatActivity {
    private Dialog myDialog;
    private RecyclerView recyclerViewPsikolog;
    private PsikologAdapter adapter;
    private List<PsikologModel> psikologModelList;
    private String token;
    private android.support.v7.widget.CardView pilihLayanan, keluhan;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_konsul_pilih_psikolog);
        myDialog = new Dialog(this);
        TextView exit = (TextView) findViewById(R.id.back);
        loading = ProgressDialog.show(DaftarKonsul_pilihPsikolog.this, null, "Sedang Memuat...", true, false);
        String layanan = getIntent().getStringExtra("namaLayanan");
        int  idLayanan = getIntent().getIntExtra("idLayanan", 1);

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
                        Intent keluar = new Intent(DaftarKonsul_pilihPsikolog.this, DaftarKonsul.class);
                        myDialog.dismiss();
                        startActivity(keluar);
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });
        Button psikolog = (Button)findViewById(R.id.bt_psikolog);
        psikolog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent psikolog = new Intent(DaftarKonsul_pilihPsikolog.this, DaftarKonsul_keluhan.class);
                startActivity(psikolog);
            }
        });
        pilihLayanan = (android.support.v7.widget.CardView)findViewById(R.id.head_pilihLayanan);
        pilihLayanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent layanan = new Intent(DaftarKonsul_pilihPsikolog.this, DaftarKonsul_pilihLayanan2.class);
                startActivity(layanan);
            }
        });
        recyclerViewPsikolog = findViewById(R.id.recyclerViewPsikolog);
        recyclerViewPsikolog.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    protected void onStart() {
        super.onStart();
        User user  = SharedPrefManager.getInstance(this).getUser();
        token = "Bearer "+ user.getToken();
        SharedPreferences sharedPref = getSharedPreferences("DaftarKonsul", Context.MODE_PRIVATE);
        int layanan_id = sharedPref.getInt("idLayanan", -1);
        Call<Response_Psikolog> call = RetrofitClient.getInstance().getApi().getPsikolog(token,layanan_id);
        call.enqueue(new Callback<Response_Psikolog>() {
            @Override
            public void onResponse(Call<Response_Psikolog> call, Response<Response_Psikolog> response) {
                loading.dismiss();
                psikologModelList = response.body().getPsikolog();
                adapter = new PsikologAdapter(DaftarKonsul_pilihPsikolog.this, psikologModelList);
                recyclerViewPsikolog.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<Response_Psikolog> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(DaftarKonsul_pilihPsikolog.this, "Periksa Koneksi Internet Anda" ,Toast.LENGTH_LONG).show();
            }
        });
    }
}
