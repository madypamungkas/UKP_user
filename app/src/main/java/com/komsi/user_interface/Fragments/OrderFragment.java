package com.komsi.user_interface.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.komsi.user_interface.Activities.JadwalChild.RiwayatChild;
import com.komsi.user_interface.Adapter.JadwalAdapter;
import com.komsi.user_interface.Api.RetrofitClient;
import com.komsi.user_interface.R;
import com.komsi.user_interface.Activities.bin.RiwayatKonsul;
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

import static android.content.Context.CONNECTIVITY_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment implements View.OnClickListener{


    public OrderFragment() {
        // Required empty public constructor
    }
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_order, container, false);
        loading = ProgressDialog.show(getActivity(), null,
                "Sedang Memuat...", true, false);
        connectivityManager = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        swiperefresh = fragmentView.findViewById(R.id.swiperefresh);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getListJadwal();
            }
        });

        ImageView sem_konsul = (ImageView) fragmentView.findViewById(R.id.semua_konsul);
        LinearLayout riwayatChild = (LinearLayout)fragmentView.findViewById(R.id.riwayatChild);

        riwayatChild.setOnClickListener(this);
        sem_konsul.setOnClickListener(this);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewJadwal = view.findViewById(R.id.recyclerViewJadwal);
        recyclerViewJadwal.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView =view.findViewById(R.id.recyclerView);
        noData = view.findViewById(R.id.noData);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.semua_konsul:
                Intent semua = new Intent(getActivity(), RiwayatKonsul.class);
                startActivity(semua);
                break;
                case R.id.riwayatChild:
                Intent riwayat = new Intent(getActivity(), RiwayatChild.class);
                startActivity(riwayat);
                break;
        }
    }

    public void getListJadwal(){
       User user = SharedPrefManager.getInstance(getActivity()).getUser();
        token = "Bearer "+ user.getToken();
        retrofit2.Call<Response_Jadwal> call = RetrofitClient.getInstance().getApi().getRiwayat(token, klienId);
        call.enqueue(new Callback<Response_Jadwal>() {
            @Override
            public void onResponse(retrofit2.Call<Response_Jadwal> call, Response<Response_Jadwal> response) {
                loading.dismiss();
                if (response.body().getJadwal() != null) {
                    modelJadwalList = response.body().getJadwal();
                    adapter = new JadwalAdapter(getActivity(), modelJadwalList);
                    recyclerViewJadwal.setAdapter(adapter);
                    if (modelJadwalList.size() > 0){
                        recyclerView.setVisibility(View.VISIBLE);
                        noData.setVisibility(View.GONE); }
                    else{
                        recyclerView.setVisibility(View.GONE);
                        noData.setVisibility(View.VISIBLE);
                    }refresh_count++;
                    swiperefresh.setRefreshing(false);
                    if (refresh_count > 3){ refresh_count = 0;
                        swiperefresh.setRefreshing(false); } }
                else {
                    Toast.makeText(getActivity(), "Tidak Ada Data", Toast.LENGTH_LONG).show(); } }
            @Override
            public void onFailure(retrofit2.Call<Response_Jadwal> call, Throwable t) {
                loading.dismiss();refresh_count++;
                swiperefresh.setRefreshing(false);
                recyclerView.setVisibility(View.GONE);
                noData.setVisibility(View.VISIBLE);
                if (refresh_count > 3){
                    refresh_count = 0;
                    swiperefresh.setRefreshing(false); } }}); }
    @Override
    public void onStart() {
        super.onStart();
        cekConnectivity();
        User user  = SharedPrefManager.getInstance(getActivity()).getUser();

        final SharedPreferences sharedPref = getActivity()
                .getSharedPreferences("DaftarKonsul", Context.MODE_PRIVATE);
        idUser = sharedPref.getInt("idUser", 0);

        token = "Bearer "+ user.getToken();
        Call<Response_Klien> call = RetrofitClient.getInstance().getApi().getKlien(token,idUser);
        call.enqueue(new Callback<Response_Klien>() {
            @Override
            public void onResponse(Call<Response_Klien> call, Response<Response_Klien> response) {
                klienId = sharedPref.getInt("idKlien", 0);
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
    private void cekConnectivity() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Snackbar.make(getActivity().findViewById(R.id.orderFragment), "Anda Terhubung", Snackbar.LENGTH_LONG);
        } else {
            Snackbar.make(getActivity().findViewById(R.id.orderFragment), "Koneksi Terputus, Anda Tidak Terhubung Dengan Layanan Internet", Snackbar.LENGTH_LONG).show();

        }
    }

}
