package com.komsi.user_interface.Activities.DaftarKonsultasi;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.komsi.user_interface.Activities.EditActivity.EditBiodataUser;
import com.komsi.user_interface.Activities.Menu.DaftarKonsul;
import com.komsi.user_interface.Api.RetrofitClient;
import com.komsi.user_interface.R;
import com.komsi.user_interface.Storage.SharedPrefManager;
import com.komsi.user_interface.models.Details;
import com.komsi.user_interface.models.Klien;
import com.komsi.user_interface.models.Response_DaftarOrangLain;
import com.komsi.user_interface.models.User;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarKonsul_Lain extends AppCompatActivity {
    private Spinner spPendidikan;
    private Spinner spJK;
    private TextInputLayout namaKlien, tanggalLahir, anakkeDaftar, dariDaftar, hubunganKlien, biodata_NIK;
    TextView test;
    Dialog myDialog;
    String token;
    ImageView data_Child;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    Button child_ttl;
    RadioButton r_lakilaki, r_perempuan, r_sd, r_smp, r_sma, r_diploma, r_s1, r_s2, r_lain;
    RadioGroup rg_jk, rg_pendidikan, rg_pendidikan2;
    private String pendidikanKlien, genderKlien;
    ProgressDialog loading;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_konsul__lain);

        TextView back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DaftarKonsul_Lain.super.onBackPressed();
            }
        });


        rg_jk = findViewById(R.id.rg_jk);
        rg_pendidikan = findViewById(R.id.rg_pendidikan);
        rg_pendidikan2 = findViewById(R.id.rg_pendidikan2);

        // radio button
        r_lakilaki = findViewById(R.id.r_lakilaki);
        r_perempuan = findViewById(R.id.r_perempuan);
        r_sd = findViewById(R.id.r_sd);
        r_smp = findViewById(R.id.r_smp);
        r_sma = findViewById(R.id.r_sma);
        r_diploma = findViewById(R.id.r_diploma);
        r_s1 = findViewById(R.id.r_s1);
        r_s2 = findViewById(R.id.r_s2);
        r_lain = findViewById(R.id.r_lain);

        //SharedPreferences sharedPref = getSharedPreferences("DaftarKonsul", Context.MODE_PRIVATE);


        namaKlien = (TextInputLayout) findViewById(R.id.biodata_nama);
        biodata_NIK = (TextInputLayout) findViewById(R.id.biodata_NIK);
        tanggalLahir = (TextInputLayout) findViewById(R.id.daftar_ttl);
        anakkeDaftar = (TextInputLayout) findViewById(R.id.daftar_anakke);
        dariDaftar = (TextInputLayout) findViewById(R.id.daftar_dari);
        hubunganKlien = (TextInputLayout) findViewById(R.id.daftar_hubungan);
        spPendidikan = (Spinner) findViewById(R.id.spin_pendidikan);
        spJK = (Spinner) findViewById(R.id.spin_kelamin);
        test = (TextView) findViewById(R.id.test);
        child_ttl = (Button) findViewById(R.id.child_ttl);
        child_ttl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(DaftarKonsul_Lain.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        tanggalLahir.getEditText().setText((mYear) + "-" + (mMonth + 1) + "-" + mDay);
                    }
                }, year, month, day);
                Calendar calMax = Calendar.getInstance();
                calMax.add(Calendar.YEAR, -1);
                long calMaxMillis = calMax.getTimeInMillis();
                datePickerDialog.getDatePicker().setMaxDate(calMaxMillis);
                datePickerDialog.show();
            }
        });


        Button bt_daftar = (Button) findViewById(R.id.bt_daftarLain);
        bt_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(DaftarKonsul_Lain.this, token, Toast.LENGTH_LONG).show();

                simpanKlien();
