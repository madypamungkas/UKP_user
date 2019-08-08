package com.komsi.user_interface.Activities.JadwalChild;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.komsi.user_interface.Adapter.JadwalAdapter;
import com.komsi.user_interface.Api.RetrofitClient;
import com.komsi.user_interface.R;
import com.komsi.user_interface.Storage.SharedPrefManager;
import com.komsi.user_interface.models.Model_Jadwal;
import com.komsi.user_interface.models.Response_Jadwal;
import com.komsi.user_interface.models.Klien;
import com.komsi.user_interface.models.Response_Klien;
import com.komsi.user_interface.models.StatusModel;
import com.komsi.user_interface.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatChild extends AppCompatActivity {
    private String token;
    private int idUser, klienId;
    private RecyclerView recyclerViewJadwal;
    private JadwalAdapter adapter;
    private List<Model_Jadwal> modelJadwalList;
    private StatusModel statusModel;
    ProgressDialog loading;
    ScrollView recyclerView;
    LinearLayout noData;
    private ConnectivityManager connectivityManager;
    SwipeRefreshLayout swiperefresh;
    private int refresh_count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_child);
        loading = ProgressDialog.show(RiwayatChild.this, null, "Sedang Memuat...", true, false);
        TextView back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RiwayatChild.super.onBackPressed();
            }
        });

        recyclerView =findViewById(R.id.recyclerView);
        noData = findViewById(R.id.noData);
        recyclerViewJadwal = findViewById(R.id.recyclerViewJadwal);
        recyclerViewJadwal.setLayoutManager(new LinearLayoutManager(this));
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
        Klien klien = SharedPrefManager.getInstance(this).getKlienModel();


        token = "Bearer " + user.getToken();
        retrofit2.Call<Response_Jadwal> call = RetrofitClient.getInstance().getApi().getRiwayatChild(token, klienId);
        call.enqueue(new Callback<Response_Jadwal>() {
            @Override
            public void onResponse(retrofit2.Call<Response_Jadwal> call, Response<Response_Jadwal> response) {
                modelJadwalList = response.body().getJadwal();
                // statusModel = (StatusModel) response.body().getJadwal();
                adapter = new JadwalAdapter(RiwayatChild.this, modelJadwalList);//, statusModel);
                recyclerViewJadwal.setAdapter(adapter);
                if (modelJadwalList.size() > 0){
                    recyclerView.setVisibility(View.VISIBLE);
                    noData.setVisibility(View.GONE);
                }
                else{
                    recyclerView.setVisibility(View.GONE);
                    noData.setVisibility(View.VISIBLE);
                }
                refresh_count++;
                swiperefresh.setRefreshing(false);
                if (refresh_count > 3){
                    refresh_count = 0;
                    swiperefresh.setRefreshing(false);
                }
                loading.dismiss();

            }

            @Override
            public void onFailure(retrofit2.Call<Response_Jadwal> call, Throwable t) {
                loading.dismiss();
                refresh_count++;
                swiperefresh.setRefreshing(false);
                if (refresh_count > 3){
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

        idUser = user.getId();
        token = "Bearer " + user.getToken();
        Call<Response_Klien> call = RetrofitClient.getInstance().getApi().getKlien(token, idUser);
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


    }
}
