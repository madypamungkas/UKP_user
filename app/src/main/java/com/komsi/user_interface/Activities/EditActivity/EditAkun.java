package com.komsi.user_interface.Activities.EditActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.komsi.user_interface.Activities.LoadData.Loading;
import com.komsi.user_interface.Activities.MainActivity;
import com.komsi.user_interface.Activities.VerifEmail.SendEmailVerif;
import com.komsi.user_interface.Api.RetrofitClient;
import com.komsi.user_interface.R;
import com.komsi.user_interface.Storage.SharedPrefManager;
import com.komsi.user_interface.models.Details;
import com.komsi.user_interface.models.JsonDefault;
import com.komsi.user_interface.models.Response_DetailKlien;
import com.komsi.user_interface.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class EditAkun extends AppCompatActivity {
    private ImageView avatar;
    private Button ubah;
    private TextInputLayout nama, username, email, phone;
    private String token;
    private int userId;
    Context mCtx;
    ProgressDialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_akun);
        TextView back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditAkun.super.onBackPressed();
            }
        });
        Details details = SharedPrefManager.getInstance(mCtx).getDetails();
        //Klien klien = SharedPrefManager.getInstance(this).getKlienModel();
        User user = SharedPrefManager.getInstance(mCtx).getUser();
        nama = (TextInputLayout) findViewById(R.id.namaEditAkun);
        nama.getEditText().setText(details.getNama());
        username = (TextInputLayout) findViewById(R.id.usernameEditAkun);
        username.getEditText().setText(details.getUsername());
        phone = (TextInputLayout) findViewById(R.id.noHpEditAkun);
        phone.getEditText().setText("" + details.getNo_telepon());
        email = (TextInputLayout) findViewById(R.id.emailEditAkun);
        email.getEditText().setText(details.getEmail());

        ubah = (Button) findViewById(R.id.bt_ubah);
        ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Details details = SharedPrefManager.getInstance(mCtx).getDetails();
                //Klien klien = SharedPrefManager.getInstance(this).getKlienModel();
                User user = SharedPrefManager.getInstance(mCtx).getUser();
                String emailOld = details.getEmail();
                String email_verified_at_old = details.getEmail_verified_at();

                final String usernameAkun = username.getEditText().getText().toString().trim();
                final String emailNew = email.getEditText().getText().toString().trim();
                final String phoneAkun = "" + phone.getEditText().getText().toString().trim();
                String email_verif_at_new = null;

                if (usernameAkun.isEmpty()) {
                    username.setError("Username Tidak Boleh Kosong");
                    username.requestFocus();
                    return;
                }
                if (usernameAkun.contains(" ")) {
                    username.setError("Username Terdiri Dari Satu Kata!");
                    username.requestFocus();
                    return;
                }
                if (usernameAkun.length() < 6) {
                    username.setError("Username Minimum Terdiri Dari 6 Karakter!");
                    username.requestFocus();
                    return;
                }

                if (emailNew.isEmpty()) {
                    email.setError("Email Tidak Boleh Kosong");
                    email.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(emailNew).matches()) {
                    email.setError("Isikan Email yang Benar!");
                    email.requestFocus();
                    return;
                }

                if (emailNew.equals(emailOld)) {
                    email_verif_at_new = details.getEmail_verified_at();
                }
                if (!emailNew.equals(emailOld)) {
                    email_verif_at_new = null;
                }

                if (phoneAkun.isEmpty()) {
                    phone.setError("Email Tidak Boleh Kosong");
                    phone.requestFocus();
                    return;
                }


                token = "Bearer " + user.getToken();
                SharedPreferences sharedPref = getSharedPreferences("DataPsikolog", Context.MODE_PRIVATE);
                final int iduser = details.getId();
                userId = iduser;
                final String email_verified_at = email_verif_at_new;
                loading = ProgressDialog.show(EditAkun.this, null, "Sedang Memuat...", true, false);

                Call<JsonDefault> call = RetrofitClient.getInstance().getApi().updateUser("application/json", token, userId,
                        usernameAkun, emailNew, email_verified_at, phoneAkun);
                call.enqueue(new Callback<JsonDefault>() {
                    @Override
                    public void onResponse(Call<JsonDefault> call, Response<JsonDefault> response) {
                        loading.dismiss();
                        JsonDefault signUpResponse = response.body();
                        String checkEmailVerif = email_verified_at + "0";
                        if (response.isSuccessful()) {
                            if (signUpResponse.getStatus().equals("success")) {
                                Log.i("debug", "onResponse: SUCCESS");
                                loading.dismiss();
//                                Toast.makeText(EditAkun.this, "Ubah Data Success!", Toast.LENGTH_LONG).show();
//                                startActivity(new Intent(EditAkun.this, MainActivity.class));
//
                                if (checkEmailVerif.length() < 6) {
                                    Intent sukses = new Intent(EditAkun.this, SendEmailVerif.class);
                                    sukses.putExtra("email", emailNew);
                                    sukses.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(sukses);
                                } else {
                                    Intent sukses = new Intent(EditAkun.this, MainActivity.class);
                                    sukses.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(sukses);
                                }
                            }
                        } else {
                            loading.dismiss();
                            try {
                                String errorBody = response.errorBody().string();
                                JSONObject jsonObject = new JSONObject(errorBody.trim());
                                jsonObject = jsonObject.getJSONObject("errors");
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
                                Toast.makeText(EditAkun.this, errors.toString(), Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(EditAkun.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<JsonDefault> call, Throwable t) {
                        Toast.makeText(EditAkun.this, "Data Telah Digunakan!", Toast.LENGTH_LONG).show();
                        if (t instanceof HttpException) {
                            HttpException httpException = (HttpException) t;
                            try {
                                String error = httpException.response().errorBody().string();
                                Toast.makeText(EditAkun.this, error, Toast.LENGTH_LONG).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });
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
                    SharedPrefManager.getInstance(EditAkun.this).saveDetails(response.body().getDetails());

                } else {
                    Toast.makeText(EditAkun.this, "Error, Periksa Kembali Jaringan Anda", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Response_DetailKlien> call, Throwable t) {
                Toast.makeText(EditAkun.this, "Error, Periksa Kembali Jaringan Anda", Toast.LENGTH_LONG).show();
            }
        });
    }

}
