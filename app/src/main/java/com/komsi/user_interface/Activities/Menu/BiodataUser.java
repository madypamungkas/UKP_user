package com.komsi.user_interface.Activities.Menu;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.komsi.user_interface.Activities.EditActivity.EditBiodataUser;
import com.komsi.user_interface.Activities.MainActivity;
import com.komsi.user_interface.AlarmTask.AlarmTask;
import com.komsi.user_interface.Api.RetrofitClient;
import com.komsi.user_interface.R;
import com.komsi.user_interface.Storage.SharedPrefManager;
import com.komsi.user_interface.models.Biodata;
import com.komsi.user_interface.models.Details;
import com.komsi.user_interface.models.Klien;
import com.komsi.user_interface.models.Response_Klien;
import com.komsi.user_interface.models.User;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Response;

public class BiodataUser extends AppCompatActivity {
    TextView nama, nik, jk, ttl, alamat, noHp, anak_ke, jumlah_saudara, pendidikan_terakhir;
    ImageView avatar;
    private Context context = this;
    SwipeRefreshLayout swiperefresh;
    private int refresh_count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biodata_user);
        Details details = SharedPrefManager.getInstance(this).getDetails();
        Klien klien = SharedPrefManager.getInstance(this).getKlienModel();
        avatar = (ImageView) findViewById(R.id.avatar);
        SharedPreferences sharedPrefFCM = getSharedPreferences("linkFoto", Context.MODE_PRIVATE);
        String link = sharedPrefFCM.getString("link", null);
        final String url = link + details.getFoto();
        Picasso.with(this).load(url).error(R.drawable.menu_icon_user).into(avatar);

        swiperefresh = findViewById(R.id.swiperefresh);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDetails();
            }
        });

        //String url="https://10.0.2.2/psikolog-api-master/storage/app/images/330813306475681.jpg";

//        Picasso.with(this).load("https://i.redd.it/zcoqt2kvwqb21.jpg")
//                .into(avatar);

        nama = findViewById(R.id.bioName);
        nama.setText(" " + details.getNama());
        nik = findViewById(R.id.bioNik);
        nik.setText(" " + details.getNik());
        jk = findViewById(R.id.bioJk);
        jk.setText(" " + details.getJenis_kelamin());
        ttl = findViewById(R.id.bioTTL);
        ttl.setText(" " + details.getTanggal_lahir());
        alamat = findViewById(R.id.bioAlamat);
        alamat.setText(" " + details.getAlamat());
        noHp = findViewById(R.id.bioNohp);
        noHp.setText("  " + details.getNo_telepon());
        anak_ke = findViewById(R.id.bioAnakke);
        anak_ke.setText("  " + klien.getAnak_ke());
        jumlah_saudara = findViewById(R.id.bioJumlahsaudara);
        jumlah_saudara.setText("  " + klien.getJumlah_saudara() + "  Bersaudara");
        pendidikan_terakhir = findViewById(R.id.bioPendidikan);
        pendidikan_terakhir.setText("  " + klien.getPendidikan_terakhir());
        TextView back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BiodataUser.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Button bt_edit = (Button) findViewById(R.id.bt_daftarSaya);
        bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent edit = new Intent(BiodataUser.this, EditBiodataUser.class);
                startActivity(edit);

            }
        });
    }

    @Override
    protected void onStart() {
        // super.onResume();
        super.onStart();

    }

    public void getDetails() {

        final Details details = SharedPrefManager.getInstance(this).getDetails();
        User user = SharedPrefManager.getInstance(this).getUser();
        final String token;
        token = "Bearer " + user.getToken();
        Call<Response_Klien> callKlien = RetrofitClient
                .getInstance()
                .getApi()
                .getKlien(token, details.getId());
        callKlien.enqueue(new retrofit2.Callback<Response_Klien>() {
            @Override
            public void onResponse(Call<Response_Klien> call, Response<Response_Klien> response) {
                swiperefresh.setRefreshing(false);
                if (refresh_count > 3){
                    refresh_count = 0;
                    swiperefresh.setRefreshing(false);
                }

                SharedPrefManager.getInstance(BiodataUser.this).saveKlien(response.body().getKlien());

            }

            @Override
            public void onFailure(Call<Response_Klien> call, Throwable t) {
                Toast.makeText(BiodataUser.this, "Error, Periksa Kembali Jaringan Anda", Toast.LENGTH_LONG).show();
                swiperefresh.setRefreshing(false);
                if (refresh_count > 3){
                    refresh_count = 0;
                    swiperefresh.setRefreshing(false);
                }
            }
        });
    }
//    private void setAlarm(long time){
//        AlarmManager am =(AlarmManager)getSystemService(this.ALARM_SERVICE);
//
//        Intent intent = new Intent(BiodataUser.this, AlarmTask.class);
//        PendingIntent pi = PendingIntent.getBroadcast(this,0, intent, 0);
//        am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi);
//        Toast.makeText(this, "Notif Set ", Toast.LENGTH_SHORT).show();
//
//
//    }
//
}
