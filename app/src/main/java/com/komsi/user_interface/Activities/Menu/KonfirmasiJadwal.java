package com.komsi.user_interface.Activities.Menu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.komsi.user_interface.Activities.JadwalChild.JadwalChild;
import com.komsi.user_interface.Activities.JadwalChild.KonfirmasiChild;
import com.komsi.user_interface.Activities.MainActivity;
import com.komsi.user_interface.Adapter.JadwalAdapter;
import com.komsi.user_interface.Api.RetrofitClient;
import com.komsi.user_interface.R;
import com.komsi.user_interface.Storage.SharedPrefManager;
import com.komsi.user_interface.models.Klien;
import com.komsi.user_interface.models.Model_Jadwal;
import com.komsi.user_interface.models.Response_Jadwal;
import com.komsi.user_interface.models.StatusModel;
import com.komsi.user_interface.models.User;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class KonfirmasiJadwal extends AppCompatActivity {
    private String token;
    private int idUser, klienId;
    private RecyclerView recyclerViewJadwal;
    private JadwalAdapter adapter;
    private List<Model_Jadwal> modelJadwalList;
    private StatusModel statusModel;
    ProgressDialog loading;
    ScrollView recyclerView;
    LinearLayout noData;
    SwipeRefreshLayout swiperefresh;
    private int refresh_count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_jadwal);
        TextView back = (TextView) findViewById(R.id.back);
        loading = ProgressDialog.show(KonfirmasiJadwal.this, null,
                "Sedang Memuat...", true, false);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(KonfirmasiJadwal.this, MainActivity.class);
               startActivity(intent);
            }
        });
        LinearLayout notif_jadwalKonsul = (LinearLayout) findViewById(R.id.notif_jadwalKonsul);
        notif_jadwalKonsul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent notif = new Intent(KonfirmasiJadwal.this, KonfirmasiChild.class);
                startActivity(notif);
            }
        });
        recyclerViewJadwal = findViewById(R.id.recyclerViewJadwal);
        recyclerViewJadwal.setLayoutManager(new LinearLayoutManager(this));
        recyclerView = findViewById(R.id.recyclerView);
        noData = findViewById(R.id.noData);
        swiperefresh = findViewById(R.id.swiperefresh);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getListJadwal();
            }
        });

    }

    public void getListJadwal() {
        User user = SharedPrefManager.getInstance(this).getUser();
        token = "Bearer " + user.getToken();
        retrofit2.Call<Response_Jadwal> call = RetrofitClient.getInstance().getApi().getkonfirmasi(token, klienId);
        call.enqueue(new Callback<Response_Jadwal>() {
            @Override
            public void onResponse(retrofit2.Call<Response_Jadwal> call, Response<Response_Jadwal> response) {
                loading.dismiss();
                if (response.body().getJadwal() == null){
                    recyclerView.setVisibility(View.GONE);
                    noData.setVisibility(View.VISIBLE);
                }
                else {
                    modelJadwalList = response.body().getJadwal();
                    // statusModel = (StatusModel) response.body().getJadwal();
                    adapter = new JadwalAdapter(KonfirmasiJadwal.this, modelJadwalList);//, statusModel);
                    recyclerViewJadwal.setAdapter(adapter);
                    if (modelJadwalList.size() > 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        noData.setVisibility(View.GONE);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        noData.setVisibility(View.VISIBLE);
                    }
                    refresh_count++;
                    swiperefresh.setRefreshing(false);
                    if (refresh_count > 3) {
                        refresh_count = 0;
                        swiperefresh.setRefreshing(false);
                    }
                }

            }

            @Override
            public void onFailure(retrofit2.Call<Response_Jadwal> call, Throwable t) {
                loading.dismiss();
                refresh_count++;
                swiperefresh.setRefreshing(false);
                if (refresh_count > 3) {
                    refresh_count = 0;
                    swiperefresh.setRefreshing(false);
                }

            }
        });
    }

    public void onStart() {
        super.onStart();
        User user = SharedPrefManager.getInstance(this).getUser();
        Klien klien = SharedPrefManager.getInstance(this).getKlienModel();
        SharedPreferences sharedPref = getSharedPreferences("DaftarKonsul", Context.MODE_PRIVATE);

        klienId = sharedPref.getInt("idKlien", 0);
        getListJadwal();
      /*  idUser = user.getId();
        token = "Bearer "+ user.getToken();
        Call<Response_Klien> call = RetrofitClient.getInstance().getApi().getKlien(token,idUser);
        call.enqueue(new Callback<Response_Klien>() {
            @Override
            public void onResponse(Call<Response_Klien> call, Response<Response_Klien> response) {
                klienId = response.body().getKlien().getKlienId();
                //Toast.makeText(getActivity(), response.body().getKlien().getKlienId(), Toast.LENGTH_LONG).show();
                getListJadwal();
                loading.dismiss();
            }

            @Override
            public void onFailure(Call<Response_Klien> call, Throwable t) {
                loading.dismiss();
            }
        });
*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loading.dismiss();
    }
}
