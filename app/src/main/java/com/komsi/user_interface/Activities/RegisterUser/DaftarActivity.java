package com.komsi.user_interface.Activities.RegisterUser;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.google.gson.JsonObject;
import com.komsi.user_interface.Activities.EditActivity.EditBiodataUser;
import com.komsi.user_interface.Activities.LoginKlien.LoginActivity;
import com.komsi.user_interface.Activities.MainActivity;
import com.komsi.user_interface.Api.RetrofitClient;
import com.komsi.user_interface.R;
import com.komsi.user_interface.Storage.SharedPrefManager;
import com.komsi.user_interface.models.Details;
import com.komsi.user_interface.models.JsonDefault;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DaftarActivity extends AppCompatActivity {
    Dialog myDialog;
    TextView txtclose;
    private Spinner spPendidikan;
    private final static int REQUEST_CODE = 999;
    Button daftar;
    private TextInputLayout textNama, textEmail, textPassword, textNohp, textUsername, bioTTL;
    RadioButton r_sd, r_smp, r_sma, r_diploma, r_s1, r_s2, r_lain;
    RadioGroup rg_jk, rg_pendidikan, rg_pendidikan2;
    private String pendidikanKlien;
    NotificationCompat.Builder notification;
    public static final int uniqeID = 45614;
    ProgressDialog loading;
    CheckBox termsCb;
    TextView termsText;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    Button dateTTL;
    int yearOB, age, kategori_id = 0;
    String ttl;


    @Override
    protected void onPostResume() {
        super.onPostResume();
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(Account account) {
                textNohp.getEditText().setText(String.format(account.getPhoneNumber() == null ?
                        "" : account.getPhoneNumber().toString()));
            }

            @Override
            public void onError(AccountKitError accountKitError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        myDialog = new Dialog(this);


        termsCb = findViewById(R.id.termsCb);
        termsText = findViewById(R.id.termsText);
        termsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DaftarActivity.this, TermOfServices.class);
                startActivity(intent);
            }
        });
        dateTTL = (Button) findViewById(R.id.dateTTL);
        bioTTL = (TextInputLayout) findViewById(R.id.biodata_TL);

        dateTTL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(DaftarActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        ttl = (mYear + "-" + (mMonth + 1) + "-" + mDay);
                        int bulan = mMonth + 1;
                        String b, t;

                        if (bulan < 10) {
                            b = "0" + bulan;
                        } else {
                            b = "" + bulan;
                        }
                        if (mDay < 10) {
                            t = "0" + mDay;
                        } else {
                            t = "" + mDay;
                        }
                        bioTTL.getEditText().setText(t + "-" + b + "-" + mYear);


                    }
                }, year, month, day);
                Calendar calMax = Calendar.getInstance();
                calMax.add(Calendar.YEAR, -3);
                long calMaxMillis = calMax.getTimeInMillis();
                datePickerDialog.getDatePicker().setMaxDate(calMaxMillis);
                datePickerDialog.show();


            }
        });

        textNama = (TextInputLayout) findViewById(R.id.textInputNama);
        textUsername = (TextInputLayout) findViewById(R.id.textInputUsername);
        textEmail = (TextInputLayout) findViewById(R.id.textInputEmail);
        textPassword = (TextInputLayout) findViewById(R.id.textInputPassword);
        textNohp = (TextInputLayout) findViewById(R.id.textInputNohp);
        spPendidikan = (Spinner) findViewById(R.id.spin_pendidikan);
        daftar = (Button) findViewById(R.id.bt_daftar);

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daftar();
            }


        });

    }

    public void daftar() {
        String name = textNama.getEditText().getText().toString().trim();
        String username = textUsername.getEditText().getText().toString().trim();
        String email = textEmail.getEditText().getText().toString().trim();
        //String ttl = bioTTL.getEditText().getText().toString().trim();
        String password = textPassword.getEditText().getText().toString().trim();
        String no_telepon = textNohp.getEditText().getText().toString().trim();
        String pendidikan = spPendidikan.getSelectedItem().toString().trim();
        if (name.isEmpty()) {
            textNama.setError("Nama Harus Diisi!");
            textNama.requestFocus();
            return;
        }
        if (username.isEmpty()) {
            textUsername.setError("Username Harus Diisi!");
            textUsername.requestFocus();
            return;
        }
        if (username.contains(" ")) {
            textUsername.setError("Username Terdiri Dari Satu Kata!");
            textUsername.requestFocus();
            return;
        }
        if (username.length() < 6) {
            textUsername.setError("Username Minimum Terdiri Dari 6 Karakter!");
            textUsername.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            textPassword.setError("Password Harus Diisi!");
            textPassword.requestFocus();
            return;
        }
        if (username.contains(" ")) {
            textUsername.setError("Username Terdiri Dari Satu Kata!");
            textUsername.requestFocus();
            return;
        }
        if (password.length() < 6) {
            textPassword.setError("Password Minimum Terdiri Dari 6 Karakter!");
            textPassword.requestFocus();
            return;
        }
        if (ttl.isEmpty()) {
            bioTTL.setError("Tanggal Harus Diisi!");
            bioTTL.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            textEmail.setError("Email Harus Diisi!");
            textEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textEmail.setError("Isikan Email yang Benar!");
            textEmail.requestFocus();
            return;
        }
        if (no_telepon.isEmpty()) {
            textNohp.setError("No Telepon Harus Diisi!");
            textNohp.requestFocus();
            return;
        }

        if (!termsCb.isChecked()) {
            termsCb.setError("Pilih Dan Setujui");
            termsCb.requestFocus();
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

        loading = ProgressDialog.show(DaftarActivity.this, null, "Sedang Memproses...", true, false);

        Call<JsonDefault> call = RetrofitClient
                .getInstance()
                .getApi()
                .klien("application/json",name, email, password, username, no_telepon, ttl, kategori_id, "Klien");
        call.enqueue(new Callback<JsonDefault>() {
            @Override
            public void onResponse(Call<JsonDefault> call, Response<JsonDefault> response) {
                JsonDefault signUpResponse = response.body();
                if (response.isSuccessful()){
                    if (signUpResponse.getStatus().equals("success")) {
                        Log.i("debug", "onResponse: SUCCESS");
                        loading.dismiss();
                        Toast.makeText(DaftarActivity.this, "Registration Success!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(DaftarActivity.this, LoginActivity.class));
                    }
                } else {
                    loading.dismiss();
                    try {
                        String errorBody = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(errorBody.trim());
                        jsonObject = jsonObject.getJSONObject("error");
                        Iterator<String> keys = jsonObject.keys();
                        StringBuilder errors = new StringBuilder();
                        String separator = "";
                        while (keys.hasNext()) {
                            String key = keys.next();
                            JSONArray arr = jsonObject.getJSONArray(key);
                            for (int i = 0; i < arr.length(); i++) {
                                errors.append(separator).append(key).append(" : ").append(arr.getString(i));
                                separator = "\n";
                            }
                        }
                        Toast.makeText(DaftarActivity.this, errors.toString(), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(DaftarActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<JsonDefault> call, Throwable t) {
                Toast.makeText(DaftarActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                loading.dismiss();

            }
        });
    }

    public void notifikasi() {
        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);
        Details details = SharedPrefManager.getInstance(this).getDetails();

        notification.setSmallIcon(R.drawable.nav_icon_orders);
        notification.setTicker("ticker");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("Daftar Akun Sukses");
        // notification.setLargeIcon(R.drawable.nav_icon_orders);
        notification.setContentText("Pendaftaran Akun Telah Berhasil");

        Intent intent = new Intent(this, LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqeID, notification.build());
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


}
