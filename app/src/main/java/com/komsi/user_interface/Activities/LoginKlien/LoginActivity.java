package com.komsi.user_interface.Activities.LoginKlien;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.komsi.user_interface.Activities.Dashboard.Dashboard;
import com.komsi.user_interface.Activities.LoadData.Loading;
import com.komsi.user_interface.Activities.MainActivity;
import com.komsi.user_interface.Activities.RegisterUser.DaftarActivity;
import com.komsi.user_interface.Activities.password.LupaPassword;
import com.komsi.user_interface.Api.RetrofitClient;
import com.komsi.user_interface.R;
import com.komsi.user_interface.Storage.SharedPrefManager;
import com.komsi.user_interface.models.Details;
import com.komsi.user_interface.models.Response_Login;
import com.komsi.user_interface.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout textInputUsername;
    private TextInputLayout textInputPassword;
    ProgressDialog loading;
    NotificationCompat.Builder notification;
    public static final int uniqeID = 45614;
    private ConnectivityManager connectivityManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textInputUsername = findViewById(R.id.textInputUsername);
        textInputPassword = findViewById(R.id.textInputPassword);
        connectivityManager = (ConnectivityManager)
                getSystemService(CONNECTIVITY_SERVICE);
        final String level = "Response_Klien";
        Button daftar_lg = (Button) findViewById(R.id.bt_daftarSkrg);
        daftar_lg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent daftar_lg = new Intent(LoginActivity.this,
                        Dashboard.class);
                startActivity(daftar_lg);
            }
        });

        Button login_bt = (Button) findViewById(R.id.bt_login);
        login_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               login();
            }
        });
        Button lupa = (Button) findViewById(R.id.bt_lupa);
        lupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lupa = new Intent(LoginActivity.this, LupaPassword.class);
                startActivity(lupa);
            }
        });
    }


    public void showResultDialogue() {
        SharedPreferences sharedPrefFCM = getSharedPreferences("TokenFCM", Context.MODE_PRIVATE);
        final String TokenFCM = sharedPrefFCM.getString("token", null);
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Token FCM")
                .setMessage("Token FCM " + TokenFCM)
                .setPositiveButton("Copy Token", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("Hasil Scanning", TokenFCM);
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(LoginActivity.this, "Hasil Scan Berhasil Ter-Copy", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        cekConnectivity();
        //showResultDialogue();
    }

    private void cekConnectivity() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Snackbar.make(findViewById(R.id.loginLayout),
                    "Anda Terhubung Dengan Layanan Internet", Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(findViewById(R.id.loginLayout),
                    "Koneksi Terputus, Anda Tidak Terhubung Dengan Layanan Internet", Snackbar.LENGTH_LONG).show();

        }
    }
    public void login(){
        String username = textInputUsername.getEditText().
                getText().toString().trim();
        String password = textInputPassword.getEditText().
                getText().toString().trim();

        if (username.isEmpty()) {
            textInputUsername.setError("Username Harus Diisi!");
            textInputUsername.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            textInputPassword.setError("Password Harus Diisi!");
            textInputPassword.requestFocus();
            return;
        }
        loading = ProgressDialog.show(LoginActivity.this, null,
                "Sedang Memuat...", true, false);


        Call<Response_Login> call = RetrofitClient
                .getInstance()
                .getApi()
                .login(username, password, "Klien");
        call.enqueue(new Callback<Response_Login>() {
            @Override
            public void onResponse(Call<Response_Login> call, Response<Response_Login> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    SharedPrefManager.getInstance(LoginActivity.this)
                            .saveUser(response.body().getUser());
                    Toast.makeText(LoginActivity.this, "Selamat Datang " + response.body().getUser()
                            .getUsername_id(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, Loading.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Error, Periksa Kembali Username dan Password Anda",
                            Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Response_Login> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error, Periksa Kembali Username dan Password Anda",
                        Toast.LENGTH_LONG).show();
                loading.dismiss();
            }
        });
    }


}
