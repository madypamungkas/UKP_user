package com.komsi.user_interface.Activities.EditActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.komsi.user_interface.Activities.LoadData.Loading;
import com.komsi.user_interface.Activities.MainActivity;
import com.komsi.user_interface.Api.RetrofitClient;
import com.komsi.user_interface.R;
import com.komsi.user_interface.Storage.SharedPrefManager;
import com.komsi.user_interface.models.Details;
import com.komsi.user_interface.models.Response_DetailKlien;
import com.komsi.user_interface.models.Klien;
import com.komsi.user_interface.models.Response_Klien;
import com.komsi.user_interface.models.Response_BiodataUpdate;
import com.komsi.user_interface.models.User;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditBiodataUser extends AppCompatActivity {
    private Spinner spPendidikan, spJK;
    private TextInputLayout bioNama, bioNik, bioTTL, bioAlamat, bioAnakke, bioDari, bioNohp;
    ImageView avatar;
    Dialog myDialog;
    ProgressDialog loading;
    String token;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    Button dateTTL;
    RadioButton r_lakilaki, r_perempuan, r_sd, r_smp, r_sma, r_diploma, r_s1, r_s2, r_lain;
    RadioGroup rg_jk, rg_pendidikan, rg_pendidikan2;
    private String pendidikanKlien, genderKlien;
    Details details = SharedPrefManager.getInstance(this).getDetails();
    Klien klien = SharedPrefManager.getInstance(this).getKlienModel();
    int yearOB, age, kategori_id = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_biodata_user);
        TextView back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.setContentView(R.layout.pop_up_edit_biodata);
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
                        Intent keluar = new Intent(EditBiodataUser.this, MainActivity.class);
                        myDialog.dismiss();
                        startActivity(keluar);
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });

        avatar = (ImageView) findViewById(R.id.avatar);
        SharedPreferences sharedPrefFCM = getSharedPreferences("linkFoto", Context.MODE_PRIVATE);
        String link = sharedPrefFCM.getString("link", null);
        final String url = link + details.getFoto();
        Picasso.with(this).load(url).error(R.drawable.menu_icon_user).into(avatar);

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


        spPendidikan = (Spinner) findViewById(R.id.spin_pendidikan);
        spJK = (Spinner) findViewById(R.id.spin_kelamin);

        //spJK.getSelectedItem(details.getJenis_kelamin());
        bioNama = (TextInputLayout) findViewById(R.id.biodata_nama);
        bioNama.getEditText().setText(details.getNama());
        bioNohp = (TextInputLayout) findViewById(R.id.biodata_nohp);
        bioNohp.getEditText().setText(details.getNo_telepon());
        bioNik = (TextInputLayout) findViewById(R.id.biodata_NIK);
        bioNik.getEditText().setText(details.getNik());
        bioTTL = (TextInputLayout) findViewById(R.id.biodata_TL);
        //  bioTTL.getEditText().setText(details.getTanggal_lahir());
        bioAlamat = (TextInputLayout) findViewById(R.id.biodata_alamat);
        bioAlamat.getEditText().setText(details.getAlamat());
        bioDari = (TextInputLayout) findViewById(R.id.biodata_dari);
        bioDari.getEditText().setText("" + klien.getJumlah_saudara());
        bioAnakke = (TextInputLayout) findViewById(R.id.biodata_anakke);
        bioAnakke.getEditText().setText("" + klien.getAnak_ke());
        setPendidikan();
        setGender();

        dateTTL = (Button) findViewById(R.id.dateTTL);

        dateTTL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(EditBiodataUser.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        bioTTL.getEditText().setText(mYear + "-" + (mMonth + 1) + "-" + mDay);


                    }
                }, year, month, day);
                Calendar calMax = Calendar.getInstance();
                calMax.add(Calendar.YEAR, -3);
                long calMaxMillis = calMax.getTimeInMillis();
                datePickerDialog.getDatePicker().setMaxDate(calMaxMillis);
                datePickerDialog.show();


            }
        });


        Button bt_simpan = (Button) findViewById(R.id.bt_simpanBiodata);
        bt_simpan.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                simpan();
            }
        });

        myDialog = new

                Dialog(this);

        TextView exit = (TextView) findViewById(R.id.back);

        exit.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                myDialog.setContentView(R.layout.pop_up_edit_biodata);
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
                        Intent keluar = new Intent(EditBiodataUser.this, MainActivity.class);
                        myDialog.dismiss();
                        startActivity(keluar);
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });
    }


    public void simpan() {
        User user = SharedPrefManager.getInstance(this).getUser();
        Details details = SharedPrefManager.getInstance(this).getDetails();

        final int id;
        token = "Bearer " + user.getToken();
        id = details.getId();

        String name = bioNama.getEditText().getText().toString().trim();
        String nik = bioNik.getEditText().getText().toString().trim();
        String ttl = bioTTL.getEditText().getText().toString().trim();
        String alamat = bioAlamat.getEditText().getText().toString().trim();
        String anakke = bioAnakke.getEditText().getText().toString().trim();
        String dari = bioDari.getEditText().getText().toString().trim();
        String nohp = bioNohp.getEditText().getText().toString().trim();


        if (name.isEmpty()) {
            bioNama.setError("Nama Harus Diisi!");
            bioNama.requestFocus();
            return;
        }
        if (nik.isEmpty()) {
            bioNik.setError("NIK Harus Diisi!");
            bioNik.requestFocus();
            return;
        }
        if (ttl.isEmpty()) {
            bioTTL.setError("Tanggal Harus Diisi!");
            bioTTL.requestFocus();
            return;
        }

        if (alamat.isEmpty()) {
            bioAlamat.setError("Alamat Harus Diisi!");
            bioAlamat.requestFocus();
            return;
        }
        if (anakke.isEmpty()) {
            bioAnakke.setError("Form Harus Diisi!");
            bioAnakke.requestFocus();
            return;
        }
        if (anakke.equals("0")) {
            bioAnakke.setError("Form Harus Diisi!");
            bioAnakke.requestFocus();
            return;
        }
        if (dari.isEmpty()) {
            bioDari.setError("Jumlah Saudara Harus Diisi!");
            bioDari.requestFocus();
            return;
        }
        if (dari.equals("0")) {
            bioAnakke.setError("Form Harus Diisi!");
            bioAnakke.requestFocus();
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


        String[] yob = ttl.split("-");
        yearOB = Integer.parseInt(yob[0]);
        Calendar today = Calendar.getInstance();
        int yearNow = today.get(Calendar.YEAR);

        if (yearOB == today.get(Calendar.YEAR)) {
            age = 1;
        } else {
            age = yearNow - yearOB;
        }


        if (age >= 1 && age <= 12) {
            kategori_id = 1;
        } else if (age > 12 && age <= 21) {
            kategori_id = 2;
        } else if (age > 21 && age <= 59) {
            kategori_id = 3;
        } else {
            kategori_id = 9;
        }

        // Toast.makeText(EditBiodataUser.this, kategori_id + " - " + age + " ", Toast.LENGTH_LONG).show();

        loading = ProgressDialog.show(EditBiodataUser.this,
                null, "Sedang Memproses...", true, false);


        retrofit2.Call<Response_BiodataUpdate> call = RetrofitClient.getInstance()
                .getApi().update(token, id, name, nik, ttl, genderKlien
                        , alamat, nohp, anakke, dari, pendidikanKlien,kategori_id);
        call.enqueue(new Callback<Response_BiodataUpdate>() {
            @Override
            public void onResponse(retrofit2.Call<Response_BiodataUpdate> call, Response<Response_BiodataUpdate> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    loadDetails();

                } else {
                    Toast.makeText(EditBiodataUser.this, "Terjadi Kesalahan Sistem", Toast.LENGTH_LONG).show();
                    loading.dismiss();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Response_BiodataUpdate> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(EditBiodataUser.this, "Terjadi Kesalahan Sistem", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void loadDetails() {
        retrofit2.Call<Response_DetailKlien> call = RetrofitClient
                .getInstance()
                .getApi()
                .getDetails(token);
        call.enqueue(new retrofit2.Callback<Response_DetailKlien>() {
            @Override
            public void onResponse(retrofit2.Call<Response_DetailKlien> call, retrofit2.Response<Response_DetailKlien> response) {
                if (response.isSuccessful()) {
                    SharedPrefManager.getInstance(EditBiodataUser.this).saveDetails(response.body().getDetails());
                    loadKlien();

                } else {
                    Toast.makeText(EditBiodataUser.this, "Error, Periksa Kembali Jaringan Anda", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Response_DetailKlien> call, Throwable t) {
                Toast.makeText(EditBiodataUser.this, "Error, Periksa Kembali Jaringan Anda", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void loadKlien() {
        Details details = SharedPrefManager.getInstance(EditBiodataUser.this).getDetails();
        int idUser = details.getId();

        Call<Response_Klien> callKlien = RetrofitClient.getInstance().getApi().getKlien(token, idUser);
        callKlien.enqueue(new Callback<Response_Klien>() {
            @Override
            public void onResponse(Call<Response_Klien> call, Response<Response_Klien> response) {
                SharedPrefManager.getInstance(EditBiodataUser.this).saveKlien(response.body().getKlien());
                /*Intent intent = new Intent(EditBiodataUser.this, MainActivity.class);
               // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);*/
                Intent sukses = new Intent(EditBiodataUser.this, MainActivity.class);
                sukses.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(sukses);
            }

            @Override
            public void onFailure(Call<Response_Klien> call, Throwable t) {
                Toast.makeText(EditBiodataUser.this, "Error, Periksa Kembali Jaringan Anda", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void setGender() {
        if (details.getJenis_kelamin() == null) {
            r_lakilaki.setChecked(false);
            r_perempuan.setChecked(false);
            return;
        }
        if (details.getJenis_kelamin().equals("Perempuan")) {
            r_lakilaki.setChecked(false);
            r_perempuan.setChecked(true);
            genderKlien = "Perempuan";
            return;
        }
        if (details.getJenis_kelamin().equals("Laki-laki")) {
            r_lakilaki.setChecked(true);
            r_perempuan.setChecked(false);
            genderKlien = "Laki-laki";
            return;
        }
    }

    public void setPendidikan() {
        if (klien.getPendidikan_terakhir() == null) {
            rg_pendidikan.clearCheck();
            rg_pendidikan2.clearCheck();
            return;
        }
        if (klien.getPendidikan_terakhir().equals("SD")) {
            r_sd.setChecked(true);
            rg_pendidikan2.clearCheck();
            pendidikanKlien = "SD";
            return;
        }
        if (klien.getPendidikan_terakhir().equals("SMP")) {
            r_smp.setChecked(true);
            rg_pendidikan2.clearCheck();
            pendidikanKlien = "SMP";
            return;
        }
        if (klien.getPendidikan_terakhir().equals("SMA")) {
            r_sma.setChecked(true);
            rg_pendidikan2.clearCheck();
            pendidikanKlien = "SMA";
            return;
        }
        if (klien.getPendidikan_terakhir().equals("D3")) {
            r_diploma.setChecked(true);
            rg_pendidikan2.clearCheck();
            pendidikanKlien = "D3";
            return;
        }
        if (klien.getPendidikan_terakhir().equals("S1")) {
            r_s1.setChecked(true);
            rg_pendidikan.clearCheck();
            pendidikanKlien = "S1";
            return;
        }
        if (klien.getPendidikan_terakhir().equals("S2")) {
            r_s2.setChecked(true);
            rg_pendidikan.clearCheck();
            pendidikanKlien = "S2";
            return;
        }
        if (klien.getPendidikan_terakhir().equals("S3")) {
            r_lain.setChecked(true);
            rg_pendidikan.clearCheck();
            pendidikanKlien = "S3";
            return;
        }


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
}