/*
                Call<Response_DaftarOrangLain> call = RetrofitClient.getInstance().getApi().daftarLain(token, "Si DOI", "acak", "Klien", "2019-1-20", "Perempuan", "1", "3","SMA","7", "Teman");
                call.enqueue(new Callback<Response_DaftarOrangLain>() {
                    @Override
                    public void onResponse(retrofit2.Call<Response_DaftarOrangLain> call, Response<Response_DaftarOrangLain > response) {
                        Toast.makeText(DaftarKonsul_Lain.this, "Sukses", Toast.LENGTH_LONG).show();

                            Toast.makeText(DaftarKonsul_Lain.this, ""+response.body().getKlien_DaftarOrangLain().getIdKlien() , Toast.LENGTH_LONG).show();


                    }

                    @Override
                    public void onFailure(retrofit2.Call<Response_DaftarOrangLain> call, Throwable t) {
                        Toast.makeText(DaftarKonsul_Lain.this, "Terjadi Kesalahan Sistem", Toast.LENGTH_LONG).show();

                    }
                });*/


            }
        });


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
                        Intent keluar = new Intent(DaftarKonsul_Lain.this, DaftarKonsul.class);
                        myDialog.dismiss();
                        startActivity(keluar);
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });

    }

    public void simpanKlien() {
        User user = SharedPrefManager.getInstance(this).getUser();
        Details details = SharedPrefManager.getInstance(this).getDetails();
        SharedPreferences sharedPref = getSharedPreferences("DaftarKonsul", Context.MODE_PRIVATE);

        final int id;
        token = "Bearer " + user.getToken();
        id = details.getId();
        String nama = namaKlien.getEditText().getText().toString();
        String bio_nik = biodata_NIK.getEditText().getText().toString();
        String ttl = tanggalLahir.getEditText().getText().toString();
        String anakke = anakkeDaftar.getEditText().getText().toString();
        String dari = dariDaftar.getEditText().getText().toString();
        String hubungan = hubunganKlien.getEditText().getText().toString();
        String pendidikan = spPendidikan.getSelectedItem().toString().trim();
        String jk = spJK.getSelectedItem().toString().trim();
        int parent_id = sharedPref.getInt("idKlien", 0);


        if (nama.isEmpty()) {
            namaKlien.setError("Nama Harus Diisi!");
            namaKlien.requestFocus();
            return;
        }

        if (bio_nik.isEmpty()) {
            biodata_NIK.setError("Nik Harus Diisi!");
            biodata_NIK.requestFocus();
            Snackbar.make(findViewById(R.id.daftarChild), "Untuk Balita Dapat Menggunakan nomor KIA", Snackbar.LENGTH_LONG).show();
            return;
        }
        if (ttl.isEmpty()) {
            tanggalLahir.setError("Tanggal Lahir Harus Diisi!");
            tanggalLahir.requestFocus();
            return;
        }
        if (anakke.isEmpty()) {
            anakkeDaftar.setError("Form Harus Diisi!");
            anakkeDaftar.requestFocus();
            return;
        }
        if (dari.isEmpty()) {
            dariDaftar.setError("Jumlah Saudara Harus Diisi!");
            dariDaftar.requestFocus();
            return;
        }
        if (hubungan.isEmpty()) {
            hubunganKlien.setError("Hubungan Harus Diisi!");
            hubunganKlien.requestFocus();
            return;
        }
        if (rg_pendidikan.getCheckedRadioButtonId() == 0) {
            if (rg_pendidikan2.getCheckedRadioButtonId() == 0) {
                r_lain.setError("Pendidikan Harus Diisi!");
                rg_pendidikan2.requestFocus();
                return;
            } else {
                r_lain.setError(null);
                return;
            }
        }
        if (rg_jk.getCheckedRadioButtonId() == 0) {
            r_perempuan.setError("Jenis Kelamin Harus Diisi!");
            rg_jk.requestFocus();
            return;
        }

        SharedPreferences sharedPrefDaftarLain = getSharedPreferences("DaftarKonsul_lain", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefDaftarLain.edit();
        editor.putString("namaKlien", nama);
        editor.putString("tanggalLahir", ttl);
        editor.putString("bio_nik", bio_nik);
        editor.putString("anakke", anakke);
        editor.putString("jumlahSaudara", dari);
        editor.putString("hubungan", hubungan);
        editor.putString("pendidikan", pendidikanKlien);
        editor.putString("jenisKelamin", genderKlien);
        editor.apply();

        if (nama != null) {
            //   Toast.makeText(DaftarKonsul_Lain.this, "Sukses Menambahkan Klien", Toast.LENGTH_LONG).show();
            addOrangLain();
        } else {
            // Toast.makeText(DaftarKonsul_Lain.this, "Gagal Menambahkan Klien", Toast.LENGTH_LONG).show();

        }


    }

    public void addOrangLain() {

        SharedPreferences sharedPref = getSharedPreferences("DaftarKonsul", Context.MODE_PRIVATE);
        final SharedPreferences sharedPrefDaftarLain = getSharedPreferences("DaftarKonsul_lain", Context.MODE_PRIVATE);

        final String namaKlien = sharedPrefDaftarLain.getString("namaKlien", null);
        final String bio_nik = sharedPrefDaftarLain.getString("bio_nik", null);
        final String ttlKlien = sharedPrefDaftarLain.getString("tanggalLahir", null);
        final String pendidikanKlien = sharedPrefDaftarLain.getString("pendidikan", null);
        String anakke = sharedPrefDaftarLain.getString("anakke", null);
        String dari = sharedPrefDaftarLain.getString("jumlahSaudara", null);
        String hubungan = sharedPrefDaftarLain.getString("hubungan", null);
        String jk = sharedPrefDaftarLain.getString("jenisKelamin", null);
        final int parent_id = sharedPref.getInt("idKlien", -1);
        SharedPreferences sharedPrefFCM = getSharedPreferences("TokenFCM", Context.MODE_PRIVATE);
        String tokenFCM = sharedPrefFCM.getString("token", null);

        int yearOB, age, kategori_id = 0;
        String[] yob = ttlKlien.split("-");
        yearOB = Integer.parseInt(yob[0]);
        Calendar today = Calendar.getInstance();
        int yearNow = today.get(Calendar.YEAR);

        if (yearOB == today.get(Calendar.YEAR)) {
            age = 1;
        } else {
            age = yearNow - yearOB;
        }


        if (age >= 1 && age <= 15) {
            kategori_id = 1;
        } else if (age > 15 && age <= 25) {
            kategori_id = 2;
        } else if (age > 25 && age <= 60) {
            kategori_id = 3;
        } else {
            kategori_id = 4;
        }

        test.setText("nama "+namaKlien+" layanan "+ttlKlien+""+" psikolog "+pendidikanKlien+" klien "+anakke+ "status "+dari+ "tanggal "+hubungan+ " keluhan "+genderKlien +"kategori"+kategori_id+"--"+tokenFCM);
       // Toast.makeText(DaftarKonsul_Lain.this, "nama "+namaKlien+" layanan "+ttlKlien+""+" psikolog "+pendidikanKlien+" klien "+anakke+ "status "+dari+ "tanggal "+hubungan+ " keluhan "+genderKlien +"kategori"+kategori_id+"--"+tokenFCM, Toast.LENGTH_LONG).show();

          loading = ProgressDialog.show(DaftarKonsul_Lain.this, null, "Sedang Memproses...", true, false);

        Call<Response_DaftarOrangLain> call = RetrofitClient.getInstance().getApi().daftarLain(token,
                namaKlien,
                "acak", bio_nik, "Klien", ttlKlien, genderKlien,
                anakke, dari,
                pendidikanKlien, parent_id,hubungan, tokenFCM, kategori_id);
        call.enqueue(new Callback<Response_DaftarOrangLain>() {
            @Override
            public void onResponse(retrofit2.Call<Response_DaftarOrangLain> call, Response<Response_DaftarOrangLain> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(DaftarKonsul_Lain.this,  "Sukses Menambahkan", Toast.LENGTH_LONG).show();
                    loading.dismiss();
                    SharedPreferences sharedPref = getSharedPreferences("DaftarKonsul_lain", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("idKlien", response.body().getKlien_DaftarOrangLain().getIdKlien());
                    editor.apply();
                    int idKlienLain = sharedPrefDaftarLain.getInt("idKlien", 0);
                    if (idKlienLain != 0) {
                        Intent intent = new Intent(DaftarKonsul_Lain.this, DaftarKonsul_pilihLayanan2.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(DaftarKonsul_Lain.this,
                                "Data Klien Orang Lain Tidak Valid", Toast.LENGTH_LONG).show(); }
                } else {
                    loading.dismiss();
                    Toast.makeText(DaftarKonsul_Lain.this,
                            "Terjadi Kesalahan Sistem (Tidak Dapat menambahkan Data Klien Baru)", Toast.LENGTH_LONG).show(); } }
            @Override
            public void onFailure(retrofit2.Call<Response_DaftarOrangLain> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(DaftarKonsul_Lain.this, "Periksa Kembali Koneksi Internet Anda", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Snackbar.make(findViewById(R.id.daftarChild), "Untuk Balita Dapat Menggunakan nomor KIA", Snackbar.LENGTH_LONG).show();
    }

    public void r_sd(View view) {
        rg_pendidikan2.clearCheck();
        pendidikanKlien = "SD";
    }

    public void r_diploma(View view) {
        rg_pendidikan2.clearCheck();
        pendidikanKlien = "D3";
    }

    public void r_sma(View view) {
        rg_pendidikan2.clearCheck();
        pendidikanKlien = "SMA";
    }

    public void r_smp(View view) {
        rg_pendidikan2.clearCheck();
        pendidikanKlien = "SMP";
    }

    public void r_lain(View view) {
        rg_pendidikan.clearCheck();
        pendidikanKlien = "S3";
    }

    public void r_s1(View view) {

        rg_pendidikan.clearCheck();
        pendidikanKlien = "S1";
    }

    public void r_s2(View view) {
        rg_pendidikan.clearCheck();
        pendidikanKlien = "S2";
    }

    public void r_lakilaki(View view) {
        genderKlien = "Laki-laki";
    }

    public void r_perempuan(View view) {
        genderKlien = "Perempuan";
    }

    public void loadDataChild(View view) {
        Intent intent = new Intent(DaftarKonsul_Lain.this, Child_Klien.class);
        startActivity(intent);
    }
}
