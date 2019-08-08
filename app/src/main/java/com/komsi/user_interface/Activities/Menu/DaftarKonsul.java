package com.komsi.user_interface.Activities.Menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.komsi.user_interface.Activities.DaftarKonsultasi.DaftarKonsul_Lain;
import com.komsi.user_interface.Activities.DaftarKonsultasi.DaftarKonsul_Saya;
import com.komsi.user_interface.Activities.MainActivity;
import com.komsi.user_interface.Api.RetrofitClient;
import com.komsi.user_interface.R;
import com.komsi.user_interface.Storage.SharedPrefManager;
import com.komsi.user_interface.models.Details;
import com.komsi.user_interface.models.Response_Klien;
import com.komsi.user_interface.models.User;

import retrofit2.Call;
import retrofit2.Response;

public class DaftarKonsul extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_konsul);

        TextView back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(DaftarKonsul.this, MainActivity.class);
                startActivity(back);
            }
        });
        Button daftar_saya = (Button) findViewById(R.id.bt_daftarSaya);
        daftar_saya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent daftar_lg = new Intent( DaftarKonsul.this, DaftarKonsul_Saya.class);
                startActivity(daftar_lg);
            }
        });

        Button daftar_lain = (Button) findViewById(R.id.bt_daftarLain);
        daftar_lain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login_bt = new Intent(DaftarKonsul.this, DaftarKonsul_Lain.class);
                startActivity(login_bt);
            }
        });
        //getInfo();

    }
    private void getInfo(){
        final Details details = SharedPrefManager.getInstance(this).getDetails();
        User user = SharedPrefManager.getInstance(this).getUser();
        final String token;
        token = "Bearer "+ user.getToken();
        Call<Response_Klien> callKlien = RetrofitClient
                .getInstance()
                .getApi()
                .getKlien(token, details.getId());
        callKlien.enqueue(new retrofit2.Callback<Response_Klien>() {
            @Override
            public void onResponse(Call<Response_Klien> call, Response<Response_Klien> response) {
                if(response.isSuccessful()) {
                    SharedPrefManager.getInstance(DaftarKonsul.this).saveKlien(response.body().getKlien());
                }
                else{
                    Toast.makeText(DaftarKonsul.this, "Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Response_Klien> call, Throwable t) {
                Toast.makeText(DaftarKonsul.this, "Error, Periksa Kembali Jaringan Anda", Toast.LENGTH_LONG).show();
            }
        });

    }
    @Override
    protected void onStart() {
        // super.onResume();
        super.onStart();
      /*  SharedPreferences sharedPref = getSharedPreferences("DaftarKonsul", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();*/
        SharedPreferences sharedPrefDaftarLain = getSharedPreferences(
                "DaftarKonsul_lain", Context.MODE_PRIVATE);
        SharedPreferences.Editor editorDL = sharedPrefDaftarLain.edit();
        editorDL.clear();
        editorDL.apply();


    }
}
