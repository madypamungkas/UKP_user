package com.komsi.user_interface.Activities.DaftarKonsultasi;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.komsi.user_interface.Activities.Menu.DaftarKonsul;
import com.komsi.user_interface.Adapter.SesiAdapter;
import com.komsi.user_interface.Api.RetrofitClient;
import com.komsi.user_interface.R;
import com.komsi.user_interface.Storage.SharedPrefManager;
import com.komsi.user_interface.models.SesiModel;
import com.komsi.user_interface.models.Response_Sesi;
import com.komsi.user_interface.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarKonsul_pilihJadwal extends AppCompatActivity {
    Dialog myDialog;
    private RecyclerView recyclerViewSesi;
    private SesiAdapter adapter;
    private List<SesiModel> sesiModelList;
    private String token;
    private android.support.v7.widget.CardView pilihLayanan, keluhan;
    ProgressDialog loading;
    CalendarView calendarView;
    TextView tanggal;


    DatePickerDialog pickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_konsul_pilih_jadwal);
        loading = ProgressDialog.show(DaftarKonsul_pilihJadwal.this,
                null, "Sedang Memuat...", true, false);
        TextView back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DaftarKonsul_pilihJadwal.super.onBackPressed();
            }
        });
        final Button konfirmasi = (Button) findViewById(R.id.bt_konfirmasi);
        final Button ganti = (Button) findViewById(R.id.bt_ganti);
        ganti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reload = new Intent(DaftarKonsul_pilihJadwal.this, DaftarKonsul_pilihJadwal.class);
                startActivity(reload);
            }
        });
        tanggal = (TextView) findViewById(R.id.tanggal);


        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setMinDate(System.currentTimeMillis()-1000);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = i + "-" + (i1 + 1) + "-" + i2; //yymmdd
                konfirmasi.setVisibility(View.VISIBLE);

              //  ganti.setVisibility(View.VISIBLE);
                SharedPreferences sharedPref = getSharedPreferences("DaftarKonsul", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                String tanggalShow[] =date.split("-");

                String bulan; String tang;
                if (tanggalShow[1].length() <2){
                    bulan ="0"+tanggalShow[1];
                }
                else {
                    bulan = tanggalShow[1];
                }
                if (tanggalShow[2].length() <2){
                    tang ="0"+tanggalShow[2];
                }
                else {
                    tang = tanggalShow[2];
                }
                tanggal.setText(tang+" - "+ bulan +" - "+ tanggalShow[0]);

                editor.putString("tanggal", date);
                editor.apply();

            }
        });

        konfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ganti.setVisibility(View.VISIBLE);
                LinearLayout sesi = (LinearLayout) findViewById(R.id.sesi);
                sesi.setVisibility(View.VISIBLE);
            }
        });
        recyclerViewSesi = findViewById(R.id.recyclerViewSesi);
        recyclerViewSesi.setLayoutManager(new LinearLayoutManager(
                this, LinearLayout.HORIZONTAL, false));

        myDialog = new Dialog(this);
        TextView exit = (TextView) findViewById(R.id.back);

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
                        Intent keluar = new Intent(DaftarKonsul_pilihJadwal.this, DaftarKonsul.class);
                        myDialog.dismiss();
                        startActivity(keluar);
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });
        recyclerViewSesi = findViewById(R.id.recyclerViewSesi);
        recyclerViewSesi.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));

    }


    @Override
    protected void onStart() {
        super.onStart();

        User user = SharedPrefManager.getInstance(this).getUser();
        token = "Bearer " + user.getToken();
        Call<Response_Sesi> call = RetrofitClient.getInstance().getApi().getSesi(token);
        call.enqueue(new Callback<Response_Sesi>() {
            @Override
            public void onResponse(Call<Response_Sesi> call, Response<Response_Sesi> response) {
                loading.dismiss();
                sesiModelList = response.body().getSesi();
                adapter = new SesiAdapter(DaftarKonsul_pilihJadwal.this, sesiModelList);
                recyclerViewSesi.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Response_Sesi> call, Throwable t) {
                loading.dismiss();
            }
        });
    }
}
